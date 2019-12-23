# FROM tomcat:8-jdk8-openjdk

# RUN curl -s "https://get.sdkman.io" | bash

# COPY ./safira_api.war /usr/local/tomcat/webapps/safira_api.war

# CMD [ "catalina.sh","run" ]
# EXPOSE 8080
FROM tomcat:8-jdk8-openjdk

WORKDIR /safira
COPY . /safira

RUN apt-get update
RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apt-get -q -y install curl zip unzip
RUN curl -s https://get.sdkman.io | bash
RUN chmod a+x "/root/.sdkman/bin/sdkman-init.sh"
RUN source "/root/.sdkman/bin/sdkman-init.sh" && \
    sdk install grails 4.0.0.RC2 && \
    ./gradlew clean && \
    ./gradlew assemble && \
    mv build/libs/safira-0.1.war /usr/local/tomcat/webapps/safira.war

EXPOSE 8080