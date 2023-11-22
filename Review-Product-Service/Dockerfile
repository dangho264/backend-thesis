FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} review-service.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /review-service.jar ${0} ${@}"]