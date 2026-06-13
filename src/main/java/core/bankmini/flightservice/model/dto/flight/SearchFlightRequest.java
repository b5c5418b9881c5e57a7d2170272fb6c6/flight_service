package core.bankmini.flightservice.model.dto.flight;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SearchFlightRequest(
        @NotBlank(message = "Flight airport from IATA code is required")
        String from,

        @NotBlank(message = "Flight airport to IATA code is required")
        String to,

//        @NotNull(message = "Departure date is required")
//        @Future
        LocalDate departureDate,

//        @NotNull(message = "Departure date is required")
//        @Future
        LocalDate arrivalDate
) {
}
