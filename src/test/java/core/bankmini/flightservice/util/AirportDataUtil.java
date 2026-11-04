package core.bankmini.flightservice.util;

import core.bankmini.flightservice.model.dto.airport.AirportRequestDto;
import core.bankmini.flightservice.model.entity.Airport;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class AirportDataUtil {

    public static Airport getAirportPersisted(UUID id) {
        return Airport.builder()
                .id(id)
                .iataCode("XYZ")
                .icaoCode("TEST")
                .name("Test Airport")
                .city("Test City")
                .country("Test Country")
                .timezone("Test/Timezone")
                .latitude(BigDecimal.ONE)
                .longitude(BigDecimal.ONE)
                .minLayoverMinutes(0)
                .internationalAirport(true)
                .build();
    }

    public static Airport getAirportTransient() {
        return Airport.builder()
                .id(UUID.randomUUID())
                .iataCode("XYZ")
                .icaoCode("TEST")
                .name("Test Airport")
                .city("Test City")
                .country("Test Country")
                .timezone("Test/Timezone")
                .latitude(BigDecimal.ONE)
                .longitude(BigDecimal.ONE)
                .minLayoverMinutes(0)
                .internationalAirport(true)
                .build();
    }

    public static Airport getAirportPersistedWithQueryCountryCityIataCodeFist(
            UUID id,
            String country,
            String city,
            String iataCode
    ) {
        return Airport.builder()
                .id(id)
                .iataCode(iataCode)
                .icaoCode("FRST")
                .name("Test Airport first")
                .city(city)
                .country(country)
                .timezone("Test/Timezone")
                .latitude(BigDecimal.ONE)
                .longitude(BigDecimal.ONE)
                .minLayoverMinutes(0)
                .internationalAirport(true)
                .build();
    }

    public static Airport getAirportObject(
            String iataCode, String icaoCode,
            String city, String country
    ) {
        return Airport.builder()
                .id(UUID.randomUUID())
                .iataCode(iataCode)
                .icaoCode(icaoCode)
                .name("Airport name " + iataCode + " " + icaoCode)
                .city(city)
                .country(country)
                .timezone("Test/Test")
                .minLayoverMinutes(new Random().nextInt(100))
                .internationalAirport(new Random().nextBoolean())
                .build();
    }

    public static AirportRequestDto getAirportRequestDtoObject() {
        return AirportRequestDto.builder()
                .iataCode("XYZ")
                .icaoCode("TEST")
                .name("Test Airport")
                .city("Test City")
                .country("Test Country")
                .timezone("Test/Timezone")
                .minLayoverMinutes(0)
                .internationalAirport(true)
                .build();
    }

    public static Airport createAirport(
            String name,
            String country,
            String city,
            String iataCode,
            String icaoCode,
            String timezone
    ) {
        return Airport.builder()
                .id(UUID.randomUUID())
                .iataCode(iataCode)
                .icaoCode(icaoCode)
                .name("Airport name " + name + " " + icaoCode)
                .city(city)
                .country(country)
                .timezone(timezone)
                .minLayoverMinutes(new Random().nextInt(100))
                .internationalAirport(new Random().nextBoolean())
                .build();
    }
}
