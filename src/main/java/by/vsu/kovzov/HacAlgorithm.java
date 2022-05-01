package by.vsu.kovzov;

import by.vsu.kovzov.function.algorithm.*;
import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

import java.util.Iterator;

public class HacAlgorithm<T> {
    private DataSet<T> source;

    private Linkage<T> linkage;

    public HacAlgorithm(DataSet<T> source, Linkage<T> linkage) {
        this.source = source;
        this.linkage = linkage;
    }

    public DataSet<Cluster<T>> start() {
        DataSet<Cluster<T>> clusters = source.map(new ClusterMapFunction<>());

        IterativeDataSet<Cluster<T>> iteration = clusters.iterate(Integer.MAX_VALUE);

        DataSet<Tuple3<Cluster<T>, Cluster<T>, Double>> clustersWithDist = iteration.join(iteration)
                .where(value -> true)
                .equalTo(value -> true)
                .with(new DistanceCalculator<>(linkage))
                .name("calculate dist")
                .distinct(Tuple3::hashCode);

        DataSet<Cluster<T>> min = clustersWithDist
                .reduce(new MinFunction<T>())
                .name("find 2 cluster with min dist")
                .map(new MergeFunction<T>())
                .name("merge 2 clusters with min dist");

        DataSet<Cluster<T>> step = clustersWithDist.join(min)
                .where(value -> true)
                .equalTo(value -> true)
                .with(new ClusterCleaner<T>())
                .distinct(Cluster::hashCode)
                .name("clean after merge")
                .union(min)
                .name("add merged cluster");

        DataSet<Integer> termination = iteration.first(3).reduceGroup(new GroupReduceFunction<Cluster<T>, Integer>() {
            @Override
            public void reduce(Iterable<Cluster<T>> values, Collector<Integer> out) throws Exception {
                Iterator iterator = values.iterator();
                if (iterator.hasNext()) {
                    iterator.next();
                }
                if (iterator.hasNext()) {
                    iterator.next();
                }
                if (iterator.hasNext()) {

                    out.collect(1);
                }
            }
        });

        return iteration.closeWith(step, termination);
    }
}
