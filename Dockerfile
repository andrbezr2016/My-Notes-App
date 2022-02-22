FROM gcr.io/distroless/java:11

COPY ./target/my-notes-app.jar /app/my-notes-app.jar

EXPOSE 8081

WORKDIR /app
CMD ["/app/my-notes-app.jar"]