package com.ri.generalFramework.model;

/**
 * Created by ri on 2019/10/31.
 */

public class Param2<T, K> implements java.io.Serializable {
    private T t;

    private K k;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }
}

