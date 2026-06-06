package core.bankmini.flightservice.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/// забронирован
///
/// посадка на рейс
///
/// ожидание посадки на рейс
///
/// отменен
///
/// завершен

public enum FlightStatus {
    CREATED,
    SCHEDULED,
    BOARDING,
    DELAYED, // Задержан
    CANCELLED,
    COMPLETED;

    private final static Set<String> NAMES =
            Arrays.stream(values())
                    .map(Enum::name)
                    .collect(Collectors.toUnmodifiableSet());

    public static boolean contains(String name) {
        return NAMES.contains(name);
    }
}
