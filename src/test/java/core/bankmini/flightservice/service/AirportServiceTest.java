package core.bankmini.flightservice.service;

import core.bankmini.flightservice.exceptions.AirportAlreadyExistsException;
import core.bankmini.flightservice.model.dto.airport.AirportRequestDto;
import core.bankmini.flightservice.model.entity.Airport;
import core.bankmini.flightservice.repository.AirportRepository;
import core.bankmini.flightservice.util.AirportDataUtil;
import core.bankmini.flightservice.utility.AirportMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;

    @Mock
    private AirportMapper airportMapper;

    @InjectMocks
    private AirportService airportServiceUnderTest;

    @Test
    @DisplayName("Test throw Airport Already Exists Exception")
    void givenAirportToSave_whenSaveAirport_thenThrowAirportAlreadyExistsException() {
        //given
        AirportRequestDto dto = AirportDataUtil.getAirportRequestDtoObject();
        Airport airport = AirportDataUtil.getAirportObject(dto.icaoCode(), dto.icaoCode(), dto.city(), dto.country());

        //when
        when(airportRepository.findByName(dto.name())).thenReturn(airport);

        //then
        assertThrows(
                AirportAlreadyExistsException.class,
                () -> airportServiceUnderTest.createAirport(dto)
        );
        verifyNoInteractions(airportMapper);
        verify(airportRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test create airport func")
    void givenAirportToSave_whenSaveAirport_thenRepositoryIsCalled() {
        //given
        AirportRequestDto dto = AirportDataUtil.getAirportRequestDtoObject();
        Airport airport = AirportDataUtil.getAirportObject(dto.icaoCode(), dto.icaoCode(), dto.city(), dto.country());

        //when
        when(airportRepository.findByName(dto.name())).thenReturn(null);
        when(airportMapper.mapDtoToEntity(eq(dto), any(UUID.class))).thenReturn(airport);
        when(airportRepository.save(any(Airport.class))).thenReturn(airport);

        UUID result = airportServiceUnderTest.createAirport(dto);

        //then
        assertEquals(airport.getId(), result);
        verify(airportMapper).mapDtoToEntity(eq(dto), any(UUID.class));
        verify(airportRepository).save(airport);
    }


}