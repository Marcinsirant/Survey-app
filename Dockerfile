FROM openjdk:16-ea-14-jdk-alpine3.12
ADD build/libs/Rest-0.0.1-SNAPSHOT.jar .
EXPOSE 8000
CMD java -jar Rest-0.0.1-SNAPSHOT.jar