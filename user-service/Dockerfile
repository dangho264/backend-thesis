FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} user-service.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /user-service.jar ${0} ${@}"]