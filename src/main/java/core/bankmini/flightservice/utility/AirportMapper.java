package core.bankmini.flightservice.utility;

import core.bankmini.flightservice.model.dto.airport.AirportRequestDto;
import core.bankmini.flightservice.model.dto.airport.AirportResponseDto;
import core.bankmini.flightservice.model.entity.Airport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class AirportMapper {

    public AirportResponseDto mapEntityToDto(Airport airport) {
        return AirportResponseDto.builder()
                .id(airport.getId())
                .name(airport.getName())
                .iataCode(airport.getIataCode())
                .icaoCode(airport.getIcaoCode())
                .city(airport.getCity())
                .country(airport.getCountry())
                .timezone(airport.getTimezone())
                .minLayoverMinutes(airport.getMinLayoverMinutes())
                .internationalAirport(airport.getInternationalAirport())
                .build();
    }

    public Airport mapEntityToDto(AirportRequestDto dto, UUID id) {
        return Airport.builder()
                .id(id)
                .iataCode(Objects.requireNonNull(dto.iataCode().toUpperCase()))
                .icaoCode(Objects.requireNonNull(dto.icaoCode()).toUpperCase())
                .name(dto.name())
                .city(dto.city())
                .country(dto.country())
                .timezone(dto.timezone())
                .minLayoverMinutes(dto.minLayoverMinutes())
                .internationalAirport(dto.internationalAirport())
                .build();
    }

    public List<AirportResponseDto> mapAirportEntityToListDto(List<Airport> airports) {
        return airports.stream()
                .map(this::mapAirportToResponseDto)
                .toList();
    }

    private AirportResponseDto mapAirportToResponseDto(Airport airport) {
        return AirportResponseDto.builder()
                .id(airport.getId())
                .name(airport.getName())
                .iataCode(airport.getIataCode())
                .icaoCode(airport.getIcaoCode())
                .city(airport.getCity())
                .country(airport.getCountry())
                .timezone(airport.getTimezone())
                .minLayoverMinutes(airport.getMinLayoverMinutes())
                .internationalAirport(airport.getInternationalAirport())
                .build();
    }

}
