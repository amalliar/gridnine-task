package com.gridnine.testing;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class to apply single or multiple filters.
 */
class FlightFilter {
    static List<Flight> apply(List<Flight> flights, Predicate<Flight> filter) {
        if (flights == null || filter == null)
            throw new NullPointerException(
                    "parameters can not be null");
        return flights.parallelStream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    static List<Flight> apply(List<Flight> flights, List<Predicate<Flight>> filters) {
        if (flights == null || filters == null)
            throw new NullPointerException(
                    "parameters can not be null");
        Predicate<Flight> filter = filters.stream()
                .reduce(Predicate::and)
                .orElse(x -> true);
        return FlightFilter.apply(flights, filter);
    }
}
