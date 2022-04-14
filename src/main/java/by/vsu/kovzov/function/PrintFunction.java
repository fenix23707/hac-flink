package by.vsu.kovzov.function;

import org.apache.flink.api.common.functions.MapFunction;

public class PrintFunction<T> implements MapFunction<T, T> {
    private String info;

    public PrintFunction(String info) {
        this.info = info;
    }

    @Override
    public T map(T value) throws Exception {
//        System.out.println(info + " " + value);
        return value;
    }
}
