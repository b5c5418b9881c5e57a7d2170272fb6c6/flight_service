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

    @PostMapping("/create")
    @Tag(name = "Create flights")
    public ResponseEntity<FlightDtoResponse> createFlight(@RequestBody CreateFlightDtoRequest dtoRequest) {
        UUID flightId = flightService.createFlight(dtoRequest);
        return ResponseEntity.ok().body(new FlightDtoResponse(flightId));
    }

    @GetMapping("/{id}")
    @Tag(name = "Поиск по Id рейса", description = "Получить инфо по рейсу")
    public ResponseEntity<FlightSearchDto> getFlightById(@Valid @PathVariable UUID id) {
        return ResponseEntity.ok().body(flightService.getFlightById(id));
    }

    @PatchMapping("/status")
    @Tag(name = "Изменение статуса рейса")
    public ResponseEntity<Void> changeFlightStatus(@RequestBody FlightChangeStatusDto dto) {
        flightService.changeFlightStatus(dto.id(), dto.status());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/status/{id}/canceled")
    @Tag(name = "Cancel flight status")
    public ResponseEntity<Void> cancelFlight(@Valid @PathVariable UUID id) {
        flightService.cancelFlight(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/search")
    @Tag(name = "Search flights", description = "Search all flights by filters")
    public ResponseEntity<List<FlightSearchDto>> searchFlights(@RequestBody SearchFlightRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        flightService.searchAllFlights(
                                request.from(),
                                request.to(),
                                request.departureDate(),
                                request.arrivalDate()
                        )
                );
    }

    @GetMapping("/number")
    @Tag(name = "Find by Flight number", description = "Example: Номер рейса KC123")
    public ResponseEntity<FlightSearchDto> getFlightByNumber(@Valid @RequestParam String number) {
        return ResponseEntity.ok().body(flightService.getFlightByNumber(number));
    }

}
