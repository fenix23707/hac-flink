package by.vsu.kovzov.repository;

import by.vsu.kovzov.model.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentRepository {
    @Select("SELECT * FROM student")
    List<Student> findAll();
}
