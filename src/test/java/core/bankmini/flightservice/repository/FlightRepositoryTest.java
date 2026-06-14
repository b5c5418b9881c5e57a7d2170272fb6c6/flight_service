package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.config.IntegrationTest;
import core.bankmini.flightservice.model.entity.Aircraft;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.model.entity.Flight;
import core.bankmini.flightservice.model.enums.FlightStatus;
import core.bankmini.flightservice.util.AircraftDataUtil;
import core.bankmini.flightservice.util.AirlineDataUtil;
import core.bankmini.flightservice.util.AirportDataUtil;
import core.bankmini.flightservice.util.FlightDataUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightRepositoryTest extends IntegrationTest {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    AirportRepository airportRepository;

    @Autowired
    AircraftRepository aircraftRepository;

    @Autowired
    AirlineRepository airlineRepository;

    private Airline airline;
    private Aircraft aircraft;
    private Airport departure;
    private Airport arrival;

    @BeforeAll
    void setUp() {
        airline = airlineRepository.save(
                Airline.builder()
                        .id(UUID.randomUUID())
                        .name("Air Astana")
                        .iataCode("AST")
                        .icaoCode("CA")
                        .country("Kazakhstan")
                        .build()
        );
        System.out.println("Created airline: " + airline.getId());

        aircraft = aircraftRepository.save(
                Aircraft.builder()
                        .id(UUID.randomUUID())
                        .model("A320")
                        .manufactured("sho?")
                        .seatsTotal(180)
                        .build()
        );
        System.out.println("Created aircraft: " + aircraft.getId());

        departure = airportRepository.save(
                Airport.builder()
                        .id(UUID.randomUUID())
                        .iataCode("ALA")
                        .icaoCode("A")
                        .name("Almaty International Airport")
                        .city("Almaty")
                        .country("Kazakhstan")
                        .timezone("Asia/Almaty")
                        .minLayoverMinutes(30)
                        .internationalAirport(true)
                        .build()
        );
        System.out.println("Created departure: " + departure.getId());

        arrival = airportRepository.save(
                Airport.builder()
                        .id(UUID.randomUUID())
                        .iataCode("AAA")
                        .icaoCode("C")
                        .name("Astana International Airport")
                        .city("Astana")
                        .country("Kazakhstan")
                        .timezone("Asia/Astana")
                        .minLayoverMinutes(3)
                        .internationalAirport(false)
                        .build()
        );
        System.out.println("Created arrival: " + arrival.getId());

        Aircraft aircraft1 = AircraftDataUtil.getAircraftObject();
        Aircraft aircraft2 = AircraftDataUtil.getAircraftObject();
        Aircraft aircraft3 = AircraftDataUtil.getAircraftObject();

        Airline airline1 = AirlineDataUtil.getAirlineObject("ATA", "AB");
        Airline airline2 = AirlineDataUtil.getAirlineObject("X", "YX");
        Airline airline3 = AirlineDataUtil.getAirlineObject("ABO", "BA");

        Airport departure1 = AirportDataUtil.getAirportObject("ATA", "AB", "Almaty", "Kaz");
        Airport departure2 = AirportDataUtil.getAirportObject("X", "YX", "IKS", "SOME");
        Airport departure3 = AirportDataUtil.getAirportObject("ABO", "BA", "Moscow", "Russia");

        Airport arrivals1 = AirportDataUtil.getAirportObject("AST", "AS", "Astana", "Kaz");
        Airport arrivals2 = AirportDataUtil.getAirportObject("Y", "XY", "TEST", "WERE");
        Airport arrivals3 = AirportDataUtil.getAirportObject("BA", "ABO", "Zimbabwe", "Africa");

        aircraftRepository.saveAll(List.of(aircraft1, aircraft2, aircraft3));
        airlineRepository.saveAll(List.of(airline1, airline2, airline3));
        airportRepository.saveAll(List.of(departure1, departure2, departure3));
        airportRepository.saveAll(List.of(arrivals1, arrivals2, arrivals3));

        flightRepository.saveAll(
                List.of(
                        FlightDataUtil.getFlightPersisted(
                                airline1, aircraft1, departure1, arrivals1, "FL1"
                        ),
                        FlightDataUtil.getFlightPersisted(
                                airline2, aircraft2, departure2, arrivals2, "FL2"
                        ),
                        FlightDataUtil.getFlightPersisted(
                                airline3, aircraft3, departure3, arrivals3, "FL3"
                        )

                )
        );

    }

    @Test
    void shouldCreateFlight() {

        Flight flight = FlightDataUtil.getFlightPersisted(
                airline, aircraft, departure, arrival, "KC123"
        );

        flightRepository.save(flight);

        System.out.println("saved flight: " + flight);

        assertThat(flightRepository.count()).isEqualTo(4);
        assertThat(flight.getId()).isEqualTo(flightRepository.findById(flight.getId()).get().getId());
        List<Flight> flights = flightRepository.findAll();
        flights.forEach(System.out::println);
    }

    @Test
    void searchAllFlightsWithDeparture() {
        //given

        //when

        //then
    }

    @Test
    void searchAllFlightsWithArrival() {

    }

    @Test
    void searchAllFlightsWithoutDate() {

    }

    @Test
    void searchAllFlightsWithDate() {

    }

    @Test
    void findByFlightNumber() {
        //given

        //when
        Flight flight = flightRepository.findByFlightNumber("FL1");
        //then
        assertThat(flight).isNotNull();
        assertThat(flight.getFlightNumber()).isEqualTo("FL1");
    }

    @Test
    void cancelExpiredFlights() {
        //given
        Aircraft aircraft1 = AircraftDataUtil.getAircraftObject();
        Airline airline1 = AirlineDataUtil.getAirlineObject("1", "4");
        Airport departure1 = AirportDataUtil.getAirportObject("2", "5", "NULL", "Kaz");
        Airport arrivals1 = AirportDataUtil.getAirportObject("3", "6", "qwer", "Kaz");

        airlineRepository.save(airline1);
        aircraftRepository.save(aircraft1);
        airportRepository.saveAll(List.of(departure1, arrivals1));

        Flight flight = FlightDataUtil.getFlightPersisted(
                airline1, aircraft1, departure1, arrivals1, "cancelTest", LocalDateTime.now().minusDays(2));

        //when
        flightRepository.save(flight);

        int updated = flightRepository.cancelExpiredFlights(LocalDateTime.now());

        //then
        assertThat(updated).isGreaterThan(0);
        assertThat(flightRepository.findByFlightNumber("cancelTest").getStatus()).isEqualTo(FlightStatus.CANCELLED);
    }

    @Test
    void existsByFlightNumberIsPositiveTest() {
        //given
        Aircraft aircraft1 = AircraftDataUtil.getAircraftObject();
        Airline airline1 = AirlineDataUtil.getAirlineObject("11", "44");
        Airport departure1 = AirportDataUtil.getAirportObject("22", "55", "NULL", "Kaz");
        Airport arrivals1 = AirportDataUtil.getAirportObject("33", "66", "qwer", "Kaz");

        airlineRepository.save(airline1);
        aircraftRepository.save(aircraft1);
        airportRepository.saveAll(List.of(departure1, arrivals1));

        Flight flight = FlightDataUtil.getFlightPersisted(
                airline1, aircraft1, departure1, arrivals1, "existTest", LocalDateTime.now().minusDays(2));
        flightRepository.save(flight);

        //when
        boolean exist = flightRepository.existsByFlightNumberIs("existTest");
        //then
        assertThat(exist).isEqualTo(true);
    }

    @Test
    void existsByFlightNumberIsNegativeTest() {
        // given

        //when
        boolean exist = flightRepository.existsByFlightNumberIs("NOTEXISTOBJECT");
        //then
        assertThat(exist).isEqualTo(false);
    }

}