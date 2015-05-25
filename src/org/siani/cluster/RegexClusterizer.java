package org.siani.cluster;

import java.util.List;
import java.util.Map;

public class RegexClusterizer<T> extends Clusterizer<T> {

    private String regex;

    public RegexClusterizer() {
    }

    public RegexClusterizer(StringExtractor<T> extractor) {
        super(extractor);
    }

    public RegexClusterizer<T> split(String regex) {
        this.regex = regex;
        return this;
    }

    @Override
    protected Map<String, List<T>> buildClusterizationMap(Cluster<T> cluster) {
        Map<String, List<T>> map = buildMap();
        for (Item<T> element : cluster.elements()) {
            String suffix = extract(element.get()).substring(cluster.id().length());
            String[] split = suffix.split(regex);
            if(split.length == 1 || (split.length == 2 && split[0].isEmpty())) continue;
            map.get(split[0].isEmpty() ? split[1] : split[0]).add(element.get());
        }
        return map;
    }
}
