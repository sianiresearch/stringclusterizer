package org.siani.cluster;

class NullItem implements Item{

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
        return null;
    }

    @Override
    public Item parent() {
        return this;
    }

    @Override
    public ItemList items() {
        return new ItemList();
    }

    @Override
    public boolean isGroup() {
        return false;
    }
}
