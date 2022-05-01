package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.CrossFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public class CartesianProduct<T> implements CrossFunction<Cluster<T>, Cluster<T>, Tuple2<Cluster<T>, Cluster<T>>>{
    @Override
    public Tuple2<Cluster<T>, Cluster<T>> cross(Cluster<T> val1, Cluster<T> val2) throws Exception {
        if (val1.hashCode() < val2.hashCode()) {
            Cluster<T> temp = val1;
            val1 = val2;
            val2 = temp;
        }
        return new Tuple2<>(val1, val2);
    }
}
