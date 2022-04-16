package by.vsu.kovzov.config;

import by.vsu.kovzov.repository.StudentRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisConfig {
    private static final StudentRepository studentRepository;

    static {
        try {
            Reader reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            studentRepository = sqlSessionFactory.openSession().getMapper(StudentRepository.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static StudentRepository getStudentRepository() {
        return studentRepository;
    }
}
