FROM tomcat:8-alpine

WORKDIR /safira
COPY . /safira

RUN ./gradlew clean && \
    ./gradlew assemble


EXPOSE 8080