package by.vsu.kovzov.function.load;

import by.vsu.kovzov.config.MyBatisConfig;
import by.vsu.kovzov.model.ReceivedMark;
import by.vsu.kovzov.model.Student;
import by.vsu.kovzov.repository.ReceivedMarkRepository;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.util.List;

public class StudentEnrichment extends RichMapFunction<Student, Student> {
    private transient ReceivedMarkRepository receivedMarkRepository;

    @Override
    public void open(Configuration parameters) throws Exception {
        receivedMarkRepository = MyBatisConfig.getReceivedMarkRepository();
    }

    @Override
    public Student map(Student student) throws Exception {
        List<ReceivedMark> receivedMarks = receivedMarkRepository.findAllByStudentId(student.getId());
        student.setReceivedMarks(receivedMarks);
        return student;
    }
}
