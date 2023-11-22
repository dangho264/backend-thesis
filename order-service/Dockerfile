FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} order-service.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /order-service.jar ${0} ${@}"]