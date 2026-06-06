package core.bankmini.flightservice.model.dto.flight;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record CreateFlightDtoRequest(

        @NotBlank(message = "Flight number is required")
        @Pattern(
                regexp = "^[A-Z]{2}\\d{2,4}$",
                message = "Flight number must be like KC123"
        )
        String flightNumber,

        @NotNull(message = "Airline id is required")
        UUID airlineId,

        @NotNull(message = "Aircraft id is required")
        UUID aircraftId,

        @NotNull(message = "Departure airport id is required")
        UUID departureAirport,

        @NotNull(message = "Arrival airport id is required")
        UUID arrivalAirport,

        @NotNull(message = "Departure time is required")
        @Future
        OffsetDateTime departureAt,

        @NotNull(message = "Arrival time is required")
        @Future
        OffsetDateTime arrivalAt,

        @NotNull(message = "Duration minutes is required")
        @Positive
        Integer durationMinutes,

        @NotBlank(message = "Terminal is required")
        @Size(max = 10)
        String terminal,

        @NotBlank(message = "Gate is required")
        @Size(max = 10)
        String gate
) {
}
