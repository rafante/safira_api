FROM openjdk:8-jdk

WORKDIR /
COPY ./safira-base.bash /safira-base.bash

CMD [ "/bin/bash", "safira-base.bash" ]
# ENTRYPOINT [ "safira-base.bash" ]
# RUN curl -O https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-245.0.0-linux-x86_64.tar.gz && \
#     tar zxvf google-cloud-sdk-245.0.0-linux-x86_64.tar.gz google-cloud-sdk && \
#     ./google-cloud-sdk/install.sh && \
#     export PATH="./google-cloud-sdk/bin:$PATH" && \
# gcloud auth loginrafante2@gmail.com && \
# gcloud config set project safiraapi-263018 && \
# ./gradlew appengineDeploy