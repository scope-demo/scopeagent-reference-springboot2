ARG JDK
FROM openjdk:${JDK}
WORKDIR /app
COPY . /app
ENTRYPOINT [ "sh", "./entrypoint.sh" ]