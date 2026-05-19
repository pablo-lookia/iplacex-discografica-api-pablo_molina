FROM eclipse-temurin:17-jdk AS build
COPY . /app
WORKDIR /app
RUN chmod +x gradlew
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:17-jdk
EXPOSE 8080
RUN mkdir /app
COPY --from=build /app/build/libs/discografia-1.war /app/discografia-api.war
ENTRYPOINT ["java", "-jar", "/app/discografia-api.war"]
