package org.siani.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Cluster {

    private String id;
    private final Collection<String> items;
    private final List<Cluster> clusters;

    public Cluster(String id, Collection<String> items) {
        this.id = id;
        this.items = items;
        clusters = new ArrayList<>();
    }

    public String id() {
        return id;
    }

    public String[] items() {
        return items.toArray(new String[items.size()]);
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

    protected Cluster[] allClusters() {
        List<Cluster> clusters = new ArrayList<>();
        clusters.add(this);
        for (Cluster cluster : this.clusters)
            clusters.addAll(Arrays.asList(cluster.allClusters()));
        return clusters.toArray(new Cluster[clusters.size()]);
    }

    protected void add(Cluster cluster){
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
