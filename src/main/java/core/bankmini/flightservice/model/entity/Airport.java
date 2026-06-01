package core.bankmini.flightservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(
        name = "airports",
        indexes = {
                @Index(name = "idx_airport_iataCode", columnList = "iataCode"),
                @Index(name = "idx_airport_iataCode", columnList = "icaoCode"),
                @Index(name = "idx_airport_city", columnList = "city"),
                @Index(name = "idx_airport_country", columnList = "country")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Airport {

    //Уникальный ID аэропорта
    @Id
    private UUID id;

    //	Код IATA (ALA)
    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;

    //ICAO код
    @Column(unique = true, nullable = false, length = 4)
    private String icaoCode;

    private String name;

    private String city;

    private String country;

    private String timezone;

    //	Координата
    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    // 	Координата
    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    //Мин. время пересадки
    private Integer minLayoverMinutes;

    private Boolean internationalAirport;

}
