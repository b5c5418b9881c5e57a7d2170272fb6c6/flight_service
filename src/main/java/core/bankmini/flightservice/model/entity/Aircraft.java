package core.bankmini.flightservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Table(name = "aircrafts")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Aircraft {

    //Уникальный ID самолета
    @Id
    private UUID id;

    private String model;

    //Производитель
    private String manufactured;

    private Integer seatsEconomy;

    private Integer seatsBusiness;

    private Integer seatsFirst;

    private Integer seatsTotal;

}
