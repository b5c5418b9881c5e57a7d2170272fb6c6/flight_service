package core.bankmini.flightservice.model.dto.events;

import core.bankmini.flightservice.model.enums.FlightStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record FlightCreateEvent(
        UUID eventId,
        Instant eventTimeStamp,
        UUID flightId,
        String flightNumber,
        UUID airlineId,
        UUID aircraftId,
        UUID departureAirportId,
        UUID arrivalAirportId,
        LocalDateTime departureAt,
        LocalDateTime arrivalAt,
        Integer durationMinutes,
        Integer seatsTotal,
        String terminal,
        String gate,
        FlightStatus status
        ) {
}
