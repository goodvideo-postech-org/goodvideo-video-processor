FROM maven:3.6.3-openjdk-17 as build

EXPOSE 8080

COPY src /app/src

COPY pom.xml /app

WORKDIR /app

RUN mvn clean package -DskipTests=true

FROM linuxserver/ffmpeg

RUN apt-get update && \
    apt-get install -y openjdk-17-jre-headless && \
    apt-get clean;

COPY --from=build app/target/processor-0.0.1-SNAPSHOT.jar goodvideo-processor.jar

ENTRYPOINT ["java","-jar","/goodvideo-processor.jar"]
