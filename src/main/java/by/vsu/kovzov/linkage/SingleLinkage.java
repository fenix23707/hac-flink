package by.vsu.kovzov.linkage;

import by.vsu.kovzov.model.Cluster;
import org.apache.commons.math3.util.Pair;

import java.util.function.BiFunction;

public class SingleLinkage <T> extends Linkage<T>{
    public SingleLinkage(BiFunction<T, T, Double> distancefunction) {
        super(distancefunction);
    }

    public double calc(Cluster<T> a, Cluster<T> b) {
        double min = Double.MAX_VALUE;
        for (T p1 : a.getClusterElements())
            for(T p2: b.getClusterElements()){
                Pair<T,T> p = new Pair(p1,p2);
                double dis =-1;
                dis = distancefunction.apply(p1,p2);

//                if(this.pairwise_distances.containsKey(p)){
//                    dis = pairwise_distances.get(p);
//                }else{
//                    dis = distancefunction.apply(p1,p2);
//                    pairwise_distances.put(p,dis);
//                }

                if(dis<min)
                    min = dis;
            }
        return min;
    }
}