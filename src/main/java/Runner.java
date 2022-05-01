import by.vsu.kovzov.HacAlgorithm;
import by.vsu.kovzov.config.MyBatisConfig;
import by.vsu.kovzov.function.distance.StudentEuclideanDistance;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Runner {

    public static void main(String[] args) throws Exception {

//        main();
        test();
    }

    public static void main() throws Exception {
        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        Source<Student> source = new StudentSource(env, MyBatisConfig.getStudentRepository());
        Linkage<Student> linkage = new SingleLinkage<>(new StudentEuclideanDistance());

        HacAlgorithm<Student> algorithm = new HacAlgorithm<Student>(source.getDataSet(), linkage);

        DataSet<Cluster<Student>> result = algorithm.start();
        List<Cluster<Student>> list = result.collect();
        list.get(0).print();
    }

    public static void test() throws Exception {
        int size = 10_000;
        List<Double> data = new ArrayList<>(size);
//        Random random = new Random();
//        for (int i = 0; i < size; i++) {
//            data.add(random.nextDouble());
//        }

        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Double> input = env.fromCollection(Arrays.asList(1d,3d,10d, 15d));

        Linkage<Double> LINKAGE = new SingleLinkage<>((aDouble, aDouble2) -> Math.abs(aDouble - aDouble2));
        HacAlgorithm<Double> algorithm = new HacAlgorithm<>(input, LINKAGE);

        DataSet result = algorithm.start();
//        result.writeAsText("/shared/output");
        List<Cluster> list = result.collect();
        list.get(0).print();
//        System.out.println(list);
//        env.execute();
    }
}