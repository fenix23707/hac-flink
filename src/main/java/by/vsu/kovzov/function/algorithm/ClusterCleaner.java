package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

public class ClusterCleaner<T> implements FlatJoinFunction<Tuple3<Cluster<T>, Cluster<T>, Double>, Cluster<T>, Cluster<T>> {
    @Override
    public void join(Tuple3<Cluster<T>, Cluster<T>, Double> c, Cluster<T> p, Collector<Cluster<T>> out) throws Exception {
        if (!c.f0.equals(p.leftChild) && !c.f0.equals(p.rightChild)) {
            out.collect(c.f0);
        }
        if (!c.f1.equals(p.leftChild) && !c.f1.equals(p.rightChild)) {
            out.collect(c.f1);
        }
    }
}
