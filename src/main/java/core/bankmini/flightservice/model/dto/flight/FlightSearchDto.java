package core.bankmini.flightservice.model.dto.flight;

import core.bankmini.flightservice.model.enums.FlightStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record FlightSearchDto(
        @NotNull(message = "Fight id is required")
        UUID id,

        @NotBlank(message = "Flight number is required")
        String flightNumber,

        @NotNull(message = "Airline id is required")
        UUID airlineId,

        @NotNull(message = "Aircraft id is required")
        UUID aircraftId,

        @NotNull(message = "Departure airport id is required")
        UUID departureAirportId,

        @NotNull(message = "Arrival airport id is required")
        UUID arrivalAirportId,

        @NotNull(message = "Departure at is required")
        @Future
        ZonedDateTime departureAt,

        @NotNull(message = "Arrival at is required")
        @Future
        ZonedDateTime arrivalAt,

        @NotNull(message = "Duration minutes is required")
        Integer durationMinutes,

        @NotNull(message = "Seats total is required")
        Integer seatsTotal,

        @NotBlank(message = "Terminal can't be null")
        String terminal,

        @NotBlank(message = "Gate can't be null")
        String gate,

        @NotBlank(message = "Status can't be null")
        FlightStatus status,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
