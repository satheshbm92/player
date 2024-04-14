FROM openjdk:17
ADD target/player-0.0.1-SNAPSHOT.jar player.jar
ENTRYPOINT ["java", "-jar", "player.jar"]