FROM adoptopenjdk/openjdk11:latest

COPY ./target/purple-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch purple-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","purple-0.0.1-SNAPSHOT.jar"]
