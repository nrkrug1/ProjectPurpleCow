## Project Purple Cow

### Description

Project Purple Cow is a SpringBoot best REST API backed by an H2 in-memory Database. A Swagger Use Interface is provided as a form of documentation and live demo. The API follows the requirements specified in the project's writeup including standard CRUD operations, a batch create and the ability to delete all items.

### Required tools

The project is setup to use **Maven** as a build tool. In order to build docker images using the built-in Maven commands, the **Docker** runtime must also be installed.


### Usage

Commands are run from the project root directory.

**Build and Test only:** mvn clean compile

**Run Locally:** mvn spring-boot:run  

*or*

from the < Project Root >/target directory run:  

java -jar purple-0.0.1-SNAPSHOT.jar

**Build Docker Image:** mvn spring-boot:build-image

*or*

from the < Project Root > directory run:

docker build -t purple:0.0.1-SNAPSHOT .

**Run Docker Image:** docker run -p 3000:3000 -t purple:0.0.1-SNAPSHOT

**Swagger UI:** Once app is running either locally or through Docker using a browser, navigate to [Swagger UI ](http://localhost:3000//swagger-ui.html#/)


### Additional Details

- Server port can be changed by adding command line arguments to the run commands. Examples chage default port from 3000 to 8080.
  - **Locally:** java -jar purple-0.0.1-SNAPSHOT.jar --server.port=8080
  - **Maven:** mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080
  - **Docker:** docker run -p 8080:3000 -t purple:0.0.1-SNAPSHOT
