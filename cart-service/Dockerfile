FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY ./target/cart-service-2.7.17.jar cart-service.jar
ENTRYPOINT ["java","-jar","/cart-service.jar"]
