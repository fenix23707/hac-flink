package by.vsu.kovzov.function.load;

import by.vsu.kovzov.config.MyBatisConfig;
import by.vsu.kovzov.model.Student;
import by.vsu.kovzov.repository.StudentRepository;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

public class StudentMapFunction extends RichMapFunction<Long, Student> {
    private transient StudentRepository studentRepository;

    @Override
    public void open(Configuration parameters) throws Exception {
        studentRepository = MyBatisConfig.getStudentRepository();
    }

    @Override
    public Student map(Long id) throws Exception {
        return studentRepository.findById(id);
    }
}
