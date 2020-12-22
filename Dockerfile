FROM openjdk:14.0.2-oraclelinux8

#ARG JAR_FILE=target/*.jar
COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
