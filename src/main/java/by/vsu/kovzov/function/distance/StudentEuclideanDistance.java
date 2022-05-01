package by.vsu.kovzov.function.distance;

import by.vsu.kovzov.model.ReceivedMark;
import by.vsu.kovzov.model.SerializableBiFunction;
import by.vsu.kovzov.model.Student;

import java.util.List;
import java.util.stream.IntStream;

public class StudentEuclideanDistance implements SerializableBiFunction<Student, Student, Double> {
    @Override
    public Double apply(Student s1, Student s2) {
        List<ReceivedMark> marks1 = s1.getReceivedMarks();
        List<ReceivedMark> marks2 = s2.getReceivedMarks();
        if (marks1.size() != marks2.size()) {
            String msg = String.format("marks size : %d for student: %d; marks size : %d for student: %d are not equal",
                    marks1.size(), s1.getId(),
                    marks2.size(), s2.getId());
            throw new RuntimeException(msg);
        }

        return (double) IntStream.range(0, marks1.size())
                .parallel()
                .map(index -> {
                    int temp = (marks1.get(index).getMark().getValue() - marks2.get(index).getMark().getValue());
                    return temp * temp;
                })
                .sum();
    }
}
