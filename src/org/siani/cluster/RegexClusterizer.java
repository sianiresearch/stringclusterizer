package org.siani.cluster;

import java.util.List;
import java.util.Map;

public class RegexClusterizer<T> extends Clusterizer<T> {

    private String pattern;

    public RegexClusterizer() {
    }

    public RegexClusterizer(StringExtractor<T> extractor) {
        super(extractor);
    }

    public RegexClusterizer<T> split(String pattern) {
        this.pattern = pattern;
        return this;
    }

    @Override
    protected Map<String, List<T>> buildClusterizationMap(Cluster<T> cluster) {
        Map<String, List<T>> map = buildMap();
        for (Item<T> element : cluster.elements()) {
            String suffix = extract(element.get()).substring(cluster.id().length());
            String[] split = suffix.split(pattern);
            if(split.length == 1) continue;
            map.get(key(cluster, split)).add(element.get());
        }
        return map;
    }

    private String key(Cluster<T> cluster, String[] split) {
        return (cluster.id().isEmpty() ? "" : cluster.id() + pattern) + (split[0].isEmpty() ? split[1] : split[0]);
    }
}
