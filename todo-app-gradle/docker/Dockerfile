FROM openjdk:23-ea-17-jdk-bullseye
COPY ./build/libs/todo.jar /app/todo.jar
CMD ["java", "-jar", "/app/todo.jar"]
EXPOSE 8080
HEALTHCHECK --timeout=40s --interval=1m --retries=3 CMD wget http://localhost:8080/rest/health -O /dev/null
