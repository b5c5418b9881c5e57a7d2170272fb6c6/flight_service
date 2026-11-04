package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.config.TestcontainersConfiguration;
import core.bankmini.flightservice.model.entity.Airline;
import core.bankmini.flightservice.util.AirlineDataUtil;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Log4j2
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestcontainersConfiguration.class)
public class AirlineRepositoryTest {

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private EntityManager entityManager;

    private Airline almaty;
    private Airline astana;
    private Airline frankfurt;

    @BeforeEach
    void setUp() {
        almaty = AirlineDataUtil.createAirline("ALA", "AL");
        astana = AirlineDataUtil.createAirline("NQZ", "NQ");
        frankfurt = AirlineDataUtil.createAirline("FRA", "FR");

        entityManager.persist(almaty);
        entityManager.persist(astana);
        entityManager.persist(frankfurt);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByIataCode() {
        //given
        //when
        Airline airline = airlineRepository.findByIataCode("ALA");
        //then
        assertThat(airline.getId()).isEqualTo(almaty.getId());
        assertThat(airline.getIataCode()).isEqualTo(almaty.getIataCode());
        assertThat(airline.getName()).isEqualTo(almaty.getName());
    }

    @Test
    void findByIcaoCode() {
        //given
        //when
        Airline airline = airlineRepository.findByIcaoCode("NQ");
        //then
        assertThat(airline.getId()).isEqualTo(astana.getId());
        assertThat(airline.getIataCode()).isEqualTo(astana.getIataCode());
        assertThat(airline.getName()).isEqualTo(astana.getName());
    }

    @Test
    void searchAirlinesByQueriesWithAllParameters() {
        //given
        //when
        List<Airline> airline = airlineRepository
                .searchAirlinesByQueries("ALA", "AL", "Airline name ALA");
        //then
        assertThat(airline.get(0).getId()).isEqualTo(almaty.getId());
        assertThat(airline.get(0).getIataCode()).isEqualTo(almaty.getIataCode());
        assertThat(airline.get(0).getName()).isEqualTo(almaty.getName());
    }

    @Test
    void searchAirlinesByQueriesWithIcaoAndIataParameters() {
        //given
        //when
        List<Airline> airline = airlineRepository
                .searchAirlinesByQueries("ALA", "AL", null);
        //then
        assertThat(airline)
                .hasSize(1)
                .extracting(Airline::getId)
                .containsExactly(almaty.getId());
    }

    @Test
    void searchAirlinesByQueriesWithIcaoParameters() {
        //given
        //when
        List<Airline> airline = airlineRepository
                .searchAirlinesByQueries(null, "NQ", null);
        //then
        assertThat(airline)
                .hasSize(1)
                .extracting(Airline::getId)
                .containsExactly(astana.getId());
    }

    @Test
    void searchAirlinesByQueriesWithIcaoAndNameParameters() {
        //given
        //when
        List<Airline> airline = airlineRepository
                .searchAirlinesByQueries(null, "AL", "Airline name ALA");
        //then
        assertThat(airline.get(0).getId()).isEqualTo(almaty.getId());
        assertThat(airline.get(0).getIataCode()).isEqualTo(almaty.getIataCode());
        assertThat(airline.get(0).getName()).isEqualTo(almaty.getName());
    }

    @Test
    void searchAirlinesByQueriesWithIataAndNameParameters() {
        //given
        //when
        List<Airline> airline = airlineRepository
                .searchAirlinesByQueries("ALA", null, "Airline name ALA");
        //then
        assertThat(airline.get(0).getId()).isEqualTo(almaty.getId());
        assertThat(airline.get(0).getIataCode()).isEqualTo(almaty.getIataCode());
        assertThat(airline.get(0).getName()).isEqualTo(almaty.getName());
    }

    @Test
    void searchAirlinesByQueriesWithoutParameters() {
        //given
        //when
        List<Airline> airline = airlineRepository
                .searchAirlinesByQueries(null, null, null);
        //then
        assertThat(airline).hasSize(3);
    }

    @Test
    void searchAirlinesByQueriesWithNonExistingParameters() {
        // when
        List<Airline> airlines = airlineRepository
                .searchAirlinesByQueries("XXX", null, null);

        // then
        assertThat(airlines).isEmpty();
    }

    @Test
    void existsByIataCodeAndIcaoCode() {
        //given
        //when
        boolean existAirline = airlineRepository
                .existsByIataCodeAndIcaoCode("ALA", "AL");
        //then
        assertThat(existAirline).isTrue();
    }

    @Test
    void notExistsByIataCodeAndIcaoCode() {
        //given
        //when
        boolean notExistAirline = airlineRepository
                .existsByIataCodeAndIcaoCode("ABO", "X");
        //then
        assertThat(notExistAirline).isFalse();
    }
}
