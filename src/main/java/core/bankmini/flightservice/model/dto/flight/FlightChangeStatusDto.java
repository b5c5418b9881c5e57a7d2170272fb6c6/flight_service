package core.bankmini.flightservice.model.dto.flight;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record FlightChangeStatusDto(
        @NotBlank(message = "Flight ID is required")
        UUID id,
        @NotBlank(message = "Flight status is required")
        String status
) {
}
