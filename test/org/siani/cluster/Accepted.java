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
        assertEquals(1, cluster.clusters().length);

        assertEquals("a", cluster.clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].size());
        assertEquals(Arrays.toString((new String[]{"a1011", "a2002"})), Arrays.toString(cluster.clusters()[0].items()));
        assertEquals(0, cluster.clusters()[0].clusters().length);

        System.out.println(cluster.toString());
    }

    @Test
    public void should_clusterize_in_two_clusters_making_prefix_up() throws Exception {
        Cluster cluster = Clusterizer.execute("a2011", "a2002", "b2012");
        assertEquals("", cluster.id());
        assertEquals(3, cluster.size());
        assertEquals(1, cluster.clusters().length);

        assertEquals("a20", cluster.clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].size());
        assertEquals(Arrays.toString((new String[]{"a2011", "a2002"})), Arrays.toString(cluster.clusters()[0].items()));

        System.out.println(cluster.toString());
    }

    @Test
    public void should_clusterize_in_codes_according_to_different_patterns() throws Exception {
        String[] elements = {
                "P-0005-SM-Open", "P-0005-SM-Closed",
                "P-0005-B-Open", "P-0005-B-Closed",
                "P-0006-SM-Open", "P-0006-SM-Closed"};
        Cluster cluster = Clusterizer.execute(elements);
        assertEquals("P-000", cluster.id());
        assertEquals(6, cluster.size());
        assertEquals(2, cluster.clusters().length);

        assertEquals("P-0005-", cluster.clusters()[0].id());
        assertEquals(4, cluster.clusters()[0].size());
        String[] itemsP005 = new String[]{"P-0005-SM-Open", "P-0005-SM-Closed", "P-0005-B-Open", "P-0005-B-Closed"};
        assertEquals(Arrays.toString(itemsP005), Arrays.toString(cluster.clusters()[0].items()));
        assertEquals(2, cluster.clusters()[0].clusters().length);

        assertEquals("P-0005-SM-", cluster.clusters()[0].clusters()[0].id());
        assertEquals(2, cluster.clusters()[0].clusters()[0].size());
        String[] itemsP005SM = new String[]{"P-0005-SM-Open", "P-0005-SM-Closed"};
        assertEquals(Arrays.toString(itemsP005SM), Arrays.toString(cluster.clusters()[0].clusters()[0].items()));
        assertEquals(0, cluster.clusters()[0].clusters()[0].clusters().length);

        assertEquals("P-0005-B-", cluster.clusters()[0].clusters()[1].id());
        assertEquals(2, cluster.clusters()[0].clusters()[1].size());
        String[] itemsP005B = new String[]{"P-0005-B-Open", "P-0005-B-Closed"};
        assertEquals(Arrays.toString(itemsP005B), Arrays.toString(cluster.clusters()[0].clusters()[1].items()));
        assertEquals(0, cluster.clusters()[1].clusters().length);

        assertEquals("P-0006-SM-", cluster.clusters()[1].id());
        assertEquals(2, cluster.clusters()[1].size());
        String[] itemsP0006 = new String[]{"P-0006-SM-Open", "P-0006-SM-Closed"};
        assertEquals(Arrays.toString(itemsP0006), Arrays.toString(cluster.clusters()[1].items()));
        assertEquals(0, cluster.clusters()[1].clusters().length);

        System.out.println(cluster.toString());
    }

    @Test
    public void should_clusterize_real_idenfitiers() throws Exception {
        Cluster cluster = Clusterizer.execute(
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
        String[] itemsEI000 = new String[]{"EI-0002/15-A 18/05/2015", "EI-0003/15-A 18/05/2015"};
        assertEquals(Arrays.toString(itemsEI000), Arrays.toString(cluster.clusters()[0].items()));
        assertEquals(0, cluster.clusters()[0].clusters().length);

        assertEquals("P-000", cluster.clusters()[1].id());
        assertEquals(7, cluster.clusters()[1].size());
        String[] itemsP000 = new String[]{
                "P-0002/15-1: Señales Marítimas 12010",
                "P-0002/15-2: Señales Marítimas 12095",
                "P-0002/15-3: Señales-Firma",
                "P-0003/15-1: Señales Marítimas 12024",
                "P-0003/15-2: Señales Marítimas 12060",
                "P-0003/15-3: Señales Marítimas 12000",
                "P-0003/15-4: Señales Marítimas 12103"};
        assertEquals(Arrays.toString(itemsP000), Arrays.toString(cluster.clusters()[1].items()));
        assertEquals(2, cluster.clusters()[1].clusters().length);

        assertEquals("P-0002/15-", cluster.clusters()[1].clusters()[0].id());
        assertEquals(3, cluster.clusters()[1].clusters()[0].size());
        String[] itemsP000215 = new String[]{
                "P-0002/15-1: Señales Marítimas 12010",
                "P-0002/15-2: Señales Marítimas 12095",
                "P-0002/15-3: Señales-Firma"};
        assertEquals(Arrays.toString(itemsP000215), Arrays.toString(cluster.clusters()[1].clusters()[0].items()));
        assertEquals(0, cluster.clusters()[1].clusters()[0].clusters().length);

        assertEquals("P-0003/15-", cluster.clusters()[1].clusters()[1].id());
        assertEquals(4, cluster.clusters()[1].clusters()[1].size());
        String[] itemsP000315 = new String[]{
                "P-0003/15-1: Señales Marítimas 12024",
                "P-0003/15-2: Señales Marítimas 12060",
                "P-0003/15-3: Señales Marítimas 12000",
                "P-0003/15-4: Señales Marítimas 12103"};
        assertEquals(Arrays.toString(itemsP000315), Arrays.toString(cluster.clusters()[1].clusters()[1].items()));
        assertEquals(0, cluster.clusters()[1].clusters()[1].clusters().length);

        System.out.println(cluster.toString());
    }

}
