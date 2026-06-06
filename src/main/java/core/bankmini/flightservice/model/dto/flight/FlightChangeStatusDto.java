package core.bankmini.flightservice.model.dto.flight;

import lombok.Builder;

import java.util.UUID;

@Builder
public record FlightChangeStatusDto(
        UUID id,
        String status
) {
}
