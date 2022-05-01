package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.CrossFunction;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

public class DistanceCalculator<T> implements MapFunction<Tuple2<Cluster<T>, Cluster<T>>, Tuple3<Cluster<T>, Cluster<T>, Double>> {
    private Linkage<T> linkage;

    public DistanceCalculator(Linkage<T> linkage) {
        this.linkage = linkage;
    }

    @Override
    public Tuple3<Cluster<T>, Cluster<T>, Double> map(Tuple2<Cluster<T>, Cluster<T>> value) throws Exception {
        return new Tuple3<>(value.f0, value.f1, linkage.calc(value.f0, value.f1));
    }
}
