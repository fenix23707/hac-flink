package by.vsu.kovzov.source;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.io.Serializable;

public abstract class Source<T> implements Serializable {
    protected ExecutionEnvironment env;

    public Source(ExecutionEnvironment environment) {
        this.env = environment;
    }

    public abstract DataSet<T> getDataSet();
}
