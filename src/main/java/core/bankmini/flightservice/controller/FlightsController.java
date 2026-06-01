package core.bankmini.flightservice.controller;

import core.bankmini.flightservice.model.dto.flight.CreateFlightDtoRequest;
import core.bankmini.flightservice.model.dto.flight.FlightDtoResponse;
import core.bankmini.flightservice.service.FlightService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
@Tag(name = "Flights controller", description = "Working with flight's, airline's, airport's and aircraft's")
public class FlightsController {

    private final FlightService flightService;

    @PostMapping("/create")
    @Tag(name = "Create flights", description = "")
    public ResponseEntity<?> createFlight(@RequestBody CreateFlightDtoRequest dtoRequest) {
        UUID flightId = flightService.createFlight(dtoRequest);
        return ResponseEntity.ok().body(new FlightDtoResponse(flightId));
    }


//    /// GET /flights/search?
//    /// from=ALA&
//    /// to=FRA&
//    /// date=2026-06-10
//    ///
//    @GetMapping
//    @Tag(name = "Search flights", description = "Search all flights by filters")
//    public ResponseEntity<List<FlightDtoResponse>> searchFlights(
//            @RequestParam
//            String from,     // departureAirportName
//            @RequestParam
//            String to,       // arrivalAirportName
//            @RequestParam(required = false)
//            LocalDate date,   // departureDate
//            HttpServletRequest request
//    ){
//        return ResponseEntity.ok().body();
//    }

}
