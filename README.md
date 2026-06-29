Controller Open API:
http://localhost:44441/api/swagger-ui/index.html

Зона ответственности
Хранение и управление рейсами.

Сервис отвечает только за:

1. рейсы
2. аэропорты
3. авиакомпании
4. самолеты
5. доступные места

___

## Функции сервисов:

#### Flights:

``` 
* createFlight()
* updateFlight()
* cancelFlight()
* changeFlightStatus()
* searchFlights()
* getFlightById()
```

#### Airports:

```
* createAirport()
* updateAirport()
* searchAirports()
* getAirportByCode()
```

#### Airlines:

```
* createAirline()
* updateAirline()
```

___

`YYYY-MM-DDThh:mm:ss.sssZ`

Component Breakdown

* YYYY:Four-digit year (e.g., \(2026\))
* MM: Two-digit month (01–12)
* DD: Two-digit day of the month (01–31)
* T: Separator between date and time
* hh: Two-digit hour in 24-hour format (00–23)
* mm: Two-digit minute (00–59)
* ss: Two-digit second (00–59)
* sss: Milliseconds (optional)
* Z: UTC time zone designator (meaning \(+00:00\) offset)

___

```
                          Frontend
                              │
                           HTTPS
                              │
                              ▼
                        API Gateway
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼

 Search Service      Booking Service        User Service
        │                     │
        │                     ├──── REST ───► User Service
        │                     ├──── REST ───► Flight Service
        │                     ├──── REST ───► Pricing Service
        │                     └──── REST ───► Payment Service
        │
        ├──── REST ───► Route Engine
        │
        └──── REST ───► Pricing Service

──────────────────────────────────────────────────────────

                    EVENT FLOW

                 Flight Service
                        │
                        │ Publish
                        ▼

                FlightCreated
                FlightUpdated
                FlightCancelled

                        │
                        ▼

                      Kafka
                        │
                        ▼

                  Route Engine
                     Consumer

                        │
                        ▼

                    route_db

──────────────────────────────────────────────────────────

                       CACHE

                    Search Service
                           │
                           ▼

                         Redis

──────────────────────────────────────────────────────────

                     DATABASES

 flight_db
 booking_db
 user_db
 pricing_db
 payment_db
 route_db

```

___

## Модели:

### Flight

| Поле               | Тип          | Описание                     |
|--------------------|--------------|------------------------------|
| id                 | UUID         | Уникальный ID рейса          |
| flightNumber       | string       | Номер рейса (`KC123`)        |
| airlineId          | UUID         | Какая авиакомпания выполняет |
| aircraftId         | UUID         | Какой самолет используется   |
| departureAirportId | UUID         | Аэропорт вылета              |
| arrivalAirportId   | UUID         | Аэропорт прилета             |
| departureAt        | datetime UTC | Время вылета                 |
| arrivalAt          | datetime UTC | Время прилета                |
| durationMinutes    | int          | Время полета                 |
| seatsTotal         | int          | Всего мест                   |
| seatsAvailable     | int          | Свободных мест               |
| terminal           | string       | Терминал                     |
| gate               | string       | Gate посадки                 |
| status             | enum         | Статус рейса                 |
| createdAt          | datetime     | Дата создания                |
| updatedAt          | datetime     | Дата обновления              |

### Flight.status:

```
SCHEDULED
BOARDING
DELAYED
CANCELLED
COMPLETED
```

### Airport:

| Поле              | Тип     | Описание             |
|-------------------|---------|----------------------|
| id                | UUID    | ID аэропорта         |
| iataCode          | string  | Код IATA (`ALA`)     |
| icaoCode          | string  | ICAO код             |
| name              | string  | Название аэропорта   |
| city              | string  | Город                |
| country           | string  | Страна               |
| timezone          | string  | Таймзона             |
| latitude          | decimal | Координата           |
| longitude         | decimal | Координата           |
| minLayoverMinutes | int     | Мин. время пересадки |
| isInternational   | bool    | Международный ли     |

### Airline:

| Поле     | Тип    | Описание        |
|----------|--------|-----------------|
| id       | UUID   | ID авиакомпании |
| name     | string | Название        |
| iataCode | string | IATA код        |
| icaoCode | string | ICAO код        |
| country  | string | Страна          |

### Aircraft:

| Поле          | Тип    | Описание       |
|---------------|--------|----------------|
| id            | UUID   | ID самолета    |
| model         | string | Модель         |
| manufacturer  | string | Производитель  |
| seatsEconomy  | int    | Места economy  |
| seatsBusiness | int    | Места business |
| seatsFirst    | int    | Места first    |

___

# Доп информация

* Код IATA (International Air Transport Association)
  Назначение: Используется пассажирами, туристическими агентствами и системами бронирования (например, при покупке
  билетов).
  Формат: Состоит из 3-х латинских букв (например, ALA для Алматы, ALA для Астаны).
  Для чего нужен: Печатается на вашем багажном талоне и посадочном талоне для удобной маршрутизации багажа.

* Код ICAO (International Civil Aviation Organization)
  Назначение: Используется пилотами, авиадиспетчерами и службами аэронавигации для составления планов полетов и
  управления воздушным движением.Формат: Состоит из 4-х латинских букв. В большинстве регионов мира эти коды имеют
  строгую географическую привязку (первая буква — регион, вторая — страна, третья и четвертая — конкретный аэропорт).
  Чаще всего под “ICAO-кодом” имеют в виду 4-буквенный код аэропорта.
  Пример: UAAA, где U обозначает Евразию/Россию и прилегающие страны, A — Казахстан, AA — Международный аэропорт Алматы.

Формат `XXXX`
Где:

* 1-я буква — регион мира
* 2-я буква — страна или подрегион
* 3–4 буквы — конкретный аэропорт

Например:

UAAA — Алматы
KJFK — JFK в Нью-Йорке
EGLL — Heathrow в Лондоне

| Префикс | Регион              |
|---------|---------------------|
| E       | Северная Европа     |
| L       | Южная Европа        |
| K       | USA                 |
| C       | Канада              |
| U       | страны бывшего СССР |
| V       | Юго-Восточная Азия  |
| Z       | Китай               |

___
















