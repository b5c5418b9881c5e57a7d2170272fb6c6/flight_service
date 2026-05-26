
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


















