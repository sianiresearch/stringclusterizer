package org.siani.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Cluster<T> implements Item {

    private String id;
    private final List<Item> items;
    private StringExtractor<T> extractor;

    protected Cluster(String id, List<T> items, StringExtractor<T> extractor) {
        this.id = id;
        this.items = buildItems(items);
        this.extractor = extractor;
    }

    public String id() {
        return id;
    }

    @Override
    public Cluster get() {
        return this;
    }

    @Override
    public List<Item> items(){
        return Collections.unmodifiableList(items);
    }

    @SuppressWarnings("unchecked")
    public List<Item<T>> elements() {
        List<Item<T>> elements = new ArrayList<>();
        for (Item item : items)
            if (!item.isCluster()) elements.add(item);
            else elements.addAll(((Cluster<T>)item.get()).elements());
        return Collections.unmodifiableList(elements);
    }

    @SuppressWarnings("unchecked")
    public List<Cluster<T>> clusters() {
        List<Cluster<T>> clusters = new ArrayList<>();
        for (Item item : items)
            if (item.isCluster()) clusters.add((Cluster<T>) item);
        return Collections.unmodifiableList(clusters);
    }

    @Override
    public boolean isCluster() {
        return true;
    }

    @Override
    public String toString() {
        return toString("");
    }

    public int size() {
        return elements().size();
    }

    protected void id(String id) {
        this.id = id;
    }

    protected List<Cluster<T>> allClusters() {
        List<Cluster<T>> clusters = new ArrayList<>();
        clusters.add(this);
        for (Cluster<T> cluster : clusters())
            clusters.addAll(cluster.allClusters());
        return clusters;
    }

    protected void add(Cluster<T> cluster){
        for (Item<T> item : cluster.elements()) items.remove(item);
        items.add(cluster);
    }

    private String toString(String prefix) {
        String result = prefix  + "Cluster " + (id.isEmpty() ? "*" : id) + ", elements=" + items;
        for (Cluster cluster : clusters()) result += "\n" + cluster.toString(prefix + "\t");
        return result;
    }

    public void sort(Comparator<T> tComparator) {
        for (Cluster<T> cluster : clusters()) cluster.sort(tComparator);
        Collections.sort(items, buildComparator(tComparator));
    }

    @SuppressWarnings("unchecked")
    private Comparator<? super Item> buildComparator(final Comparator<T> tComparator) {
        return new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                T i1 = !o1.isCluster() ? (T)o1.get() : ((Cluster<T>)o1.get()).elements(0);
                T i2 = !o2.isCluster() ? (T)o2.get() : ((Cluster<T>)o2.get()).elements(0);
                return tComparator.compare(i1, i2);
            }
        };
    }

    private T elements(int i) {
        return elements().get(i).get();
    }

    private List<Item> buildItems(List<T> items) {
        List<Item> itemList = new ArrayList<>();
        for (final T item : items) itemList.add(buildItem(item));
        return itemList;
    }

    private Item buildItem(final T item) {
        return new Item() {
            @Override
            public String id() {
                return extractor.extract(item);
            }

            @Override
            public Object get() {
                return item;
            }

            @Override
            public List<Item<T>> items() {
                return new ArrayList<>();
            }

            @Override
            public boolean isCluster() {
                return false;
            }

            @Override
            @SuppressWarnings("unchecked")
            public boolean equals(Object obj) {
                return obj instanceof Item && item.equals(((Item<T>) obj).get());
            }

            @Override
            public String toString() {
                return id();
            }
        };
    }
}
