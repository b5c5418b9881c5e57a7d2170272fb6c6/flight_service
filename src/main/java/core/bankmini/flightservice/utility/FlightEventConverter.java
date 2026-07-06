package core.bankmini.flightservice.utility;

import core.bankmini.flightservice.model.dto.events.FlightCancelledEvent;
import core.bankmini.flightservice.model.dto.events.FlightCreateEvent;
import core.bankmini.flightservice.model.entity.Flight;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class FlightEventConverter {

    public FlightCreateEvent convertCreateEvent(Flight flight){
        return new FlightCreateEvent(
                UUID.randomUUID(),
                Instant.now(),
                flight.getId(),
                flight.getFlightNumber(),
                flight.getAirline().getId(),
                flight.getAircraft().getId(),
                flight.getDepartureAirport().getId(),
                flight.getArrivalAirport().getId(),
                flight.getDepartureAt(),
                flight.getArrivalAt(),
                flight.getDurationMinutes(),
                flight.getSeatsTotal(),
                flight.getTerminal(),
                flight.getGate(),
                flight.getStatus()
        );
    }

    // todo: add update flight logic
//    public FlightUpdateEvent convertUpdateEvent(Flight flight){
//        return new FlightUpdateEvent(
//                UUID.randomUUID(),
//                Instant.now(),
//                flight.getId(),
//                flight.getFlightNumber(),
//                flight.getAirline().getId(),
//                flight.getAircraft().getId(),
//                flight.getDepartureAirport().getId(),
//                flight.getArrivalAirport().getId(),
//                flight.getDepartureAt(),
//                flight.getArrivalAt(),
//                flight.getDurationMinutes(),
//                flight.getSeatsTotal(),
//                flight.getTerminal(),
//                flight.getGate(),
//                flight.getStatus()
//        );
//    }

    public FlightCancelledEvent convertCancelEvent(Flight flight){
        return FlightCancelledEvent.builder()
                .eventId(UUID.randomUUID())
                .eventTimeStamp(Instant.now())
                .flightId(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .status(flight.getStatus())
                .build();
    }

}
