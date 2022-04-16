package by.vsu.kovzov.source;

import by.vsu.kovzov.config.MyBatisConfig;
import by.vsu.kovzov.function.load.StudentMapFunction;
import by.vsu.kovzov.model.Student;
import by.vsu.kovzov.repository.StudentRepository;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.List;

public class StudentSource extends Source<Student> {
    private static final StudentRepository studentRepository = MyBatisConfig.getStudentRepository();

    public StudentSource(ExecutionEnvironment environment, StudentRepository studentRepository) {
        super(environment);
    }

    @Override
    public DataSet<Student> getDataSet() {
        List<Long> ids = studentRepository.findAllId();
        DataSet<Long> dataSet = env.fromCollection(ids);
        return dataSet
                .map(new StudentMapFunction())
                .name("read student by id");


    }
}
