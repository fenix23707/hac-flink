package by.vsu.kovzov.source;

import by.vsu.kovzov.model.Student;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class StudentSource extends Source<Student> {
    private String filePath;

    public StudentSource(ExecutionEnvironment environment) {
        super(environment);
    }

    @Override
    public DataSet<Student> getDataSet() {
        return null;
    }
}
