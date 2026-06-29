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

    private Integer seatsEconomy;

    private Integer seatsBusiness;

    private Integer seatsFirst;

    @Column(nullable = false)
    private Integer seatsTotal;

}
