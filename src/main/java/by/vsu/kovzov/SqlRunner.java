package by.vsu.kovzov;

import by.vsu.kovzov.config.MyBatisConfig;
import by.vsu.kovzov.repository.StudentRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlRunner {
    public static void main(String[] args) {
        System.out.println(MyBatisConfig.getStudentRepository().findAll());
    }
}
