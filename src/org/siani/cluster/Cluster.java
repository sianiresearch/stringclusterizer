package org.siani.cluster;

import java.util.ArrayList;
import java.util.List;

public class Cluster<T> {

    private String id;
    private final List<T> items;
    private final List<Cluster<T>> clusters;

    public Cluster(String id, List<T> items) {
        this.id = id;
        this.items = items;
        clusters = new ArrayList<>();
    }

    public String id() {
        return id;
    }

    public List<T> items() {
        return items;
    }

    public Cluster[] clusters() {
        return clusters.toArray(new Cluster[clusters.size()]);
    }

    @Override
    public String toString() {
        return toString("");
    }

    protected void id(String id) {
        this.id = id;
    }

    protected List<Cluster<T>> allClusters() {
        List<Cluster<T>> clusters = new ArrayList<>();
        clusters.add(this);
        for (Cluster<T> cluster : this.clusters)
            clusters.addAll(cluster.allClusters());
        return clusters;
    }

    protected void add(Cluster<T> cluster){
        clusters.add(cluster);
    }

    protected int size() {
        return items.size();
    }

    private String toString(String prefix) {
        String result = prefix  + "Cluster " + (id.isEmpty() ? "*" : id) + ", items=" + items;
        for (Cluster cluster : clusters) result += "\n" + cluster.toString(prefix + "\t");
        return result;
    }
}
