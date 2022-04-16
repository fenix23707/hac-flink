package by.vsu.kovzov;

import by.vsu.kovzov.config.MyBatisConfig;

public class SqlRunner {
    public static void main(String[] args) {
        System.out.println(MyBatisConfig.getStudentRepository().findById(1l));
    }
}
