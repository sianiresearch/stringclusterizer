package org.siani.cluster;

public class NullItem implements Item{

    private static NullItem instance = new NullItem();

    static Item instance(){
        return instance;
    }

    private NullItem(){
    }

    @Override
    public String id() {
        return "null";
    }

    @Override
    public Object get() {
        return this;
    }

    @Override
    public Item parent() {
        return NullItem.instance();
    }

    @Override
    public ItemList items() {
        return new ItemList();
    }

    @Override
    public ItemList groupOfParent() {
        return new ItemList();
    }

    @Override
    public boolean isGroup() {
        return false;
    }
}
