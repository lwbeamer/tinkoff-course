FROM openjdk:17-slim

ARG JAR_FILE=app/scrapper/target/scrapper-1.0-SNAPSHOT.jar

COPY $JAR_FILE /scrapper.jar

ENTRYPOINT exec java -jar /scrapper.jar