FROM tupperware.mynt.xyz/mtc/devtools/springboot-jdk17:3.0.5 as build

COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean compile package -Dmaven.test.skip=true

FROM openjdk:17
COPY --from=build /usr/src/app/target/Bootcamp2-0.0.1-SNAPSHOT.jar /usr/app/app-0.0.1-SNAPSHOT.jar
CMD java -agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n -jar /usr/app/app-0.0.1-SNAPSHOT.jar