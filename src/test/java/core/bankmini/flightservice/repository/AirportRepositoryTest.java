package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.config.TestcontainersConfiguration;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.util.AirportDataUtil;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;


@Log4j2
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestcontainersConfiguration.class)
class AirportRepositoryTest {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private EntityManager entityManager;

    private Airport almaty;
    private Airport astana;
    private Airport frankfurt;

    @BeforeEach
    void setUp() {
        almaty = AirportDataUtil.createAirport(
                "Almaty International Airport",
                "Kazakhstan",
                "Almaty",
                "ALA",
                "AL",
                "Test/AL"
        );

        astana = AirportDataUtil.createAirport(
                "Nursultan Nazarbayev International Airport",
                "Kazakhstan",
                "Astana",
                "NQZ",
                "NQ",
                "Test/NQ"
        );

        frankfurt = AirportDataUtil.createAirport(
                "Frankfurt Airport",
                "Germany",
                "Frankfurt",
                "FRA",
                "FR",
                "Test/FRA"
        );

        entityManager.persist(almaty);
        entityManager.persist(astana);
        entityManager.persist(frankfurt);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("Test save airport functionality")
    void givenAirportObject_whenSave_thenAirportIsCreated() {
        //given
        UUID id = UUID.randomUUID();
        Airport airport = AirportDataUtil.getAirportPersisted(id);
        //when
        Airport savedAirport = airportRepository.save(airport);
        //then
        assertThat(savedAirport).isNotNull();
        assertThat(savedAirport.getId()).isEqualTo(airport.getId());
    }

    @Test
    void findNullByName() {
        //given
        Airport airport1 = AirportDataUtil.createAirport("Airport name 3", "Country3", "City3", "THR", "3", "Timezone/THR");
        //when
        airportRepository.save(airport1);
        Airport airport = airportRepository.findByName("Test Airport first");
        //then
        assertThat(airport).isNull();
    }

    @Test
    void givenAllParamsNull_thenGetAllAirports() {
        //given

        //when
        airportRepository.saveAll(List.of(almaty, astana, frankfurt));
        log.info("Create all airports: {}, {}, {}", almaty.getId(), astana.getId(), frankfurt.getId());
        List<Airport> result = airportRepository.searchAirportsByQueries(null, null, null);

        //then
        assertThat(result)
                .hasSize(3)
                .extracting(Airport::getIataCode)
                .containsExactlyInAnyOrder("ALA", "NQZ", "FRA");
    }

    @Test
    void shouldFilteredAirportsByCountry(){
        //given

        //when
        airportRepository.saveAll(List.of(almaty, astana, frankfurt));
        List<Airport> result = airportRepository.searchAirportsByQueries(
                "Kazakhstan", null, null);

        //then
        assertThat(result)
                .hasSize(2)
                .extracting(Airport::getIataCode)
                .containsExactlyInAnyOrder("ALA", "NQZ");
    }

    @Test
    void searchCountryAndCityByAirport() {
        // given

        //when
        List<Airport> searchByQueries = airportRepository.searchAirportsByQueries(
                "Kazakhstan", "Almaty", null);
        //then
        assertThat(searchByQueries.size()).isNotZero();
        assertThat(searchByQueries.size()).isEqualTo(1);
    }

    @Test
    void searchCountryAndCityAndIatacodeByAirport() {
        // given

        //when
        List<Airport> searchByQueries = airportRepository.searchAirportsByQueries(
                "Kazakhstan", "Almaty", "ALA");
        //then
        assertThat(searchByQueries.size()).isNotZero();
        assertThat(searchByQueries.size()).isEqualTo(1);
    }

}