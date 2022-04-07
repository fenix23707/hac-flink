package by.vsu.kovzov.linkage;

import by.vsu.kovzov.model.Cluster;
import org.apache.commons.math3.util.Pair;

import java.util.HashMap;
import java.util.function.BiFunction;

abstract public class Linkage<T> {
    final BiFunction<T,T,Double> distancefunction;
    final HashMap<Pair<T,T>,Double> pairwise_distances= new HashMap();
    /**
     * Constructor
     * @param distancefunction calc distance between to generic objects
     */
    public Linkage(BiFunction<T,T,Double> distancefunction){
        this.distancefunction = distancefunction;
    }
    public abstract double calc(Cluster<T> a, Cluster<T> b);
}
