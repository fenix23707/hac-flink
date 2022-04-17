package by.vsu.kovzov.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import static by.vsu.kovzov.model.Constants.*;

/**
 * Object Representing a Cluster of generic type
 *
 * @param <T> generic type
 */
public class Cluster<T> implements Serializable {


    final static Random RANDOM = new Random();
    final public Long id = System.currentTimeMillis() + RANDOM.nextLong();

    final public Cluster rightChild;
    final public Cluster leftChild;
    final private T obj;
    private LinkedList<T> nested_objs = null;

    final public double distance;

    public Cluster(T obj) {
        this.rightChild = null;
        this.leftChild = null;
        this.obj = obj;
        this.distance = 0;
    }

    public Cluster(Cluster rightChild, Cluster leftChild, double distance) {
        this.rightChild = rightChild;
        this.leftChild = leftChild;
        this.obj = null;
        this.distance = distance;
    }

    /**
     * @return elements of nested clusters
     */
    public Collection<T> getClusterElements() {
        if (nested_objs != null) {
            return nested_objs;
        }
        nested_objs = new LinkedList<>();
        if (this.leftChild != null) {
            nested_objs.addAll(this.leftChild.getClusterElements());
        }
        if (this.rightChild != null) {
            nested_objs.addAll(this.rightChild.getClusterElements());
        }
        if (this.obj != null) {
            nested_objs.add(obj);
        }
        return nested_objs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Cluster.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Cluster other = (Cluster) obj;
        if (this.distance == other.distance && this.getClusterElements().equals(other.getClusterElements())) {
            return true;
        }
        return false;
    }

    private void dump0(Cluster<T> node, String prefix, boolean root, boolean last) {
        System.out.println(prefix
                + (root ? "" : (last ? CH_UDIA_HOR : CH_VER_HOR))
                + (node != null && node.obj != null ? node.obj : "")
        );

        if (node == null || (node.leftChild == null && node.rightChild == null)) {
            return;
        }

        Cluster[] v = {node.leftChild, node.rightChild};

        for (int i = 0; i < v.length; ++i) {
            dump0(v[i], prefix + (root ? "" : (last ? "  " : CH_VER_SPA)), false, i + 1 >= v.length);
        }
    }

    public void print() {
        dump0(this, "", true, true);
    }

    @Override
    public String toString() {
        if (this.obj != null) {
            return obj.toString();
        } else {
            return "(" + leftChild.toString() + " , " + rightChild.toString() + ")";
        }
    }
}
