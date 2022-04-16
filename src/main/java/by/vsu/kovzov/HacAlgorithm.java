package by.vsu.kovzov;

import by.vsu.kovzov.function.algorithm.*;
import by.vsu.kovzov.function.base.PrintFunction;
import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.tuple.Tuple3;

public class HacAlgorithm <T> {
    private DataSet<T> source;

    private Linkage<T> linkage;

    public HacAlgorithm(DataSet<T> source, Linkage<T> linkage) {
        this.source = source;
        this.linkage = linkage;
    }

    public DataSet<Cluster<T>> start() {
        DataSet<Cluster<T>> clusters = source.map(new ClusterMapFunction<>());

        IterativeDataSet<Cluster<T>> iteration = clusters.iterate(Integer.MAX_VALUE);

        DataSet<Tuple3<Cluster<T>, Cluster<T>, Double>> clustersWithDist = iteration.joinWithHuge(iteration)
                .where(value -> true)
                .equalTo(value -> true)
                .with(new DistanceCalculator<>(linkage))
                .name("calculate dist")
                .distinct(value -> value.f0.id + value.f1.id);

        DataSet<Cluster<T>> min = clustersWithDist
                .reduce(new MinFunction<T>())
                .name("find 2 cluster with min dist")
                .map(new MergeFunction<T>())
                .name("merge 2 clusters with min dist");

        DataSet<Cluster<T>> step = clustersWithDist.join(min)
                .where(value -> true)
                .equalTo(value -> true)
                .with(new ClusterCleaner<T>())
                .distinct(value -> value.id)
                .name("clean after merge")
                .union(min)
                .name("add merged cluster");

        DataSet<Integer> termination = iteration
                .map(value -> 1)
                .reduce((value1, value2) -> value1 + value2)
                .map(new PrintFunction<>("count:"))
                .filter(value -> value > 2);

        return iteration.closeWith(step, termination);
    }
}
