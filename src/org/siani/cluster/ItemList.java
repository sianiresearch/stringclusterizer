package org.siani.cluster;

import java.util.*;

public class ItemList<T> implements Iterable<Item<T>> {

    private List<Item<T>> items = new ArrayList<>();

    public Item<T> get(int index) {
        return items.get(index);
    }

    @Override
    public Iterator<Item<T>> iterator() {
        return items.iterator();
    }

    public void sort(Comparator<T> tComparator) {
        for (Item<T> item : items) item.items().sort(tComparator);
        Collections.sort(items, buildComparator(tComparator));


    }

    public ItemList<T> groupOfParent(){
        return get(0).groupOfParent();
    }

    public Item parent(){
        return items.isEmpty() ? NullItem.instance() : items.get(0).parent();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @SuppressWarnings("unchecked")
    private Comparator<Item<T>> buildComparator(final Comparator<T> tComparator) {
        return new Comparator<Item<T>>() {
            @Override
            public int compare(Item<T> o1, Item<T> o2) {
                T i1 = o1.isGroup() ? o1.items().get(0).get() : o1.get();
                T i2 = o2.isGroup() ? o2.items().get(0).get() : o2.get();
                return tComparator.compare(i1, i2);
            }
        };
    }

    public void remove(Item<T> item) {
        items.remove(item);
    }

    public void add(Item<T> item) {
        items.add(item);
    }

    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        return toString(toArray(), "");
    }

    String toString(Item[] items, String prefix) {
        String result = "";
        for (Item item : items) result += prefix + item.id() + "\n" + toString(item.items().toArray(), prefix + "\t");
        return result;
    }

    private Item[] toArray() {
        return items.toArray(new Item[items.size()]);
    }
}
