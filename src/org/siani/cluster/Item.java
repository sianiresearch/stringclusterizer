package org.siani.cluster;

import java.util.List;

public interface Item<T> {
    String id();
    T get();
    List<Item<T>> children();
    boolean isCluster();
}
