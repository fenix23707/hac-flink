package by.vsu.kovzov.function.load;

import by.vsu.kovzov.model.ReceivedMark;
import by.vsu.kovzov.model.Student;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.hadoop.io.ByteWritable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Deduplicator implements MapFunction<Student, Student> {
    @Override
    public Student map(Student student) throws Exception {
        List<ReceivedMark> receivedMarks = student.getReceivedMarks();
        if (student.getId() == 10l) {
            System.out.println("");
        }
        receivedMarks = receivedMarks.stream()
                .collect(Collectors.groupingBy(ReceivedMark::getDiscipline, Collectors.maxBy(Comparator.comparing(ReceivedMark::getDate))))
                .values()
                .stream()
                .map(Optional::get)
                .collect(Collectors.toList());
        student.setReceivedMarks(receivedMarks);
        return student;
    }
}
