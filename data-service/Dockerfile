FROM java:8
VOLUME /tmp
WORKDIR ocr-annotation-dashboard-server/
ADD target/data-service-iStoryBook-*-SNAPSHOT.jar /app.jar
EXPOSE 8080
RUN bash -c "touch /app.jar"
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","/app.jar"]