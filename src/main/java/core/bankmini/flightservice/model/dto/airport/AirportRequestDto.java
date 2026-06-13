package core.bankmini.flightservice.model.dto.airport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AirportRequestDto(

        @NotBlank(message = "Airport name is required")
        String name,

        @NotBlank(message = "Airport IATA code is required")
        String iataCode,

        String icaoCode,

        @NotBlank(message = "City of airport is required")
        String city,

        @NotBlank(message = "Country airport is required")
        String country,

        @Pattern(
                regexp = "^[a-zA-z]+/[A-Za-z]+?$",
                message = "Неверный формат временной зоны. Используйте формат 'Регион/Город'"
        )
        @NotBlank(message = "Airport time zone is required")
        String timezone,

        @NotNull(message = "Minimum layover minutes id is required")
        Integer minLayoverMinutes,

        @NotNull(message = "Is international airport parameter required")
        Boolean internationalAirport
) {
}
