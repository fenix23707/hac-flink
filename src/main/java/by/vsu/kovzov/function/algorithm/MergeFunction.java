package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;

public class MergeFunction implements MapFunction<Tuple3<Cluster, Cluster, Double>, Cluster> {
    @Override
    public Cluster map(Tuple3<Cluster, Cluster, Double> value) throws Exception {
        return new Cluster(value.f0, value.f1, value.f2);
    }
}
