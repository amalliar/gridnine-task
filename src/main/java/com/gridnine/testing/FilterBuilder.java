package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

/**
 * Factory class to get required filters.
 */
class FilterBuilder {

    /**
     * A method for creating the requested filter based on its name.
     * @param filter String name of the requested filter.
     * @return Predicate filter.
     */
    static Predicate<Flight> createFilter(final String filter) {
        switch (filter) {
            case "departsAfterCurrentTime":
                return flight -> flight.getSegments()
                        .stream()
                        .noneMatch(segment -> segment.getDepartureDate()
                                .compareTo(LocalDateTime.now()) < 0);
            case "departsBeforeArrivalTime":
                return flight -> flight.getSegments()
                        .stream()
                        .noneMatch(segment -> segment.getDepartureDate()
                                .compareTo(segment.getArrivalDate()) > 0);
            case "groundTimeLessOrEqualsTwoHours":
                return flight -> {
                    List<Segment> segments = flight.getSegments();
                    long groundTime = 0;

                    for (int i = 0; i < segments.size() - 1; ++i) {
                        LocalDateTime prevArrival = segments.get(i).getArrivalDate();
                        LocalDateTime nextDeparture = segments.get(i + 1).getDepartureDate();
                        groundTime += ChronoUnit.HOURS.between(prevArrival, nextDeparture);
                    }

                    return groundTime <= 2;
                };
            default:
                throw new IllegalArgumentException(
                        String.format("unknown filter: %s", filter));
        }
    }
}
