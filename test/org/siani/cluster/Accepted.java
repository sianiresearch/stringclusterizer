package org.siani.cluster;

import org.junit.Test;

import java.util.Comparator;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class Accepted {

    @Test
    public void should_clusterize_in_two_clusters() throws Exception {
        ItemList<String> items = new Clusterizer<String>().clusterize("a1011", "a2002", "b2012");
        assertEquals(2, items.size());

        assertEquals("b2012", items.get(0).id());

        assertEquals("a", items.get(1).id());
        assertEquals(2, items.get(1).items().size());
        assertEquals("a1011", items.get(1).items().get(0).id());
        assertFalse(items.get(1).items().get(0).isGroup());
        assertEquals("a2002", items.get(1).items().get(1).id());
        assertFalse(items.get(1).items().get(1).isGroup());
        assertEquals("a", items.get(1).items().get(1).parent().id());
    }

    @Test
    public void should_clusterize_in_two_clusters_making_prefix_up() throws Exception {
        ItemList<String> items = new Clusterizer<String>().clusterize("a2011", "a2002", "b2012");
        assertEquals(2, items.size());

        assertEquals("b2012", items.get(0).id());
        assertFalse(items.get(0).isGroup());

        assertEquals("a20", items.get(1).id());
        assertEquals(2, items.get(1).items().size());
        assertEquals("a2011", items.get(1).items().get(0).id());
        assertFalse(items.get(1).items().get(0).isGroup());
        assertEquals("a2002", items.get(1).items().get(1).id());
        assertFalse(items.get(1).items().get(1).isGroup());
    }

    @Test
    public void should_clusterize_in_codes_according_to_different_patterns() throws Exception {
        ItemList<String> items = new Clusterizer<String>().clusterize(
                "P-0005-SM-Open", "P-0005-SM-Closed",
                "P-0005-B-Open", "P-0005-B-Closed",
                "P-0006-SM-Open", "P-0006-SM-Closed");
        assertEquals(2, items.size());

        assertEquals("P-0005-", items.get(0).id());
        assertEquals(2, items.get(0).items().size());

        assertEquals("P-0005-SM-", items.get(0).items().get(0).id());
        assertEquals(2, items.get(0).items().get(0).items().size());

        assertEquals("P-0005-SM-Open", items.get(0).items().get(0).items().get(0).id());
        assertFalse(items.get(0).items().get(0).items().get(0).isGroup());
        assertEquals("P-0005-SM-Closed", items.get(0).items().get(0).items().get(1).id());
        assertFalse(items.get(0).items().get(0).items().get(1).isGroup());

        assertEquals("P-0005-B-", items.get(0).items().get(1).id());
        assertEquals(2, items.get(0).items().get(1).items().size());

        assertEquals("P-0005-B-Open", items.get(0).items().get(1).items().get(0).id());
        assertFalse(items.get(0).items().get(1).items().get(0).isGroup());
        assertEquals("P-0005-B-Closed", items.get(0).items().get(1).items().get(1).id());
        assertFalse(items.get(0).items().get(1).items().get(1).isGroup());

        assertEquals("P-0006-SM-", items.get(1).id());
        assertEquals(2, items.get(1).items().size());

        assertEquals("P-0006-SM-Open", items.get(1).items().get(0).id());
        assertFalse(items.get(1).items().get(0).isGroup());
        assertEquals("P-0006-SM-Closed", items.get(1).items().get(1).id());
        assertFalse(items.get(1).items().get(1).isGroup());
    }

    @Test
    public void should_clusterize_real_idenfitiers() throws Exception {
        ItemList<String> items = new Clusterizer<String>().clusterize(
                "EI-0002/15-A 18/05/2015",
                "P-0002/15-1: Señales Marítimas 12010",
                "P-0002/15-2: Señales Marítimas 12095",
                "P-0002/15-3: Señales-Firma",
                "EI-0003/15-A 18/05/2015",
                "P-0003/15-1: Señales Marítimas 12024",
                "P-0003/15-2: Señales Marítimas 12060",
                "P-0003/15-3: Señales Marítimas 12000",
                "P-0003/15-4: Señales Marítimas 12103");

        assertEquals(2, items.size());

        assertEquals("EI-000", items.get(0).id());
        assertEquals(2, items.get(0).items().size());

        assertEquals("EI-0002/15-A 18/05/2015", items.get(0).items().get(0).id());
        assertFalse(items.get(0).items().get(0).isGroup());
        assertEquals("EI-0003/15-A 18/05/2015", items.get(0).items().get(1).get());
        assertFalse(items.get(0).items().get(1).isGroup());

        assertEquals("P-000", items.get(1).id());
        assertEquals(2, items.get(1).items().size());

        assertEquals("P-0002/15-", items.get(1).items().get(0).id());
        assertEquals(3, items.get(1).items().get(0).items().size());

        assertEquals("P-0002/15-1: Señales Marítimas 12010", items.get(1).items().get(0).items().get(0).id());
        assertFalse(items.get(1).items().get(0).items().get(0).isGroup());
        assertEquals("P-0002/15-2: Señales Marítimas 12095", items.get(1).items().get(0).items().get(1).id());
        assertFalse(items.get(1).items().get(0).items().get(1).isGroup());
        assertEquals("P-0002/15-3: Señales-Firma", items.get(1).items().get(0).items().get(2).id());
        assertFalse(items.get(1).items().get(0).items().get(2).isGroup());

        assertEquals("P-0003/15-", items.get(1).items().get(1).id());
        assertEquals(4, items.get(1).items().get(1).items().size());
//
        assertEquals("P-0003/15-1: Señales Marítimas 12024", items.get(1).items().get(1).items().get(0).id());
        assertFalse(items.get(1).items().get(1).items().get(0).isGroup());
        assertEquals("P-0003/15-2: Señales Marítimas 12060", items.get(1).items().get(1).items().get(1).id());
        assertFalse(items.get(1).items().get(1).items().get(1).isGroup());
        assertEquals("P-0003/15-3: Señales Marítimas 12000", items.get(1).items().get(1).items().get(2).id());
        assertFalse(items.get(1).items().get(1).items().get(2).isGroup());
        assertEquals("P-0003/15-4: Señales Marítimas 12103", items.get(1).items().get(1).items().get(3).id());
        assertFalse(items.get(1).items().get(1).items().get(3).isGroup());
    }

    @Test
    public void should_cluster_using_extractor() throws Exception {
        ItemList<Person> items = new Clusterizer<>(personExtractor()).clusterize(
                new Person("Jose Juan", "Hernandez"),
                new Person("Jose", "Evora"),
                new Person("Octavio", "Roncal"),
                new Person("Ruben", "Diaz"));

        assertEquals(3, items.size());

        assertEquals("Octavio Roncal", items.get(0).id());
        assertFalse(items.get(0).isGroup());
        assertEquals("Ruben Diaz", items.get(1).id());
        assertFalse(items.get(1).isGroup());

        assertEquals("Jose ", items.get(2).id());
        assertEquals(2, items.get(2).items().size());

        assertEquals("Jose Juan Hernandez", items.get(2).items().get(0).id());
        assertFalse(items.get(2).items().get(0).isGroup());
        assertEquals("Jose Evora", items.get(2).items().get(1).id());
        assertFalse(items.get(2).items().get(1).isGroup());
    }

    @Test
    public void should_cluster_with_sorter() throws Exception {
        ItemList<Person> items = new Clusterizer<>(personExtractor()).clusterize(
                new Person("Jose Juan", "Hernandez"),
                new Person("Jose", "Evora"),
                new Person("Octavio", "Roncal"),
                new Person("Ruben", "Diaz"));
        String expected =
                "Octavio Roncal\n" +
                        "Ruben Diaz\n" +
                        "Jose \n" +
                        "\tJose Juan Hernandez\n" +
                        "\tJose Evora\n";
        assertEquals(expected, items.toString());

        items.sort(personSorter());
        expected =
                "Ruben Diaz\n" +
                        "Jose \n" +
                        "\tJose Evora\n" +
                        "\tJose Juan Hernandez\n" +
                        "Octavio Roncal\n";
        assertEquals(expected, items.toString());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_sort_based_on_dates() throws Exception {
        ItemList<Register> items = new Clusterizer<Register>().clusterize(
                new Register("a0", new Date(115, 0, 11)),
                new Register("a1", new Date(115, 0, 13)),
                new Register("b0", new Date(115, 0, 9)),
                new Register("b1", new Date(115, 0, 15))
        );
        items.sort(registerComparator());
        String expected =
                "b\n" +
                        "\tb1\n" +
                        "\tb0\n" +
                        "a\n" +
                        "\ta1\n" +
                        "\ta0\n";
        assertEquals(expected, items.toString());
    }

    @Test
    public void should_sort_multilevel_structures() throws Exception {
        ItemList<String> items = new Clusterizer<String>().clusterize("100", "101", "110", "111", "000", "001", "010", "011");
        items.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        String expected =
                "0\n" +
                "\t00\n" +
                "\t\t000\n" +
                "\t\t001\n" +
                "\t01\n" +
                "\t\t010\n" +
                "\t\t011\n" +
                "1\n" +
                "\t10\n" +
                "\t\t100\n" +
                "\t\t101\n" +
                "\t11\n" +
                "\t\t110\n" +
                "\t\t111\n";
        assertEquals(expected, items.toString());
    }

    @Test
    public void should_browse_properly_through_parents() throws Exception {
        ItemList<String> items = new Clusterizer<String>().clusterize("a0", "a10", "a11");
        assertEquals("a0", items.get(1).items().get(0).parent().group().get(0).id());
        assertEquals("a0", items.get(1).items().get(1).parent().group().get(0).id());
        assertEquals("a1", items.get(1).items().get(0).parent().group().get(1).id());
        assertEquals("a1", items.get(1).items().get(1).parent().group().get(1).id());
    }

    private Comparator<Register> registerComparator() {
        return new Comparator<Register>() {
            @Override
            public int compare(Register o1, Register o2) {
                return o2.date.compareTo(o1.date);
            }
        };
    }

    @Test
    public void should_be_an_empty_list_when_clusterize_is_called_without_parameters() throws Exception {
        assertTrue(new Clusterizer<String>().clusterize().isEmpty());
    }

    private Comparator<Person> personSorter() {
        return new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.surname.compareTo(o2.surname);
            }
        };
    }

    private StringExtractor<Person> personExtractor() {
        return new StringExtractor<Person>() {
            @Override
            public String extract(Person object) {
                return object.name + " " + object.surname;
            }
        };
    }

    class Person {
        final String name;
        final String surname;

        public Person(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }
    }

    private class Register {
        final String id;
        final Date date;

        public Register(String id, Date date) {
            this.id = id;
            this.date = date;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
