package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

public class DistanceCalculator<T> implements FlatJoinFunction<Cluster<T>, Cluster<T>, Tuple3<Cluster<T>, Cluster<T>, Double>> {
    private Linkage<T> linkage;

    public DistanceCalculator(Linkage<T> linkage) {
        this.linkage = linkage;
    }

    @Override
    public void join(Cluster<T> с1, Cluster<T> с2, Collector<Tuple3<Cluster<T>, Cluster<T>, Double>> out) throws Exception {
        if (!с1.equals(с2)) {
            out.collect(new Tuple3<>(с1, с2, linkage.calc(с1, с2)));
        }
    }
}
