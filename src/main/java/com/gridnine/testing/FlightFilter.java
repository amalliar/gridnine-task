package com.gridnine.testing;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class to apply single or multiple filters.
 */
class FlightFilter {

    /**
     * A method for filtering Flight objects based on a given filter.
     * @param flights Collection of Flight objects to filter.
     * @param filter Predicate filter to apply.
     * @return A List of Flight objects that match the Predicate filter.
     */
    static List<Flight> apply(Collection<Flight> flights, Predicate<Flight> filter) {
        if (flights == null || filter == null)
            throw new NullPointerException(
                    "parameters can not be null");

        return flights.parallelStream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    /**
     * A method for filtering Flight objects based on a given Collection of filters.
     * @param flights Collection of Flight objects to filter.
     * @param filters Collection of Predicate filters to apply.
     * @return A List of Flight objects that match the Predicate filters.
     */
    static List<Flight> apply(Collection<Flight> flights, Collection<Predicate<Flight>> filters) {
        if (flights == null || filters == null)
            throw new NullPointerException(
                    "parameters can not be null");

        Predicate<Flight> filter = filters.stream()
                .reduce(x -> true, Predicate::and);
        return FlightFilter.apply(flights, filter);
    }
}
