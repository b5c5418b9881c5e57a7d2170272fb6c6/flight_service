package core.bankmini.flightservice.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "airlines")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Airline {

    //Уникальный ID авиакомпании - авиалиния
    @Id
    private UUID id;

    private String name;

    //	Код IATA (ALA)
    private String iataCode;

    //ICAO код
    private String icaoCode;

    private String country;

}
