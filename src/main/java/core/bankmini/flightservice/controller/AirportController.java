package core.bankmini.flightservice.controller;

import core.bankmini.flightservice.model.dto.airport.AirportRequestDto;
import core.bankmini.flightservice.model.dto.airport.AirportResponseDto;
import core.bankmini.flightservice.service.AirportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/airports")
@RequiredArgsConstructor
@Tag(name = "Airport controller")
public class AirportController {

    private final AirportService airportService;

    @PostMapping
    public ResponseEntity<UUID> createAirport(@Valid @RequestBody AirportRequestDto airport) {
        var airportId = airportService.createAirport(airport);
        return ResponseEntity.status(HttpStatus.CREATED).body(airportId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirportResponseDto>> getAllAirport() {
        var airports = airportService.getAllAirports();
        return ResponseEntity.ok().body(airports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponseDto> getAirportById(@PathVariable UUID id) {
        var airport = airportService.getAirportById(id);
        return ResponseEntity.ok().body(airport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAirport(
            @PathVariable UUID id,
            @Valid @RequestBody AirportRequestDto airportDto
    ) {
        airportService.updateAirport(id, airportDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AirportResponseDto>> searchAirport(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String iataCode
    ) {
        var listAirports = airportService.searchAirport(country, city, iataCode);
        return ResponseEntity.ok().body(listAirports);
    }

}