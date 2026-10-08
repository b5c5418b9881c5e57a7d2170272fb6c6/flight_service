package core.bankmini.flightservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
public class Airport {

    //Уникальный ID аэропорта
    @Id
    private UUID id;

    //	Код IATA (ALA)
    @Column(unique = true, nullable = false, length = 3)
    private String iataCode;

    //ICAO код
    @Column(unique = true, length = 4)
    private String icaoCode;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Pattern(
            regexp = "^[a-zA-z]+/[A-Za-z]+?$",
            message = "Неверный формат временной зоны. Используйте формат 'Регион/Город'"
    )
    @Column(nullable = false)
    private String timezone;

    //	Координата
    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    // 	Координата
    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    //todo: добавить Minimum Connecting Time (MCT) — достаточно ли времени на пересадку (например, >= 45 =< 90 мин).
    //todo: добавить Maximum Layover — отсев слишком долгих ожиданий (например, =< 24 часов).

    //Мин. время пересадки
    @Column(nullable = false)
    private Integer minLayoverMinutes;

    @Column(nullable = false)
    private Boolean internationalAirport;

}
