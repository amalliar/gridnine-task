package com.gridnine.testing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class FlightFilterTests {
    final List<Flight> flights = FlightBuilder.createFlights();

    @Test
    @DisplayName("testDepartsAfterCurrentTime")
    void testDepartsAfterCurrentTime() {
        List<Flight> expected = new LinkedList<>(flights);
        expected.remove(2);
        List<Flight> actual = FlightFilter.apply(flights,
                FilterBuilder.createFilter("departsAfterCurrentTime"));

        assertThat(expected, is(actual));
    }

    @Test
    @DisplayName("testDepartsBeforeArrivalTime")
    void testDepartsBeforeArrivalTime() {
        List<Flight> expected = new LinkedList<>(flights);
        expected.remove(3);
        List<Flight> actual = FlightFilter.apply(flights,
                FilterBuilder.createFilter("departsBeforeArrivalTime"));

        assertThat(expected, is(actual));
    }

    @Test
    @DisplayName("testGroundTimeLessOrEqualsTwoHours")
    void testGroundTimeLessOrEqualsTwoHours() {
        List<Flight> expected = new LinkedList<>(flights);
        expected = expected.subList(0, 4);
        List<Flight> actual = FlightFilter.apply(flights,
                FilterBuilder.createFilter("groundTimeLessOrEqualsTwoHours"));

        assertThat(expected, is(actual));
    }

    @Test
    @DisplayName("testMultiFilter")
    void testMultiFilter() {
        List<Flight> expected = new LinkedList<>(flights);
        expected = expected.subList(0, 2);
        List<Flight> actual = FlightFilter.apply(flights,
                Arrays.asList(
                        FilterBuilder.createFilter("departsAfterCurrentTime"),
                        FilterBuilder.createFilter("departsBeforeArrivalTime"),
                        FilterBuilder.createFilter("groundTimeLessOrEqualsTwoHours")
                        ));

        assertThat(expected, is(actual));
    }

    @Test
    @DisplayName("testEmptyFilterList")
    void testEmptyFilterList() {
        List<Flight> expected = new LinkedList<>(flights);
        List<Flight> actual = FlightFilter.apply(flights, List.of());

        assertThat(expected, is(actual));
    }

    @Test
    @DisplayName("testEmptyFlightList")
    void testEmptyFlightList() {
        List<Flight> expected = List.of();
        List<Flight> actual = FlightFilter.apply(List.of(), List.of());

        assertThat(expected, is(actual));
    }

    @Test
    @DisplayName("testNullFlightList")
    void testNullFlightList() {
        Exception ex = Assertions.assertThrows(NullPointerException.class,
                () -> FlightFilter.apply(null, List.of()));

        assertThat("parameters can not be null", is(ex.getMessage()));
    }

    @Test
    @DisplayName("testNullFilter")
    void testNullFilter() {
        Exception ex = Assertions.assertThrows(NullPointerException.class,
                () -> FlightFilter.apply(flights, (Predicate<Flight>) null));

        assertThat("parameters can not be null", is(ex.getMessage()));
    }

    @Test
    @DisplayName("testUnknownFilter")
    void testUnknownFilter() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> FilterBuilder.createFilter("random"));

        assertThat("unknown filter: random", is(ex.getMessage()));
    }
}
