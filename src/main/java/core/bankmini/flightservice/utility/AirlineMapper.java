package core.bankmini.flightservice.utility;

import core.bankmini.flightservice.model.dto.airline.AirlineRequestDto;
import core.bankmini.flightservice.model.dto.airline.AirlineResponseDto;
import core.bankmini.flightservice.model.entity.Airline;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AirlineMapper {

    public AirlineResponseDto airlineToAirlineResponseDto(Airline airline) {
        return AirlineResponseDto.builder()
                .id(airline.getId())
                .name(airline.getName())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .country(airline.getCountry())
                .build();
    }

    public Airline mapAirlineRequestDtoToEntity(AirlineRequestDto dto, UUID id) {
        return Airline.builder()
                .id(id)
                .name(dto.name())
                .iataCode(dto.iataCode())
                .icaoCode(dto.icaoCode())
                .country(dto.country())
                .build();
    }

    public List<AirlineResponseDto> mapAirlineEntityToListDto(List<Airline> airlines) {
        return airlines.stream()
                .map(this::mapAirlineToResponseDto)
                .toList();
    }

    private AirlineResponseDto mapAirlineToResponseDto(Airline airlines) {
        return AirlineResponseDto.builder()
                .id(airlines.getId())
                .name(airlines.getName())
                .iataCode(airlines.getIataCode())
                .icaoCode(airlines.getIcaoCode())
                .country(airlines.getCountry())
                .build();
    }

}
