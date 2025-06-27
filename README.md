# mcp-host

Spring Boot service for chat processing. The project implements a REST API for interacting with a chatbot and handling
user messages.

## Build

Build the jar file using Gradle:

```
./gradlew bootJar
```

The resulting jar will appear at `build/libs/mcp-host-0.0.1.jar`.

## Run locally

```
java -jar build/libs/mcp-host-0.0.1.jar
```

## Run with Docker

1. Build the Docker image:

    ```
   docker compose up -d --build
    ```
    
    The application will be available at http://localhost:9000

## Environment variables and configuration

- Main settings are specified in the `application.yml` file.
- You can override parameters using Spring Boot environment variables (e.g., `SERVER_PORT`).

## Send requests

1. Via Kafka:

   - Send a message to the `chat-messages` topic.
   - The response will be sent to the `chat-responses` topic.

    Go to Kafka terminal
    
    ```
    docker exec -it kafka bash
    ```
    
    ```
    echo '{"userId":"user1","chatId":"chat1","message":"What is the weather in NJ?"}' | \
    kafka-console-producer --broker-list localhost:9092 --topic chat-messages
    ```

2. Via REST API:

    Send a POST request to the `/chat` endpoint with the following JSON body:
    
    ```json
    {
      "message": "What is the weather in NJ?"
    }
    ```

---
