package core.bankmini.flightservice.model.dto.airline;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AirlineResponseDto(

        @NotNull(message = "Airline id is required")
        UUID id,

        @NotBlank(message = "Airline name is required")
        String name,

        @NotBlank(message = "Iata code is required")
        String iataCode,

        @NotBlank(message = "Icao code is required")
        String icaoCode,

        @NotBlank(message = "Country is required")
        String country
) {
}
