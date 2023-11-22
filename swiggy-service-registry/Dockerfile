FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./target/swiggy-service-registry-3.0.4.jar swiggy-service-registry.jar
ENTRYPOINT ["java","-jar","/swiggy-service-registry.jar"]
