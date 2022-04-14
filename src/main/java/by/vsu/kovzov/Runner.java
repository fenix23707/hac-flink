package by.vsu.kovzov;

import by.vsu.kovzov.function.MergeFunction;
import by.vsu.kovzov.function.MinFunction;
import by.vsu.kovzov.function.PrintFunction;
import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.linkage.SingleLinkage;
import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.util.Collector;

import java.util.Arrays;
import java.util.List;

public class Runner {
    //TODO: add thread safe here
    private static final Linkage<Double> LINKAGE = new SingleLinkage<>((aDouble, aDouble2) -> Math.abs(aDouble - aDouble2));

    public static void main(String[] args) throws Exception {
        // Checking input parameters
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up input for the stream of integer pairs

        // obtain execution environment and set setBufferTimeout to 1 to enable
        // continuous flushing of the output buffers (lowest latency)
        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataSet<Double> input = env.fromCollection(Arrays.asList(1d, 3d, 6d, 9d, 10d));

        DataSet<Cluster> clusters = input.map(new MapFunction<Double, Cluster>() {
            @Override
            public Cluster map(Double value) throws Exception {
                return new Cluster(value);
            }
        });
        IterativeDataSet<Cluster> iteration = clusters.iterate(Integer.MAX_VALUE);

        DataSet<Tuple3<Cluster, Cluster, Double>> clustersWithDist = iteration.joinWithHuge(iteration)
                .where(value -> true)
                .equalTo(value -> true)
                .with(new FlatJoinFunction<Cluster, Cluster, Tuple3<Cluster, Cluster, Double>>() {
                    @Override
                    public void join(Cluster f0, Cluster f1, Collector<Tuple3<Cluster, Cluster, Double>> out) throws Exception {
                        if (!f0.equals(f1)) {
                            out.collect(new Tuple3<>(f0, f1, LINKAGE.calc(f0, f1)));
                        }
                    }
                })
                .distinct(value -> value.f0.id + value.f1.id);

        DataSet<Cluster> min = clustersWithDist
                .map(new PrintFunction("distance:"))
                .reduce(new MinFunction())
                .name("find min")
                .map(new MergeFunction())
                .name("merge 2 clusters with min dist");

        DataSet<Cluster> step = clustersWithDist.join(min)
                .where(value -> true)
                .equalTo(value -> true)
                .with(new FlatJoinFunction<Tuple3<Cluster, Cluster, Double>, Cluster, Cluster>() {
                    @Override
                    public void join(Tuple3<Cluster, Cluster, Double> c, Cluster p, Collector<Cluster> out) throws Exception {
                        if (!c.f0.equals(p.leftChild) && !c.f0.equals(p.rightChild)) {
                            out.collect(c.f0);
                        }
                        if (!c.f1.equals(p.leftChild) && !c.f1.equals(p.rightChild)) {
                            out.collect(c.f1);
                        }
                    }
                })
                .name("calculate distances")
                .distinct(value -> value.id)
                .union(min)
                .map(new PrintFunction<>("min: "));

        DataSet<Integer> termination = iteration
                .map(value -> 1)
                .reduce((value1, value2) -> value1 + value2)
                .map(new PrintFunction<>("count:"))
                .filter(value -> value > 2);

        DataSet result = iteration.closeWith(step, termination);

//        result.print();
        List list = result.collect();
        System.out.println(list);

//        DataSet<Integer> test = env.fromCollection(Arrays.asList(1));
//        System.out.println(test.reduce((value1, value2) -> value1 + value2).collect());
//        env.execute();
    }
}