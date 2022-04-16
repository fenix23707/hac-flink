package by.vsu.kovzov.repository;

import by.vsu.kovzov.model.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.util.List;

public interface StudentRepository extends Serializable {
    @Select("SELECT * FROM student")
    List<Student> findAll();

    @Select("SELECT id FROM student")
    List<Long> findAllId();

    @Select("SELECT * FROM student WHERE id = ${id}")
    Student findById(@Param("id") Long id);
}
