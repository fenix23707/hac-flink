package by.vsu.kovzov.source;

import by.vsu.kovzov.model.Student;
import by.vsu.kovzov.repository.StudentRepository;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class StudentSource extends Source<Student> {

    public StudentSource(ExecutionEnvironment environment) {
        super(environment);
    }

    @Override
    public DataSet<Student> getDataSet() {
        return null;
    }
}
