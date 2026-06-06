package core.bankmini.flightservice.service;

import core.bankmini.flightservice.exceptions.AirlineNotFoundException;
import core.bankmini.flightservice.model.dto.airline.AirlineRequestDto;
import core.bankmini.flightservice.model.dto.airline.AirlineResponseDto;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.repository.AirlineRepository;
import core.bankmini.flightservice.utility.AirlineMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirlineService {
    // create get all airlines -> list airlines with UUID for front & constructor flights

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    @Transactional
    public UUID createAirline(AirlineRequestDto dto) {
        UUID id = UUID.randomUUID();

        Airline airline = airlineRepository.save(
                airlineMapper.mapAirlineRequestDtoToEntity(dto, id)
        );

        return airline.getId();
    }

    public AirlineResponseDto getAirlineById(UUID id) {
        var airline = airlineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Airline with id " + id + " not found"));
        return airlineMapper.airlineToAirlineResponseDto(airline);
    }

    public List<AirlineResponseDto> getAllAirlines() {
        var airlines = airlineRepository.findAll();
        return airlineMapper.mapAirlineEntityToListDto(airlines);
    }

    @Transactional
    public AirlineResponseDto updateAirline(UUID id, AirlineRequestDto dto) {
        var airline = airlineRepository.findById(id)
                .orElseThrow(() -> new AirlineNotFoundException(id));

        airline.setName(dto.name());
        airline.setIataCode(dto.iataCode());
        airline.setIcaoCode(dto.icaoCode());
        airline.setCountry(dto.country());

        return airlineMapper.airlineToAirlineResponseDto(airline);
    }


}
