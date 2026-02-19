FROM eclipse-temurin:17-jre
WORKDIR /app
COPY build/libs/*.jar app.jar

# FORZAR IPv4
ENV JAVA_TOOL_OPTIONS="-Djava.net.preferIPv4Stack=true -Djava.net.preferIPv6Addresses=false"

ENTRYPOINT ["java","-jar","app.jar"]
