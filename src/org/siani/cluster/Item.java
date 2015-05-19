package org.siani.cluster;

public interface Item<T> {

    T get();
    boolean hasChildren();

}
