import by.vsu.kovzov.HacAlgorithm;
import by.vsu.kovzov.config.MyBatisConfig;
import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.linkage.SingleLinkage;
import by.vsu.kovzov.model.Cluster;
import by.vsu.kovzov.model.ReceivedMark;
import by.vsu.kovzov.model.SerializableBiFunction;
import by.vsu.kovzov.model.Student;
import by.vsu.kovzov.source.Source;
import by.vsu.kovzov.source.StudentSource;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Runner {

    public static void main(String[] args) throws Exception {

        main();
//        test();
    }

    public static void main() throws Exception {
        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();

        Source<Student> source = new StudentSource(env, MyBatisConfig.getStudentRepository());

        SerializableBiFunction<Student, Student, Double> distanceFunc = (s1, s2) -> {
            List<ReceivedMark> marks1 = s1.getReceivedMarks();
            List<ReceivedMark> marks2 = s2.getReceivedMarks();
            if (marks1.size() != marks2.size()) {
                String msg = String.format("marks size : %d for student: %d; marks size : %d for student: %d are not equal",
                        marks1.size(), s1.getId(),
                        marks2.size(), s2.getId());
                throw new RuntimeException(msg);
            }

            // Euclidean distance
            return (double) IntStream.range(0, marks1.size())
                    .parallel()
                    .map(index -> {
                        int temp = (marks1.get(index).getMark().getValue() - marks2.get(index).getMark().getValue());
                        return temp * temp;
                    })
                    .sum();
        };
        Linkage<Student> linkage = new SingleLinkage<>(distanceFunc);

        HacAlgorithm<Student> algorithm = new HacAlgorithm<Student>(source.getDataSet(), linkage);

        DataSet<Cluster<Student>> result = algorithm.start();

        List<Cluster<Student>> list = result.collect();
        list.get(0).print();
    }

    public static void test() throws Exception {
        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Double> input = env.fromCollection(Arrays.asList(1d, 3d, 6d, 9d, 10d));

        Linkage<Double> LINKAGE = new SingleLinkage<>((aDouble, aDouble2) -> Math.abs(aDouble - aDouble2));
        HacAlgorithm<Double> algorithm = new HacAlgorithm<>(input, LINKAGE);

        DataSet result = algorithm.start();

        List<Cluster> list = result.collect();
        list.get(0).print();
//        System.out.println(list);
    }
}