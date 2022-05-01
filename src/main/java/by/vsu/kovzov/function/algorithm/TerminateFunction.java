package by.vsu.kovzov.function.algorithm;

import by.vsu.kovzov.model.Cluster;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.Iterator;

public class TerminateFunction<T> implements GroupReduceFunction<Cluster<T>, Integer> {
    private int skip;

    public TerminateFunction(int skip) {
        this.skip = skip;
    }

    @Override
    public void reduce(Iterable<Cluster<T>> values, Collector<Integer> out) throws Exception {
        Iterator iterator = values.iterator();
        for (int i = 0; i < skip; i++) {
            if (iterator.hasNext()) {
                iterator.next();
            }
        }

        if (iterator.hasNext()) {
            out.collect(1);
        }
    }
}
