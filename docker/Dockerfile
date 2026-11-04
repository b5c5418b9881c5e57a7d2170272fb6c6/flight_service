# Сборка
FROM gradle:9.4.1-jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle build -x test

# Финальный образ
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

#Приложение внутри контейнера слушает:
EXPOSE 54322

ENTRYPOINT ["java", "-jar", "app.jar"]