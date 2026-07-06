package core.bankmini.flightservice.controller;

import core.bankmini.flightservice.model.dto.flight.*;
import core.bankmini.flightservice.service.FlightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
@Tag(name = "Flights controller", description = "Working with flight's, airline's, airport's and aircraft's")
public class FlightsController {

    private final FlightService flightService;

    @PostMapping(name = "Create flight")
    public ResponseEntity<FlightDtoResponse> createFlight(@Valid @RequestBody CreateFlightDtoRequest dtoRequest) {
        log.info("Create flight request {}", dtoRequest);
        UUID flightId = flightService.createFlight(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(FlightDtoResponse.builder().id(flightId).build());
    }

    @GetMapping(value = "/{id}", name = "Get flight by id")
    public ResponseEntity<FlightSearchDto> getFlightById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(flightService.getFlightById(id));
    }

    @PatchMapping(value = "/status", name = "Change flight status")
    public ResponseEntity<Void> changeFlightStatus(@Valid @RequestBody FlightChangeStatusDto dto) {
        flightService.changeFlightStatus(dto.id(), dto.status());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/status/{id}/canceled", name = "Cancel status flight")
    public ResponseEntity<Void> cancelFlight(@PathVariable UUID id) {
        flightService.cancelFlight(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/search", name = "Search flights by parameters")
    public ResponseEntity<List<FlightSearchDto>> searchFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid @RequestBody SearchFlightRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        flightService.searchAllFlights(
                                request.from(),
                                request.to(),
                                request.departureDate(),
                                request.arrivalDate(),
                                page, size
                        )
                );
    }

    @GetMapping(value = "/number", name = "Get flight by number")
    public ResponseEntity<FlightSearchDto> getFlightByNumber(@Valid @RequestParam String flightNumber) {
        return ResponseEntity.ok().body(flightService.getFlightByNumber(flightNumber));
    }

}
