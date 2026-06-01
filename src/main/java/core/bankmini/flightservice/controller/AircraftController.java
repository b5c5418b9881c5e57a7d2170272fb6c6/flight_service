package core.bankmini.flightservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/aircrafts")
@RequiredArgsConstructor
@Tag(name = "Aircraft controller")
public class AircraftController {

    @GetMapping
    public ResponseEntity<?> getAllAircrafts() {

        return ResponseEntity.ok().build();
    }


}
