package core.bankmini.flightservice.repository;

import core.bankmini.flightservice.config.TestcontainersConfiguration;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.util.AirportDataUtil;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
class AirportRepositoryTest {

    @Autowired
    public AirportRepository repository;

    @Test
    @DisplayName("Test save airport functionality")
    void givenAirportObject_whenSave_thenAirportIsCreated() {
        //given
        UUID id = UUID.randomUUID();
        Airport airport = AirportDataUtil.getAirportPersisted(id);
        //when
        Airport savedAirport = repository.save(airport);
        //then
        assertThat(savedAirport).isNotNull();
        assertThat(savedAirport.getId()).isEqualTo(airport.getId());
    }


    @Test
    void searchAirportsByQueries() {
        // given
        Airport airport1 = AirportDataUtil.getAirportPersistedWithQueryCountryCityIataCodeFist(
                UUID.randomUUID(), "Country1", "City1", "ONE");
        Airport airport2 = AirportDataUtil.getAirportTransient();
        Airport airport3 = AirportDataUtil.getAirportPersistedWithQueryCountryCityIataCodeSecond(
                UUID.randomUUID(), "Country1", "City1", "A2R");
        // when
        List<Airport> airportsSaved = repository.saveAll(List.of(airport1, airport2, airport3));
        List<Airport> searchByQueries = repository.searchAirportsByQueries(
                "Country1", "City1", null
        );
        //then
        System.out.println("Search by queries: " + searchByQueries);
        assertThat(airportsSaved.size()).isNotEqualTo(searchByQueries.isEmpty());
        assertThat(searchByQueries.size()).isEqualTo(2);
    }

    @Test
    void findByName() {
        //given
        Airport airport1 = AirportDataUtil.getAirportPersistedWithQueryCountryCityIataCodeFist(
                UUID.randomUUID(), "Country1", "City1", "ONE");
        //when
        repository.save(airport1);
        Airport airport = repository.findByName("Test Airport first");
        System.out.println("Airport: " + airport);
        //then
        assertThat(airport).isNotNull();
        assertThat(airport.getIcaoCode()).isEqualTo(airport1.getIcaoCode());
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