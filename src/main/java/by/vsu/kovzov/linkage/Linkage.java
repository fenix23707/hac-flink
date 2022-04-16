package by.vsu.kovzov.linkage;

import by.vsu.kovzov.model.Cluster;
import by.vsu.kovzov.model.SerializableBiFunction;

import java.io.Serializable;

abstract public class Linkage<T> implements Serializable {
    final SerializableBiFunction<T,T,Double> distanceFunction;

    /**
     * Constructor
     * @param distanceFunction calc distance between to generic objects
     */
    public Linkage(SerializableBiFunction<T,T,Double> distanceFunction){
        this.distanceFunction = distanceFunction;
    }

    public abstract double calc(Cluster<T> a, Cluster<T> b);
}
