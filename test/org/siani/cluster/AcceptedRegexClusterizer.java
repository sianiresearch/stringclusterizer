package org.siani.cluster;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AcceptedRegexClusterizer<T> {

    @Test
    public void should_generate_hierarchy_based_on_pattern() throws Exception {
        ItemList<String> items = new RegexClusterizer<String>().split(": ").clusterize(
                "EI-0002/15: A 18/05/2015",
                "P-0002/15: 1 Se�ales Mar�timas 12010",
                "P-0002/15: 2 Se�ales Mar�timas 12095",
                "P-0002/15: 3 Se�ales-Firma",
                "EI-0003/15: A 18/05/2015",
                "P-0003/15: 1 Se�ales Mar�timas 12024",
                "P-0003/15: 2 Se�ales Mar�timas 12060",
                "P-0003/15: 3 Se�ales Mar�timas 12000",
                "P-0003/15: 4 Se�ales Mar�timas 12103"
        );
        String expected =
                "EI-0002/15: A 18/05/2015\n" +
                "EI-0003/15: A 18/05/2015\n" +
                "P-0002/15\n" +
                        "\tP-0002/15: 1 Se�ales Mar�timas 12010\n" +
                        "\tP-0002/15: 2 Se�ales Mar�timas 12095\n" +
                        "\tP-0002/15: 3 Se�ales-Firma\n" +
                "P-0003/15\n" +
                        "\tP-0003/15: 1 Se�ales Mar�timas 12024\n" +
                        "\tP-0003/15: 2 Se�ales Mar�timas 12060\n" +
                        "\tP-0003/15: 3 Se�ales Mar�timas 12000\n" +
                        "\tP-0003/15: 4 Se�ales Mar�timas 12103\n";
        assertEquals(expected, items.toString());
    }

}