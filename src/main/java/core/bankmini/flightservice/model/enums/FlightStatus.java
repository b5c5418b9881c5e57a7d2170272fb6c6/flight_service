package core.bankmini.flightservice.model.enums;

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
    DELAYED,
    CANCELLED,
    COMPLETED
}
