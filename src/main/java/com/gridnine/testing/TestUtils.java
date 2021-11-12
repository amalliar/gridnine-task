package com.gridnine.testing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Utility class to showcase basic filter functionality.
 */
class TestUtils {
    static void testFilter(final Collection<Flight> flights, final String filter) {
        List<Flight> retained = FlightFilter.apply(flights, FilterBuilder.createFilter(filter));
        List<Flight> filtered = new ArrayList<>(flights);

        filtered.removeAll(retained);
        System.out.println(filter);
        System.out.printf("retained:\n%s\n", retained);
        System.out.printf("filtered:\n%s\n", filtered);
    }

    static void testMultiFilter(final Collection<Flight> flights, final Collection<String> filters) {
        List<Predicate<Flight>> pFilters = new LinkedList<>();
        for (String filter : filters) {
            pFilters.add(FilterBuilder.createFilter(filter));
        }
        List<Flight> retained = FlightFilter.apply(flights, pFilters);
        List<Flight> filtered = new ArrayList<>(flights);

        filtered.removeAll(retained);
        filters.forEach(System.out::println);
        System.out.printf("retained:\n%s\n", retained);
        System.out.printf("filtered:\n%s\n", filtered);
    }
}
