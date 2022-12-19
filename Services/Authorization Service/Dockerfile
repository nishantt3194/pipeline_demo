FROM amazoncorretto:17.0.3-alpine3.15

COPY /build/libs/authorization-service-1.0.0.jar app.jar

ENV SERVER_PORT=""

EXPOSE $SERVER_PORT

ENTRYPOINT ["java", "-jar", "/app.jar"]

HEALTHCHECK --interval=15s --timeout=3s --retries=3 CMD wget --spider http://localhost:$SERVER_PORT/actuator/health || exit 1
