package by.vsu.kovzov.linkage;

import by.vsu.kovzov.model.Cluster;

import java.util.function.BiFunction;

abstract public class Linkage<T> {
    final BiFunction<T,T,Double> distanceFunction;

    /**
     * Constructor
     * @param distanceFunction calc distance between to generic objects
     */
    public Linkage(BiFunction<T,T,Double> distanceFunction){
        this.distanceFunction = distanceFunction;
    }

    public abstract double calc(Cluster<T> a, Cluster<T> b);
}
