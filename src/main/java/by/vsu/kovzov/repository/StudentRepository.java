package by.vsu.kovzov.repository;

import by.vsu.kovzov.model.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> findAll();
}
