package by.vsu.kovzov.source;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public abstract class Source<T> {
    private ExecutionEnvironment env;

    public Source(ExecutionEnvironment environment) {
        this.env = environment;
    }

    public abstract DataSet<T> getDataSet();
}
