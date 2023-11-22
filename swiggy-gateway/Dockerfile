FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./target/swiggy-gateway-3.0.4.jar apigateway.jar
ENTRYPOINT ["java","-jar","/apigateway.jar"]
