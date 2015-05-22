package org.siani.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Cluster<T> implements Item {

    private String id;
    private Item parent;
    private final ItemList<T> items;
    private StringExtractor<T> extractor;

    protected Cluster(String id, List<T> items, StringExtractor<T> extractor) {
        this.id = id;
        this.parent = NullItem.instance();
        this.items = buildItems(items);
        this.extractor = extractor;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public Cluster get() {
        return this;
    }

    @Override
    public Item parent() {
        return parent;
    }

    @Override
    public ItemList<T> items(){
        return items;
    }

    @Override
    public ItemList groupOfParent() {
        return parent().parent().items();
    }

    @SuppressWarnings("unchecked")
    List<Item<T>> elements() {
        List<Item<T>> elements = new ArrayList<>();
        for (Item item : items)
            if (!item.isGroup()) elements.add(item);
            else elements.addAll(((Cluster<T>)item.get()).elements());
        return Collections.unmodifiableList(elements);
    }

    @SuppressWarnings("unchecked")
    List<Cluster<T>> clusters() {
        List<Cluster<T>> clusters = new ArrayList<>();
        for (Item item : items)
            if (item.isGroup()) clusters.add((Cluster<T>) item);
        return Collections.unmodifiableList(clusters);
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    void id(String id) {
        this.id = id;
    }

    List<Cluster<T>> allClusters() {
        List<Cluster<T>> clusters = new ArrayList<>();
        clusters.add(this);
        for (Cluster<T> cluster : clusters())
            clusters.addAll(cluster.allClusters());
        return clusters;
    }

    @SuppressWarnings("unchecked")
    void add(Cluster<T> cluster){
        cluster.parent = this;
        for (Item<T> item : cluster.elements()) items.remove(item);
        items.add(cluster);
    }

    private ItemList<T> buildItems(List<T> items) {
        ItemList<T> itemList = new ItemList<>();
        for (final T item : items) itemList.add(buildItem(item));
        return itemList;
    }

    private Item<T> buildItem(final T item) {
        return new Item<T>() {
            @Override
            public String id() {
                return extractor.extract(item);
            }

            @Override
            public T get() {
                return item;
            }

            @Override
            public Item parent() {
                return Cluster.this;
            }

            @Override
            public ItemList<T> items() {
                return new ItemList<>();
            }

            @Override
            @SuppressWarnings("unchecked")
            public ItemList<T> groupOfParent() {
                return parent().parent().items();
            }

            @Override
            public boolean isGroup() {
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
