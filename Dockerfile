ARG MCP_SERVER_URL
ARG ANTHROPIC_API_KEY

FROM gradle:8.7-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/mcp-host-0.0.1.jar app.jar
EXPOSE 9000

ENTRYPOINT ["java", "-jar", "app.jar"]
