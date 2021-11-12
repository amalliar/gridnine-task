package com.gridnine.testing;

import java.util.List;
import static com.gridnine.testing.TestUtils.*;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        testFilter(flights, "departsAfterCurrentTime");
        System.out.println();

        testFilter(flights, "departsBeforeArrivalTime");
        System.out.println();

        testFilter(flights, "groundTimeLessOrEqualsTwoHours");
        System.out.println();

        testMultiFilter(flights, List.of(
                "departsAfterCurrentTime",
                "departsBeforeArrivalTime",
                "groundTimeLessOrEqualsTwoHours"
        ));
    }
}
