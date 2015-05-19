package org.siani.cluster;

import java.util.*;

public class Clusterizer {

    private static final String SHORT = "short";

    public static Cluster execute(String... strings) {
        return new Clusterizer().doExecute(strings);
    }

    private Cluster doExecute(String[] elements) {
        Cluster cluster = new Cluster("", Arrays.asList(elements));
        process(cluster, elements);
        return cluster;
    }

    private void process(Cluster cluster, String[] elements) {
        int largestSize = largestElementSize(elements);
        for (int i = 0; i < largestSize; i++) processIndex(getLeafClusters(cluster), i);
    }

    private void processIndex(List<Cluster> leafClusters, int index) {
        for (Cluster cluster : leafClusters) clusterizeIndex(cluster, index);
    }

    private void clusterizeIndex(Cluster cluster, int index) {
        Map<String, List<String>> map = buildClusterizationMap(cluster, index);
        if (thereIsOnlyOneCluster(map))
            updateClusterId(cluster, map);
        else if (thereAreLessClustersThanElements(map, cluster))
            createClusters(cluster, map);
    }

    private Map<String, List<String>> buildClusterizationMap(Cluster cluster, int index) {
        Map<String, List<String>> map = buildMap();
        for (String element : cluster.elements())
            map.get(index < element.length() ? element.substring(0, index + 1) : SHORT).add(element);
        return map;
    }

    private boolean thereIsOnlyOneCluster(Map<String, List<String>> map) {
        return map.size() == 1 && !map.keySet().iterator().next().equals(SHORT);
    }

    private void updateClusterId(Cluster cluster, Map<String, List<String>> map) {
        cluster.id(map.keySet().iterator().next());
    }

    private boolean thereAreLessClustersThanElements(Map<String, List<String>> map, Cluster cluster) {
        return map.size() != cluster.elements().length;
    }

    private void createClusters(Cluster cluster, Map<String, List<String>> map) {
        for (String key : map.keySet()) {
            if(key.equals(SHORT)) continue;
            cluster.add(new Cluster(key, map.get(key)));
        }
    }

    private LinkedHashMap<String, List<String>> buildMap() {
        return new LinkedHashMap<String, List<String>>() {
            @Override
            public List<String> get(Object key) {
                if (!containsKey(key)) put((String) key, new ArrayList<String>());
                return super.get(key);
            }
        };
    }

    private List<Cluster> getLeafClusters(Cluster root) {
        List<Cluster> clusters = new ArrayList<>();
        for (Cluster cluster : root.allClusters()) {
            if(cluster.clusters().length > 0) continue;
            clusters.add(cluster);
        }
        return clusters;
    }

    private int largestElementSize(String[] elements) {
        int largestSize = 0;
        for (String element : elements) largestSize = largestSize < element.length() ? element.length() : largestSize;
        return largestSize;
    }

}
