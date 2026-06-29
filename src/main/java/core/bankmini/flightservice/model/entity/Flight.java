package core.bankmini.flightservice.model.entity;

import core.bankmini.flightservice.model.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString(exclude = {
        "airline",
        "aircraft",
        "departureAirport",
        "arrivalAirport"
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flights")
@Entity
public class Flight {

    //Уникальный ID рейса
    @Id
    private UUID id;

    //Номер рейса (KC123)
    @Column(nullable = false, unique = true)
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
    @Column(nullable = false)
    private LocalDateTime departureAt;

    //Время прилета
    @Column(nullable = false)
    private LocalDateTime arrivalAt;

    //Время полета - (arrivalAt - departureAt)
    private Integer durationMinutes;

    //Всего мест
    @Column(nullable = false)
    private Integer seatsTotal;

    //Терминал
    @Column(nullable = false)
    private String terminal;

    //Gate
    @Column(nullable = false)
    private String gate;

    //Status
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
