package core.bankmini.flightservice.service;

import core.bankmini.flightservice.exceptions.AirlineNotFoundException;
import core.bankmini.flightservice.exceptions.CodeAlreadyExistException;
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

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    @Transactional
    public UUID createAirline(AirlineRequestDto dto) {

        try {
            String iataCode = airlineRepository.findByIataCode(dto.iataCode()).getIataCode();
            String icaoCode = airlineRepository.findByIcaoCode(dto.icaoCode()).getIcaoCode();

            if (!dto.iataCode().equals(iataCode)) {
                throw new CodeAlreadyExistException(iataCode, iataCode.getClass().getName());
            }
            if (!dto.icaoCode().equals(icaoCode)) {
                throw new CodeAlreadyExistException(icaoCode, icaoCode.getClass().getName());
            }
        } catch (NullPointerException npe) {
            throw new NullPointerException(npe.getMessage());
        }

        UUID id = UUID.randomUUID();

        Airline airline = airlineRepository.save(
                airlineMapper.mapAirlineRequestDtoToEntity(dto, id)
        );

        log.info("AirlineService: Airline with id {} created", airline.getId());

        return airline.getId();
    }

    public AirlineResponseDto getAirlineById(UUID id) {
        var airline = airlineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Airline with id " + id + " not found"));
        log.info("AirlineService: Airline with id {} found", id);
        return airlineMapper.airlineToAirlineResponseDto(airline);
    }

    public List<AirlineResponseDto> getAllAirlines() {

        var airlines = airlineRepository.findAll();

        log.info("AirlineService: Get all Airlines");

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

        log.info("AirlineService: Airline with id {} updated", id);

        return airlineMapper.airlineToAirlineResponseDto(airline);
    }


}
