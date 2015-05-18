package org.siani.cluster;

import java.util.*;
import java.util.stream.Collectors;

public class Clusterizer {

    private Cluster cluster;

    public static Cluster execute(String... strings) {
        return new Clusterizer().doExecute(strings);
    }

    private Cluster doExecute(String[] elements) {
        cluster = new Cluster("", Arrays.asList(elements));
        process(elements);
        return cluster;
    }

    private void process(String[] elements) {
        int largestSize = largestElementSize(elements);
        for (int i = 0; i < largestSize; i++) processIndex(i);
    }

    private void processIndex(int index) {
        getLeafClusters().forEach(c -> clusterizeIndex(c, index));
    }

    private void clusterizeIndex(Cluster cluster, int index) {
        Map<String, List<String>> map = buildClusterizationMap(cluster, index);
        if (map.size() == 1 && !map.keySet().iterator().next().equals("short")) cluster.id(map.keySet().iterator().next());
        else if (map.size() != cluster.elements().length)
            map.entrySet().stream().filter(e -> !e.getKey().equals("short")).forEach(e -> cluster.add(new Cluster(e.getKey(), e.getValue())));
    }

    private Map<String, List<String>> buildClusterizationMap(Cluster cluster, int index) {
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>() {
            @Override
            public List<String> get(Object key) {
                if (!containsKey(key)) put((String) key, new ArrayList<>());
                return super.get(key);
            }
        };
        Arrays.asList(cluster.elements()).stream()
                .forEach(s -> map.get((index < s.length() ? s.substring(0, index + 1) : "short")).add(s));
        return map;
    }

    private List<Cluster> getLeafClusters() {
        return Arrays.asList(cluster.allClusters()).stream().filter(c -> c.clusters().length == 0).collect(Collectors.toList());
    }

    private int largestElementSize(String[] elements) {
        return Arrays.asList(elements).stream().reduce((s, s2) -> s.length() > s2.length() ? s : s2).get().length();
    }

}
