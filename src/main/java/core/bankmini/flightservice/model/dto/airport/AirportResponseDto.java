package core.bankmini.flightservice.model.dto.airport;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AirportResponseDto(
        UUID id,

        String name,

        String iataCode,

        String icaoCode,

        String city,

        String country,

        String timezone,

        Integer minLayoverMinutes,

        Boolean internationalAirport
) {
}
