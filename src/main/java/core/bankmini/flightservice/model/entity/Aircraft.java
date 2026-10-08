package core.bankmini.flightservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aircrafts")
@Entity
public class Aircraft {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String model;

    //Производитель
    private String manufactured;

    //фича по бронированию билета - работа с Booking Service
    //todo: Выделить отдельную сущность под места посадки т.к. при бронировании билета будет браться место на бронь и покупку в тикет

    private Integer seatsEconomy;

    private Integer seatsBusiness;

    private Integer seatsFirst;

    @Column(nullable = false)
    private Integer seatsTotal;

}
