package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple3;

public class MinFunction<T> implements ReduceFunction<Tuple3<Cluster<T>, Cluster<T>, Double>> {
    @Override
    public Tuple3<Cluster<T>, Cluster<T>, Double> reduce(Tuple3<Cluster<T>, Cluster<T>, Double> value1, Tuple3<Cluster<T>, Cluster<T>, Double> value2) throws Exception {
        return value1.f2 < value2.f2 ? value1 : value2;
    }
}
