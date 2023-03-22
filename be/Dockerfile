FROM adoptopenjdk/openjdk11
CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE=build/libs/getABeer-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} beer.jar
ENTRYPOINT ["java", "-jar", "/beer.jar", "--spring.profiles.active=dev"]