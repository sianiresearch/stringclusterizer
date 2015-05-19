package org.siani.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Cluster {

    private String id;
    private final Collection<String> elements;
    private final List<Cluster> clusters;

    public Cluster(String id, Collection<String> elements) {
        this.id = id;
        this.elements = elements;
        clusters = new ArrayList<>();
    }

    public String id() {
        return id;
    }

    public String[] elements() {
        return elements.toArray(new String[elements.size()]);
    }

    public Cluster[] clusters() {
        return clusters.toArray(new Cluster[clusters.size()]);
    }

    public Cluster[] allClusters() {
        List<Cluster> clusters = new ArrayList<>();
        clusters.add(this);
        for (Cluster cluster : this.clusters)
            clusters.addAll(Arrays.asList(cluster.allClusters()));
        return clusters.toArray(new Cluster[clusters.size()]);
    }

    public void add(Cluster cluster){
        clusters.add(cluster);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {
        return toString("");
    }

    private String toString(String prefix) {
        String result = prefix  + "Cluster " + (id.isEmpty() ? "*" : id) + ", elements=" + elements;
        for (Cluster cluster : clusters) result += "\n" + cluster.toString(prefix + "\t");
        return result;
    }

    public void id(String id) {
        this.id = id;
    }
}
