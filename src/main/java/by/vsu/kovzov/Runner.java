package by.vsu.kovzov;

import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.linkage.SingleLinkage;
import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.aggregators.Aggregator;
import org.apache.flink.api.common.aggregators.DoubleSumAggregator;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.operators.IterativeDataSet;
import org.apache.flink.api.java.summarize.aggregation.ObjectSummaryAggregator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.MemorySize;
import org.apache.flink.connector.file.sink.FileSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Runner {
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

        DataSet<Tuple3<Cluster, Cluster, Double>> clustersWithDist = iteration.fullOuterJoin(clusters)
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

        DataSet<Tuple3<Cluster, Cluster, Double>> min = clustersWithDist
                .min(2);
        DataSet<Cluster> step = clustersWithDist.rightOuterJoin(min)
                .where(value -> true)
                .equalTo(value -> true)
                .with(new FlatJoinFunction<Tuple3<Cluster, Cluster, Double>, Tuple3<Cluster, Cluster, Double>, Cluster>() {
                    @Override
                    public void join(Tuple3<Cluster, Cluster, Double> c, Tuple3<Cluster, Cluster, Double> p, Collector<Cluster> out) throws Exception {
                        if (!(p.f0 == c.f0 || p.f0 == c.f1 || p.f1 == c.f1 || p.f1 == c.f0)) {
                            out.collect(p.f0);
                        }
                        if (p.equals(c)) {
                        } else {
                            out.collect(new Cluster(p.f0, p.f1, p.f2));

                        }
                    }
                })
                .distinct(value -> value.id)
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
                .filter(value -> value > 2);

        DataSet result = iteration.closeWith(step, termination);

//        result.print();
        List list = result.collect();
//        System.out.println(list);

//        DataSet<Integer> test = env.fromCollection(Arrays.asList(1));
//        System.out.println(test.reduce((value1, value2) -> value1 + value2).collect());
//        env.execute();
    }
}