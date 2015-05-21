package org.siani.cluster;

import org.junit.Test;

import java.util.Comparator;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class Accepted {

    @Test
    public void should_clusterize_in_two_clusters() throws Exception {
        Item<String> item = new Clusterizer<String>().clusterize("a1011", "a2002", "b2012");
        assertEquals("", item.id());
        assertEquals(2, item.items().size());

        assertEquals("b2012", item.items().get(0).id());

        assertEquals("a", item.items().get(1).id());
        assertEquals(2, item.items().get(1).items().size());
        assertEquals("a1011", item.items().get(1).items().get(0).id());
        assertFalse(item.items().get(1).items().get(0).isGroup());
        assertEquals("a2002", item.items().get(1).items().get(1).id());
        assertFalse(item.items().get(1).items().get(1).isGroup());

        System.out.println(item.items());
    }

    @Test
    public void should_clusterize_in_two_clusters_making_prefix_up() throws Exception {
        Item<String> item = new Clusterizer<String>().clusterize("a2011", "a2002", "b2012");
        assertEquals("", item.id());
        assertEquals(2, item.items().size());

        assertEquals("b2012", item.items().get(0).id());
        assertFalse(item.items().get(0).isGroup());

        assertEquals("a20", item.items().get(1).id());
        assertEquals(2, item.items().get(1).items().size());
        assertEquals("a2011", item.items().get(1).items().get(0).id());
        assertFalse(item.items().get(1).items().get(0).isGroup());
        assertEquals("a2002", item.items().get(1).items().get(1).id());
        assertFalse(item.items().get(1).items().get(1).isGroup());
    }

    @Test
    public void should_clusterize_in_codes_according_to_different_patterns() throws Exception {
        Item<String> item = new Clusterizer<String>().clusterize(
                "P-0005-SM-Open", "P-0005-SM-Closed",
                "P-0005-B-Open", "P-0005-B-Closed",
                "P-0006-SM-Open", "P-0006-SM-Closed");
        assertEquals("P-000", item.id());
        assertEquals(2, item.items().size());

        assertEquals("P-0005-", item.items().get(0).id());
        assertEquals(2, item.items().get(0).items().size());

        assertEquals("P-0005-SM-", item.items().get(0).items().get(0).id());
        assertEquals(2, item.items().get(0).items().get(0).items().size());

        assertEquals("P-0005-SM-Open", item.items().get(0).items().get(0).items().get(0).id());
        assertFalse(item.items().get(0).items().get(0).items().get(0).isGroup());
        assertEquals("P-0005-SM-Closed", item.items().get(0).items().get(0).items().get(1).id());
        assertFalse(item.items().get(0).items().get(0).items().get(1).isGroup());

        assertEquals("P-0005-B-", item.items().get(0).items().get(1).id());
        assertEquals(2, item.items().get(0).items().get(1).items().size());

        assertEquals("P-0005-B-Open", item.items().get(0).items().get(1).items().get(0).id());
        assertFalse(item.items().get(0).items().get(1).items().get(0).isGroup());
        assertEquals("P-0005-B-Closed", item.items().get(0).items().get(1).items().get(1).id());
        assertFalse(item.items().get(0).items().get(1).items().get(1).isGroup());

        assertEquals("P-0006-SM-", item.items().get(1).id());
        assertEquals(2, item.items().get(1).items().size());

        assertEquals("P-0006-SM-Open", item.items().get(1).items().get(0).id());
        assertFalse(item.items().get(1).items().get(0).isGroup());
        assertEquals("P-0006-SM-Closed", item.items().get(1).items().get(1).id());
        assertFalse(item.items().get(1).items().get(1).isGroup());
    }

    @Test
    public void should_clusterize_real_idenfitiers() throws Exception {
        Item<String> item = new Clusterizer<String>().clusterize(
                "EI-0002/15-A 18/05/2015",
                "P-0002/15-1: Señales Marítimas 12010",
                "P-0002/15-2: Señales Marítimas 12095",
                "P-0002/15-3: Señales-Firma",
                "EI-0003/15-A 18/05/2015",
                "P-0003/15-1: Señales Marítimas 12024",
                "P-0003/15-2: Señales Marítimas 12060",
                "P-0003/15-3: Señales Marítimas 12000",
                "P-0003/15-4: Señales Marítimas 12103");

        assertEquals("", item.id());
        assertEquals(2, item.items().size());

        assertEquals("EI-000", item.items().get(0).id());
        assertEquals(2, item.items().get(0).items().size());

        assertEquals("EI-0002/15-A 18/05/2015", item.items().get(0).items().get(0).id());
        assertFalse(item.items().get(0).items().get(0).isGroup());
        assertEquals("EI-0003/15-A 18/05/2015", item.items().get(0).items().get(1).get());
        assertFalse(item.items().get(0).items().get(1).isGroup());

        assertEquals("P-000", item.items().get(1).id());
        assertEquals(2, item.items().get(1).items().size());

        assertEquals("P-0002/15-", item.items().get(1).items().get(0).id());
        assertEquals(3, item.items().get(1).items().get(0).items().size());

        assertEquals("P-0002/15-1: Señales Marítimas 12010", item.items().get(1).items().get(0).items().get(0).id());
        assertFalse(item.items().get(1).items().get(0).items().get(0).isGroup());
        assertEquals("P-0002/15-2: Señales Marítimas 12095", item.items().get(1).items().get(0).items().get(1).id());
        assertFalse(item.items().get(1).items().get(0).items().get(1).isGroup());
        assertEquals("P-0002/15-3: Señales-Firma", item.items().get(1).items().get(0).items().get(2).id());
        assertFalse(item.items().get(1).items().get(0).items().get(2).isGroup());

        assertEquals("P-0003/15-", item.items().get(1).items().get(1).id());
        assertEquals(4, item.items().get(1).items().get(1).items().size());
//
        assertEquals("P-0003/15-1: Señales Marítimas 12024", item.items().get(1).items().get(1).items().get(0).id());
        assertFalse(item.items().get(1).items().get(1).items().get(0).isGroup());
        assertEquals("P-0003/15-2: Señales Marítimas 12060", item.items().get(1).items().get(1).items().get(1).id());
        assertFalse(item.items().get(1).items().get(1).items().get(1).isGroup());
        assertEquals("P-0003/15-3: Señales Marítimas 12000", item.items().get(1).items().get(1).items().get(2).id());
        assertFalse(item.items().get(1).items().get(1).items().get(2).isGroup());
        assertEquals("P-0003/15-4: Señales Marítimas 12103", item.items().get(1).items().get(1).items().get(3).id());
        assertFalse(item.items().get(1).items().get(1).items().get(3).isGroup());
    }

    @Test
    public void should_cluster_using_extractor() throws Exception {
        Item<Person> item = new Clusterizer<>(personExtractor()).clusterize(
                new Person("Jose Juan", "Hernandez"),
                new Person("Jose", "Evora"),
                new Person("Octavio", "Roncal"),
                new Person("Ruben", "Diaz"));

        assertEquals("", item.id());
        assertEquals(3, item.items().size());

        assertEquals("Octavio Roncal", item.items().get(0).id());
        assertFalse(item.items().get(0).isGroup());
        assertEquals("Ruben Diaz", item.items().get(1).id());
        assertFalse(item.items().get(1).isGroup());

        assertEquals("Jose ", item.items().get(2).id());
        assertEquals(2, item.items().get(2).items().size());

        assertEquals("Jose Juan Hernandez", item.items().get(2).items().get(0).id());
        assertFalse(item.items().get(2).items().get(0).isGroup());
        assertEquals("Jose Evora", item.items().get(2).items().get(1).id());
        assertFalse(item.items().get(2).items().get(1).isGroup());
    }

    @Test
    public void should_cluster_with_sorter() throws Exception {
        Item<Person> item = new Clusterizer<>(personExtractor()).clusterize(
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
        assertEquals(expected, item.items().toString());

        item.items().sort(personSorter());
        expected =
                "Ruben Diaz\n" +
                        "Jose \n" +
                        "\tJose Evora\n" +
                        "\tJose Juan Hernandez\n" +
                        "Octavio Roncal\n";
        assertEquals(expected, item.items().toString());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void should_sort_based_on_dates() throws Exception {
        Item<Register> item = new Clusterizer<Register>().clusterize(
                new Register("a0", new Date(115, 0, 11)),
                new Register("a1", new Date(115, 0, 13)),
                new Register("b0", new Date(115, 0, 9)),
                new Register("b1", new Date(115, 0, 15))
        );
        item.items().sort(registerComparator());
        String expected =
                "b\n" +
                        "\tb1\n" +
                        "\tb0\n" +
                        "a\n" +
                        "\ta1\n" +
                        "\ta0\n";
        assertEquals(expected, item.items().toString());
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
    public void should_parent_of_root_be_null_item() throws Exception {
        assertEquals(NullItem.instance(), new Clusterizer<String>().clusterize("").parent());
    }

    @Test
    public void should_be_an_empty_item() throws Exception {
        assertEquals("null", new Clusterizer<String>().clusterize().id());
        assertFalse(new Clusterizer<String>().clusterize().isGroup());
        assertEquals(null, new Clusterizer<String>().clusterize().get());
        assertEquals(0, new Clusterizer<String>().clusterize().items().size());
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
