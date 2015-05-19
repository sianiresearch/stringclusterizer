package org.siani.cluster;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class Accepted {

    @Test
    public void should_clusterize_in_two_clusters() throws Exception {
        Cluster<String> cluster = new Clusterizer<String>().clusterize("a1011", "a2002", "b2012");
        assertEquals("", cluster.id());
        assertEquals(3, cluster.size());
        assertEquals(1, cluster.clusters().length);

        assertEquals("a", cluster.clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].size());
        assertEquals("a1011", cluster.clusters()[0].items().get(0));
        assertEquals("a2002", cluster.clusters()[0].items().get(1));
        assertEquals(0, cluster.clusters()[0].clusters().length);

        System.out.println(cluster.toString());
    }

    @Test
    public void should_clusterize_in_two_clusters_making_prefix_up() throws Exception {
        Cluster<String> cluster = new Clusterizer<String>().clusterize("a2011", "a2002", "b2012");
        assertEquals("", cluster.id());
        assertEquals(3, cluster.size());
        assertEquals(1, cluster.clusters().length);

        assertEquals("a20", cluster.clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].size());
        assertEquals("a2011", cluster.clusters()[0].items().get(0));
        assertEquals("a2002", cluster.clusters()[0].items().get(1));
        assertEquals(0, cluster.clusters()[0].clusters().length);

        System.out.println(cluster.toString());
    }

    @Test
    public void should_clusterize_in_codes_according_to_different_patterns() throws Exception {
        Cluster<String> cluster = new Clusterizer<String>().clusterize(
                "P-0005-SM-Open", "P-0005-SM-Closed",
                "P-0005-B-Open", "P-0005-B-Closed",
                "P-0006-SM-Open", "P-0006-SM-Closed");
        assertEquals("P-000", cluster.id());
        assertEquals(6, cluster.size());
        assertEquals(2, cluster.clusters().length);

        assertEquals("P-0005-", cluster.clusters()[0].id());
        assertEquals(4, cluster.clusters()[0].size());
        assertEquals("P-0005-SM-Open", cluster.clusters()[0].items().get(0));
        assertEquals("P-0005-SM-Closed", cluster.clusters()[0].items().get(1));
        assertEquals("P-0005-B-Open", cluster.clusters()[0].items().get(2));
        assertEquals("P-0005-B-Closed", cluster.clusters()[0].items().get(3));
        assertEquals(2, cluster.clusters()[0].clusters().length);

        assertEquals("P-0005-SM-", cluster.clusters()[0].clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].clusters()[0].size());
        assertEquals("P-0005-SM-Open", cluster.clusters()[0].clusters()[0].items().get(0));
        assertEquals("P-0005-SM-Closed", cluster.clusters()[0].clusters()[0].items().get(1));
        assertEquals(0, cluster.clusters()[0].clusters()[0].clusters().length);

        assertEquals("P-0005-B-", cluster.clusters()[0].clusters()[1].id());
        assertEquals(2, cluster.clusters()[0].clusters()[1].size());
        assertEquals("P-0005-B-Open", cluster.clusters()[0].clusters()[1].items().get(0));
        assertEquals("P-0005-B-Closed", cluster.clusters()[0].clusters()[1].items().get(1));
        assertEquals(0, cluster.clusters()[1].clusters().length);

        assertEquals("P-0006-SM-", cluster.clusters()[1].id());
        assertEquals(2, cluster.clusters()[1].size());
        assertEquals("P-0006-SM-Open", cluster.clusters()[1].items().get(0));
        assertEquals("P-0006-SM-Closed", cluster.clusters()[1].items().get(1));
        assertEquals(0, cluster.clusters()[1].clusters().length);

        System.out.println(cluster.toString());
    }

    @Test
    public void should_clusterize_real_idenfitiers() throws Exception {
        Cluster<String> cluster = new Clusterizer<String>().clusterize(
                "EI-0002/15-A 18/05/2015",
                "P-0002/15-1: Señales Marítimas 12010",
                "P-0002/15-2: Señales Marítimas 12095",
                "P-0002/15-3: Señales-Firma",
                "EI-0003/15-A 18/05/2015",
                "P-0003/15-1: Señales Marítimas 12024",
                "P-0003/15-2: Señales Marítimas 12060",
                "P-0003/15-3: Señales Marítimas 12000",
                "P-0003/15-4: Señales Marítimas 12103");

        assertEquals("", cluster.id());
        assertEquals(9, cluster.size());
        assertEquals(2, cluster.clusters().length);

        assertEquals("EI-000", cluster.clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].size());
        assertEquals("EI-0002/15-A 18/05/2015", cluster.clusters()[0].items().get(0));
        assertEquals("EI-0003/15-A 18/05/2015", cluster.clusters()[0].items().get(1));
        assertEquals(0, cluster.clusters()[0].clusters().length);

        assertEquals("P-000", cluster.clusters()[1].id());
        assertEquals(7, cluster.clusters()[1].size());
        assertEquals("P-0002/15-1: Señales Marítimas 12010", cluster.clusters()[1].items().get(0));
        assertEquals("P-0002/15-2: Señales Marítimas 12095", cluster.clusters()[1].items().get(1));
        assertEquals("P-0002/15-3: Señales-Firma", cluster.clusters()[1].items().get(2));
        assertEquals("P-0003/15-1: Señales Marítimas 12024", cluster.clusters()[1].items().get(3));
        assertEquals("P-0003/15-2: Señales Marítimas 12060", cluster.clusters()[1].items().get(4));
        assertEquals("P-0003/15-3: Señales Marítimas 12000", cluster.clusters()[1].items().get(5));
        assertEquals("P-0003/15-4: Señales Marítimas 12103", cluster.clusters()[1].items().get(6));
        assertEquals(2, cluster.clusters()[1].clusters().length);

        assertEquals("P-0002/15-", cluster.clusters()[1].clusters()[0].id());
        assertEquals(3, cluster.clusters()[1].clusters()[0].size());
        assertEquals("P-0002/15-1: Señales Marítimas 12010", cluster.clusters()[1].clusters()[0].items().get(0));
        assertEquals("P-0002/15-2: Señales Marítimas 12095", cluster.clusters()[1].clusters()[0].items().get(1));
        assertEquals("P-0002/15-3: Señales-Firma", cluster.clusters()[1].clusters()[0].items().get(2));
        assertEquals(0, cluster.clusters()[1].clusters()[0].clusters().length);

        assertEquals("P-0003/15-", cluster.clusters()[1].clusters()[1].id());
        assertEquals(4, cluster.clusters()[1].clusters()[1].size());
        assertEquals("P-0003/15-1: Señales Marítimas 12024", cluster.clusters()[1].clusters()[1].items().get(0));
        assertEquals("P-0003/15-2: Señales Marítimas 12060", cluster.clusters()[1].clusters()[1].items().get(1));
        assertEquals("P-0003/15-3: Señales Marítimas 12000", cluster.clusters()[1].clusters()[1].items().get(2));
        assertEquals("P-0003/15-4: Señales Marítimas 12103", cluster.clusters()[1].clusters()[1].items().get(3));
        assertEquals(0, cluster.clusters()[1].clusters()[1].clusters().length);

        System.out.println(cluster.toString());
    }

}
