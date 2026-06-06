package core.bankmini.flightservice.model.dto.flight;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SearchFlightRequest(
        String from,
        String to,
        LocalDate departureDate,
        LocalDate arrivalDate
) {
}
