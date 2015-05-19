package org.siani.cluster;

import java.util.*;

public class Clusterizer<T> {

    final StringExtractor<T> extractor;

    public Clusterizer(){
        this.extractor = new StringExtractor<T>() {
            @Override
            public String extract(T object) {
                return object.toString();
            }
        };
    }

    public Clusterizer(StringExtractor<T> extractor){
        this.extractor = extractor;
    }

    @SafeVarargs
    public final Cluster<T> clusterize(T... elements) {
        Cluster<T> cluster = new Cluster<>("", Arrays.asList(elements));
        process(cluster);
        return cluster;
    }

    private void process(Cluster<T> cluster) {
        int largestSize = largestElementSize(cluster.items());
        for (int i = 0; i < largestSize; i++) processIndex(getLeafClusters(cluster), i);
    }

    private void processIndex(List<Cluster<T>> leafClusters, int index) {
        for (Cluster<T> cluster : leafClusters) clusterizeIndex(cluster, index);
    }

    private void clusterizeIndex(Cluster<T> cluster, int index) {
        Map<String, List<T>> map = buildClusterizationMap(cluster, index);
        if (thereIsOnlyOneCluster(map, cluster)) updateClusterId(cluster, map);
        else createClusters(cluster, map);
    }

    private Map<String, List<T>> buildClusterizationMap(Cluster<T> cluster, int index) {
        Map<String, List<T>> map = buildMap();
        for (T element : cluster.items()) {
            if (extract(element).length() <= index) continue;
            map.get(extract(element).substring(0, index + 1)).add(element);
        }
        return map;
    }

    private String extract(T element) {
        return extractor.extract(element);
    }

    private boolean thereIsOnlyOneCluster(Map<String, List<T>> map, Cluster cluster) {
        return map.size() == 1 && map.values().iterator().next().size() == cluster.items().size();
    }

    private void updateClusterId(Cluster cluster, Map<String, List<T>> map) {
        cluster.id(map.keySet().iterator().next());
    }

    private void createClusters(Cluster<T> cluster, Map<String, List<T>> map) {
        for (String key : map.keySet()) {
            if (map.get(key).size() == 1) continue;
            cluster.add(new Cluster<>(key, map.get(key)));
        }
    }

    private LinkedHashMap<String, List<T>> buildMap() {
        return new LinkedHashMap<String, List<T>>() {
            @Override
            public List<T> get(Object key) {
                if (!containsKey(key)) put((String) key, new ArrayList<T>());
                return super.get(key);
            }
        };
    }

    private List<Cluster<T>> getLeafClusters(Cluster<T> root) {
        List<Cluster<T>> clusters = new ArrayList<>();
        for (Cluster<T> cluster : root.allClusters()) {
            if (cluster.clusters().size() > 0) continue;
            clusters.add(cluster);
        }
        return clusters;
    }

    private int largestElementSize(List<T> elements) {
        int largestSize = 0;
        for (T element : elements)
            largestSize = largestSize < extract(element).length() ? extract(element).length() : largestSize;
        return largestSize;
    }

}