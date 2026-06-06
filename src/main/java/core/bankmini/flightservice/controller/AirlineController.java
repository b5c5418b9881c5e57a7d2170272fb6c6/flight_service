package core.bankmini.flightservice.controller;

import core.bankmini.flightservice.model.dto.airline.AirlineRequestDto;
import core.bankmini.flightservice.model.dto.airline.AirlineResponseDto;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.repository.AirlineRepository;
import core.bankmini.flightservice.service.AirlineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/airlines")
@RequiredArgsConstructor
@Tag(name = "Airlines controller")
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping("/create")
    public ResponseEntity<UUID> createAirline(@RequestBody AirlineRequestDto airline) {
        return ResponseEntity.ok().body(airlineService.createAirline(airline));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirlineResponseDto>> getAllAirlines() {
        return ResponseEntity.ok().body(airlineService.getAllAirlines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponseDto> getAllAirlines(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(airlineService.getAirlineById(id));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<AirlineResponseDto> updateAirline(
            @Valid @PathVariable UUID id,
            @RequestBody AirlineRequestDto airline
    ) {
        return ResponseEntity.ok().body(
                airlineService.updateAirline(id, airline)
        );
    }

}
