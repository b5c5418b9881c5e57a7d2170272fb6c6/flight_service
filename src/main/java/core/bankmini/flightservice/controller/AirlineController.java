package core.bankmini.flightservice.controller;

import core.bankmini.flightservice.model.dto.airline.AirlineRequestDto;
import core.bankmini.flightservice.model.dto.airline.AirlineResponseDto;
import core.bankmini.flightservice.service.AirlineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/airlines")
@RequiredArgsConstructor
@Tag(name = "Airlines controller")
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<UUID> createAirline(@Valid @RequestBody AirlineRequestDto airline) {
        return ResponseEntity.status(HttpStatus.CREATED).body(airlineService.createAirline(airline));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AirlineResponseDto>> getAllAirlines() {
        return ResponseEntity.ok().body(airlineService.getAllAirlines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponseDto> getByIdAirline(@PathVariable UUID id) {
        return ResponseEntity.ok().body(airlineService.getAirlineById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AirlineResponseDto> updateAirline(
            @PathVariable UUID id,
            @Valid @RequestBody AirlineRequestDto airline
    ) {
        return ResponseEntity.ok().body(
                airlineService.updateAirline(id, airline)
        );
    }

}
