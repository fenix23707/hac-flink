package by.vsu.kovzov;

import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.linkage.SingleLinkage;
import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.util.Arrays;
import java.util.List;

public class Runner {
    //TODO: add thread safe here
    private static final Linkage<Double> LINKAGE = new SingleLinkage<>((aDouble, aDouble2) -> Math.abs(aDouble - aDouble2));
    private static final int BOUND = 100;

    private static final OutputTag<Tuple5<Integer, Integer, Integer, Integer, Integer>>
            ITERATE_TAG =
            new OutputTag<Tuple5<Integer, Integer, Integer, Integer, Integer>>(
                    "iterate") {
            };

    public static void main(String[] args) throws Exception {
        // Checking input parameters
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up input for the stream of integer pairs

        // obtain execution environment and set setBufferTimeout to 1 to enable
        // continuous flushing of the output buffers (lowest latency)
        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Double> input = env.fromCollection(Arrays.asList(2d, 3d, 9d));
        DataSet<Cluster> clusters = input.map(new MapFunction<Double, Cluster>() {
            @Override
            public Cluster map(Double value) throws Exception {
                return new Cluster(value);
            }
        });
//        DataSet<Tuple3<Cluster, Cluster, Double>> linkage_value = ;
//
//        linkage_value.print();

        IterativeDataSet<Cluster> iteration = clusters.iterate(Integer.MAX_VALUE);

        DataSet<Tuple3<Cluster, Cluster, Double>> clustersWithDist = iteration.join(iteration)
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
                .map(new MapFunction<Tuple3<Cluster, Cluster, Double>, Tuple3<Cluster, Cluster, Double>>() {
                    @Override
                    public Tuple3<Cluster, Cluster, Double> map(Tuple3<Cluster, Cluster, Double> value) throws Exception {
                        System.out.println("distances: " + value);
                        return value;
                    }
                })
                .reduce(new ReduceFunction<Tuple3<Cluster, Cluster, Double>>() {
                    @Override
                    public Tuple3<Cluster, Cluster, Double> reduce(Tuple3<Cluster, Cluster, Double> value1, Tuple3<Cluster, Cluster, Double> value2) throws Exception {
                        return value1.f2 < value2.f2 ? value1 : value2;
                    }
                })
                .map(new MapFunction<Tuple3<Cluster, Cluster, Double>, Cluster>() {
                    @Override
                    public Cluster map(Tuple3<Cluster, Cluster, Double> value) throws Exception {
                        System.out.println("min: " + value);
                        return new Cluster(value.f0, value.f1, value.f2);
                    }
                });
        DataSet<Cluster> step = clustersWithDist.join(min)
                .where(new KeySelector<Tuple3<Cluster, Cluster, Double>, Boolean>() {
                    @Override
                    public Boolean getKey(Tuple3<Cluster, Cluster, Double> value) throws Exception {
                        return true;
                    }
                })
                .equalTo(new KeySelector<Cluster, Boolean>() {
                    @Override
                    public Boolean getKey(Cluster value) throws Exception {
                        return true;
                    }
                })
                .with(new FlatJoinFunction<Tuple3<Cluster, Cluster, Double>, Cluster, Cluster>() {
                    @Override
                    public void join(Tuple3<Cluster, Cluster, Double> c, Cluster p, Collector<Cluster> out) throws Exception {
                        if (!c.f0.equals(p.left_child) && !c.f0.equals(p.right_child)) {
                            out.collect(c.f0);
                        }
                        if (!c.f1.equals(p.left_child) && !c.f1.equals(p.right_child)) {
                            out.collect(c.f1);
                        }
                    }
                })
                .distinct(value -> value.id)
                .union(min)
                .map(new MapFunction<Cluster, Cluster>() {
                    @Override
                    public Cluster map(Cluster value) throws Exception {
                        System.out.println(value);
                        return value;
                    }
                });

        DataSet<Integer> termination = iteration
                .map(value -> 1)
                .reduce((value1, value2) -> value1 + value2)
                .map(new MapFunction<Integer, Integer>() {
                    @Override
                    public Integer map(Integer value) throws Exception {
                        System.out.println("count: " + value);
                        System.out.println();
                        return value;
                    }
                })
                .filter(value -> value < 4);

        DataSet result = iteration.closeWith(step, termination);

//        result.print();
        List list = result.collect();
//        System.out.println(list);

//        DataSet<Integer> test = env.fromCollection(Arrays.asList(1));
//        System.out.println(test.reduce((value1, value2) -> value1 + value2).collect());
//        env.execute();
    }
}