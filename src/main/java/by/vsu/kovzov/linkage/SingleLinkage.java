package by.vsu.kovzov.linkage;

import by.vsu.kovzov.model.Cluster;
import by.vsu.kovzov.model.SerializableBiFunction;

public class SingleLinkage <T> extends Linkage<T>{
    public SingleLinkage(SerializableBiFunction<T, T, Double> distanceFunction) {
        super(distanceFunction);
    }

    public double calc(Cluster<T> a, Cluster<T> b) {
        double min = Double.MAX_VALUE;
        for (T p1 : a.getClusterElements())
            for(T p2: b.getClusterElements()){
                double dis =-1;
                dis = distanceFunction.apply(p1,p2);
                if (dis < min) {
                    min = dis;
                }
            }
        return min;
    }
}
