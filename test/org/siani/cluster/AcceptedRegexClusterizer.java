package org.siani.cluster;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AcceptedRegexClusterizer {

    @Test
    public void should_generate_hierarchy_based_on_pattern() throws Exception {
        ItemList<String> items = new RegexClusterizer<String>().split(": ").clusterize(
                "EI-0002/15: A 18/05/2015",
                "P-0002/15: 1 Señales Marítimas 12010",
                "P-0002/15: 2 Señales Marítimas 12095",
                "P-0002/15: 3 Señales-Firma",
                "EI-0003/15: A 18/05/2015",
                "P-0003/15: 1 Señales Marítimas 12024",
                "P-0003/15: 2 Señales Marítimas 12060",
                "P-0003/15: 3 Señales Marítimas 12000",
                "P-0003/15: 4 Señales Marítimas 12103"
        );
        String expected =
                "EI-0002/15: A 18/05/2015\n" +
                "EI-0003/15: A 18/05/2015\n" +
                "P-0002/15\n" +
                        "\tP-0002/15: 1 Señales Marítimas 12010\n" +
                        "\tP-0002/15: 2 Señales Marítimas 12095\n" +
                        "\tP-0002/15: 3 Señales-Firma\n" +
                "P-0003/15\n" +
                        "\tP-0003/15: 1 Señales Marítimas 12024\n" +
                        "\tP-0003/15: 2 Señales Marítimas 12060\n" +
                        "\tP-0003/15: 3 Señales Marítimas 12000\n" +
                        "\tP-0003/15: 4 Señales Marítimas 12103\n";
        assertEquals(expected, items.toString());
    }


    @Test
    public void should_generate_a_two_levels_hierarchy() throws Exception {
        ItemList<String> items = new RegexClusterizer<String>().split(": ").clusterize(
                "P-0002/15: 1 Señales Marítimas: 12010",
                "P-0002/15: 1 Señales Marítimas: 12095",
                "P-0002/15: 2 Señales Marítimas: 12010",
                "P-0002/15: 3 Señales Marítimas: 12095",
                "P-0002/16: 1 Señales Marítimas: 12010"
        );
        String expected =
                "P-0002/16: 1 Señales Marítimas: 12010\n" +
                "P-0002/15\n" +
                "\tP-0002/15: 2 Señales Marítimas: 12010\n" +
                "\tP-0002/15: 3 Señales Marítimas: 12095\n" +
                "\tP-0002/15: 1 Señales Marítimas\n" +
                "\t\tP-0002/15: 1 Señales Marítimas: 12010\n" +
                "\t\tP-0002/15: 1 Señales Marítimas: 12095\n";
        assertEquals(expected, items.toString());
    }
}
