package by.vsu.kovzov.config;

import by.vsu.kovzov.repository.ReceivedMarkRepository;
import by.vsu.kovzov.repository.StudentRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisConfig {
    private static final StudentRepository STUDENT_REPOSITORY;
    private static final ReceivedMarkRepository RECEIVED_MARK_REPOSITORY;

    static {
        try {
            Reader reader = Resources
                    .getResourceAsReader("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(reader);

            STUDENT_REPOSITORY = sqlSessionFactory.openSession().getMapper(StudentRepository.class);
            RECEIVED_MARK_REPOSITORY = sqlSessionFactory.openSession().getMapper(ReceivedMarkRepository.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static StudentRepository getStudentRepository() {
        return STUDENT_REPOSITORY;
    }

    public static ReceivedMarkRepository getReceivedMarkRepository() {
        return RECEIVED_MARK_REPOSITORY;
    }
}
