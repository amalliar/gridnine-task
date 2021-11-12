package com.gridnine.testing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JunitTests {
    List<Flight> flights = FlightBuilder.createFlights();

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
}
