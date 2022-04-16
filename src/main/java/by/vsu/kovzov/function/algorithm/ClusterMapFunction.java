package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.MapFunction;

public class ClusterMapFunction<T> implements MapFunction<T, Cluster<T>> {
    @Override
    public Cluster<T> map(T value) throws Exception {
        return new Cluster<>(value);
    }
}
