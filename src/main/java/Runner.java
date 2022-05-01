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
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Runner {
    public static final String mainDir = "/shared/";

    public static void main(String[] args) throws Exception {

//        main();
        test(args);
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

    public static void test(String[] args) throws Exception {
        ParameterTool parameterTool = ParameterTool.fromArgs(args);

        int size = parameterTool.getInt("size", 10);
        Random random = new Random();

        List<Double> data = IntStream.range(0, size)
                .parallel()
                .mapToDouble(operand -> random.nextDouble())
                .boxed()
                .collect(Collectors.toList());

        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Double> input = env.fromCollection(data);
//        DataSet<Double> input = env.fromCollection(Arrays.asList(1d, 3d, 6d, 9d, 10d, 11d));

        Linkage<Double> LINKAGE = new SingleLinkage<>((aDouble, aDouble2) -> Math.abs(aDouble - aDouble2));
        HacAlgorithm<Double> algorithm = new HacAlgorithm<>(input, LINKAGE);

        DataSet result = algorithm.start();

//        List<Cluster> list = result.collect();
//        list.get(0).print();
//        System.out.println(list);
        result.writeAsText(mainDir + parameterTool.get("output", "output"));
        env.execute();
    }
}