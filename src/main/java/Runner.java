import by.vsu.kovzov.HacAlgorithm;
import by.vsu.kovzov.config.MyBatisConfig;
import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.linkage.SingleLinkage;
import by.vsu.kovzov.model.Student;
import by.vsu.kovzov.source.Source;
import by.vsu.kovzov.source.StudentSource;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.Arrays;
import java.util.List;

public class Runner {

    public static void main(String[] args) throws Exception {

        main();
//        test();
    }

    public static void main() throws Exception {
        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();

        Source<Student> source = new StudentSource(env, MyBatisConfig.getStudentRepository());
        System.out.println(source.getDataSet().collect());
//        Linkage<Student> linkage = new SingleLinkage<>((s1, s2) -> 1d);
//
//        HacAlgorithm<Student> algorithm = new HacAlgorithm<Student>(source.getDataSet(), linkage);
//
//        DataSet result = algorithm.start();
//
//        List list = result.collect();
//        System.out.println(list);
    }

    public static void test() throws Exception {
        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Double> input = env.fromCollection(Arrays.asList(1d, 3d, 6d, 9d, 10d));

        Linkage<Double> LINKAGE = new SingleLinkage<>((aDouble, aDouble2) -> Math.abs(aDouble - aDouble2));
        HacAlgorithm<Double> algorithm = new HacAlgorithm<>(input, LINKAGE);

        DataSet result = algorithm.start();

        List list = result.collect();
        System.out.println(list);
    }
}