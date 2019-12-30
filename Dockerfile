FROM tomcat:8-alpine

WORKDIR /safira
COPY . /safira

RUN ./gradlew clean && \
    ./gradlew assemble && \
    mv build/libs/safira-0.1.war /usr/local/tomcat/webapps/safira.war

EXPOSE 8080