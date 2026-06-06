package core.bankmini.flightservice.model.dto.flight;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FlightDtoResponse(
        @NotNull(message = "Fight id is required")
        UUID id
) {
}
