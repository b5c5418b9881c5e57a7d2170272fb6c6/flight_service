package core.bankmini.flightservice.model.dto.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record CreateFlightDtoRequest(
        String flightNumber,
        UUID airlineId,
        UUID aircraftId,
        UUID departureAirport,
        UUID arrivalAirport,
        OffsetDateTime departureAt,
        OffsetDateTime arrivalAt,
        Integer durationMinutes,
        String terminal,
        String gate
) {
}
