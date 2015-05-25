package org.siani.cluster;

import java.util.*;

public class CharacterClusterizer<T> extends Clusterizer<T> {

    public CharacterClusterizer() {
    }

    public CharacterClusterizer(StringExtractor<T> extractor) {
        super(extractor);
    }

    @Override
    protected Map<String, List<T>> buildClusterizationMap(Cluster<T> cluster) {
        Map<String, List<T>> map = buildMap();
        for (Item<T> element : cluster.elements()) {
            if (extract(element.get()).length() <= cluster.id().length() + 1) continue;
            map.get(extract(element.get()).substring(0, cluster.id().length() + 1)).add(element.get());
        }
        return map;
    }

}