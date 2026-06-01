package core.bankmini.flightservice.model.entity;

import core.bankmini.flightservice.model.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "flights")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flight {

    //Уникальный ID рейса
    @Id
    private UUID id;

    //Номер рейса (KC123)
    private String flightNumber;

    //Какая авиакомпания выполняет
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    //Какой самолет используется
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    //Аэропорт вылета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    //Аэропорт прилета
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    //Время вылета
    private LocalDateTime departureAt;

    //Время прилета
    private LocalDateTime arrivalAt;

    //Время полета - (arrivalAt - departureAt)
    private Integer durationMinutes;

    //Всего мест
    private Integer seatsTotal;

    //Терминал
    private String terminal;

    //Gate
    private String gate;

    //Status
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
