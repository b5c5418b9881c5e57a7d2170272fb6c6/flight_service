package core.bankmini.flightservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airlines")
@Entity public class Airline {

    //Уникальный ID авиакомпании - авиалиния
    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    //	Код IATA (ALA)
    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;

    //ICAO код
    @Column(unique = true, length = 4)
    private String icaoCode;

    @Column(nullable = false)
    private String country;

}
