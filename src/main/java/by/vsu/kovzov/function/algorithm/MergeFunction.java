package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;

public class MergeFunction<T> implements MapFunction<Tuple3<Cluster<T>, Cluster<T>, Double>, Cluster<T>> {
    @Override
    public Cluster<T> map(Tuple3<Cluster<T>, Cluster<T>, Double> value) throws Exception {
        return new Cluster(value.f0, value.f1, value.f2);
    }
}
