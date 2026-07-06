package core.bankmini.flightservice.model.dto.events;

import core.bankmini.flightservice.model.enums.FlightStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record FlightCancelledEvent(
        UUID eventId,
        Instant eventTimeStamp,
        UUID flightId,
        String flightNumber,
        FlightStatus status
) {
}
