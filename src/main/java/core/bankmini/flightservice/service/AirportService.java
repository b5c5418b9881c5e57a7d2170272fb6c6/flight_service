package core.bankmini.flightservice.service;

import core.bankmini.flightservice.exceptions.AirportAlreadyExistsException;
import core.bankmini.flightservice.model.dto.airport.AirportRequestDto;
import core.bankmini.flightservice.model.dto.airport.AirportResponseDto;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.repository.AirportRepository;
import core.bankmini.flightservice.utility.AirportMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Transactional
    public UUID createAirport(AirportRequestDto dto) {

        Airport airportAlreadySaved = airportRepository.findByName(dto.name());

        if (airportAlreadySaved != null) {
            throw new AirportAlreadyExistsException(airportAlreadySaved.getName());
        }

        Airport airport = airportMapper.mapEntityToDto(dto, UUID.randomUUID());
        UUID airportId = airportRepository.save(airport).getId();
        log.info("AirportService: Created airport with id {}", airport.getId());

        return airportId;
    }


    public List<AirportResponseDto> getAllAirports() {
        var airports = airportRepository.findAll();
        log.info("AirportService: Found {} airports", airports.size());
        return airportMapper.mapAirportEntityToListDto(airports);
    }

    public AirportResponseDto getAirportById(
            @NotNull(message = "Airport id is required")
            UUID id
    ) {
        var airport = airportRepository.findById(id);
        log.info("AirportService: Found airport with id {}", id);
        return airportMapper.mapEntityToDto(airport
                .orElseThrow(() -> new IllegalArgumentException("Airport with id " + id + " not found"))
        );
    }

    @Transactional
    public void updateAirport(
            @NotNull(message = "Airport id is required")
            UUID id,
            AirportRequestDto airportDto
    ) {
        var airportActual = airportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Airport with id " + id + " not found"));

        airportActual.setIataCode(airportDto.iataCode());
        airportActual.setIcaoCode(airportDto.icaoCode());
        airportActual.setName(airportDto.name());
        airportActual.setCity(airportDto.city());
        airportActual.setCountry(airportDto.country());
        airportActual.setTimezone(airportDto.timezone());
        airportActual.setMinLayoverMinutes(airportDto.minLayoverMinutes());
        airportActual.setInternationalAirport(airportDto.internationalAirport());

        log.info("AirportService: Updated airport with id {}", id);
    }

    @Transactional
    public List<AirportResponseDto> searchAirport(
            String country,
            String city,
            String iataCode
    ) {
        var listAirports = airportRepository.searchAirportsByQueries(country, city, iataCode);
        log.info("Find by search airports data size: {}", listAirports.size());
        return airportMapper.mapAirportEntityToListDto(listAirports);
    }
}
