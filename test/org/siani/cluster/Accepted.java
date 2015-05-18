package org.siani.cluster;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

public class Accepted {

    @Test
    public void should_clusterize_in_two_clusters() throws Exception {
        Cluster cluster = Clusterizer.execute("a1011", "a2002", "b2012");
        assertEquals("", cluster.id());
        assertEquals(3, cluster.size());
        assertEquals(2, cluster.clusters().length);

        assertEquals("a", cluster.clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].size());
        assertEquals(Arrays.toString((new String[]{"a1011", "a2002"})), Arrays.toString(cluster.clusters()[0].elements()));
        assertEquals(0, cluster.clusters()[0].clusters().length);

        assertEquals("b2012", cluster.clusters()[1].id());
        assertEquals(1, cluster.clusters()[1].size());
        assertEquals(Arrays.toString((new String[]{"b2012"})), Arrays.toString(cluster.clusters()[1].elements()));
        assertEquals(0, cluster.clusters()[1].clusters().length);

        System.out.println(cluster.toString());
    }

    @Test
    public void should_clusterize_in_two_clusters_making_prefix_up() throws Exception {
        Cluster cluster = Clusterizer.execute("a2011", "a2002", "b2012");
        assertEquals("", cluster.id());
        assertEquals(3, cluster.size());
        assertEquals(2, cluster.clusters().length);

        assertEquals("a20", cluster.clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].size());
        assertEquals(Arrays.toString((new String[]{"a2011", "a2002"})), Arrays.toString(cluster.clusters()[0].elements()));

        assertEquals("b2012", cluster.clusters()[1].id());
        assertEquals(1, cluster.clusters()[1].size());
        assertEquals(Arrays.toString((new String[]{"b2012"})), Arrays.toString(cluster.clusters()[1].elements()));
        assertEquals(0, cluster.clusters()[1].clusters().length);

        System.out.println(cluster.toString());
    }

    @Test
    public void should_clusterize_in__two_clusters() throws Exception {
        Cluster cluster = Clusterizer.execute("P-0005-SM-Open", "P-0005-SM-Closed", "P-0005-B-Open", "P-0005-B-Closed", "P-0006-SM-Open", "P-0006-SM-Closed");
        assertEquals("P-000", cluster.id());
        assertEquals(6, cluster.size());
        assertEquals(2, cluster.clusters().length);

        assertEquals("P-0005-", cluster.clusters()[0].id());
        assertEquals(4, cluster.clusters()[0].size());
        assertEquals(Arrays.toString((new String[]{"P-0005-SM-Open", "P-0005-SM-Closed", "P-0005-B-Open", "P-0005-B-Closed"})), Arrays.toString(cluster.clusters()[0].elements()));

        assertEquals("P-0006-SM-", cluster.clusters()[1].id());
        assertEquals(2, cluster.clusters()[1].size());
        assertEquals(Arrays.toString((new String[]{"P-0006-SM-Open", "P-0006-SM-Closed"})), Arrays.toString(cluster.clusters()[1].elements()));
        assertEquals(0, cluster.clusters()[1].clusters().length);

        System.out.println(cluster.toString());
    }
}
