# stage 1: build
# start with a maven image that includes JDK 21 -> this image to build file jar
FROM maven:3.9.9-sapmachine-22 AS build

# copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# build source code with maven
# RUN mvn package -DskipTests
RUN mvn package -DskipTests

# stage 2: create image
# start with amazon correto JKD 21 -> this image to run file jar
FROM amazoncorretto:22

# set working folder to App and copy complied file from above step
# copy file jar tu image o? stage 1 -> ve image o? stage 2
WORKDIR /app
COPY --from=build /app/target/*jar app.jar

# command to run the application
# java -jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# run docker build in commandline
# build: docker build -t identity-servive:0.0.1 . | docker build -t name:version .
# run : docker run -d identity-service:0.0.1

# Create network:
# docker network create devteria-network

# Start MySQL in devteria-network
# docker run --network devteria-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.36-debian

# Run your application in devteria-network
# docker run --name identity-service --network devteria-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql_IP_Address:3306/identity_service identity-service:0.9.0
# docker run --name identity-service --network devteria-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://name_container:3306/identity_service identity-service:0.9.0