package org.siani.cluster;

import java.util.*;

public class Clusterizer {

    public static Cluster execute(String... strings) {
        return new Clusterizer().doExecute(strings);
    }

    private Cluster doExecute(String[] elements) {
        Cluster cluster = new Cluster("", Arrays.asList(elements));
        process(cluster);
        return cluster;
    }

    private void process(Cluster cluster) {
        int largestSize = largestElementSize(cluster.items());
        for (int i = 0; i < largestSize; i++) processIndex(getLeafClusters(cluster), i);
    }

    private void processIndex(List<Cluster> leafClusters, int index) {
        for (Cluster cluster : leafClusters) clusterizeIndex(cluster, index);
    }

    private void clusterizeIndex(Cluster cluster, int index) {
        Map<String, List<String>> map = buildClusterizationMap(cluster, index);
        if (thereIsOnlyOneCluster(map, cluster)) updateClusterId(cluster, map);
        else createClusters(cluster, map);
    }

    private Map<String, List<String>> buildClusterizationMap(Cluster cluster, int index) {
        Map<String, List<String>> map = buildMap();
        for (String element : cluster.items()) {
            if (element.length() <= index) continue;
            map.get(element.substring(0, index + 1)).add(element);
        }
        return map;
    }

    private boolean thereIsOnlyOneCluster(Map<String, List<String>> map, Cluster cluster) {
        return map.size() == 1 && map.values().iterator().next().size() == cluster.items().length;
    }

    private void updateClusterId(Cluster cluster, Map<String, List<String>> map) {
        cluster.id(map.keySet().iterator().next());
    }

    private void createClusters(Cluster cluster, Map<String, List<String>> map) {
        for (String key : map.keySet()) {
            if (map.get(key).size() == 1) continue;
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
            if (cluster.clusters().length > 0) continue;
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