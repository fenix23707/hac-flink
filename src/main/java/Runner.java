import by.vsu.kovzov.HacAlgorithm;
import by.vsu.kovzov.linkage.Linkage;
import by.vsu.kovzov.linkage.SingleLinkage;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.Arrays;
import java.util.List;

public class Runner {
    private static final Linkage<Double> LINKAGE = new SingleLinkage<>((aDouble, aDouble2) -> Math.abs(aDouble - aDouble2));

    public static void main(String[] args) throws Exception {


        ExecutionEnvironment env =
                ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Double> input = env.fromCollection(Arrays.asList(1d, 3d, 6d, 9d, 10d));

        HacAlgorithm<Double> algorithm = new HacAlgorithm<>(input, LINKAGE);

        DataSet result = algorithm.start();

        List list = result.collect();
        System.out.println(list);

    }
}