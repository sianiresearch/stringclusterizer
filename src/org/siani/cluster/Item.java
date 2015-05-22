package org.siani.cluster;

public interface Item<T> {
    String id();
    /** Check item is not a group before calling this method **/
    T get();
    Item<T> parent();
    ItemList<T> items();
    ItemList<T> group();
    boolean isGroup();

}
