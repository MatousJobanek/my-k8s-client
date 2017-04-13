
FROM centos:7

ARG VERSION=1.0-SNAPSHOT

ENV JAVA_HOME /etc/alternatives/jre
ENV CHE_STARTER_HOME /opt/che-starter

## Default ENV variable values
ENV OSO_ADDRESS tsrv.devshift.net:8443
ENV OSO_DOMAIN_NAME tsrv.devshift.net
ENV KUBERNETES_CERTS_CA_FILE /opt/che-starter/tsrv.devshift.net.cer

RUN yum update -y && \
    yum install -y \
       java-1.8.0-openjdk java-1.8.0-openjdk-devel git && \
    yum clean all


ARG MAVEN_VERSION=3.5.0
ARG USER_HOME_DIR="/root"
ARG SHA=beb91419245395bd69a4a6edad5ca3ec1a8b64e41457672dc687c173a495f034
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-$MAVEN_VERSION-bin.tar.gz \
  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha256sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn


RUN git clone https://github.com/MatousJobanek/my-k8s-client.git
RUN ls my-k8s-client
ENV KUBERNETES_CERTS_CA_FILE my-k8s-client/ca.crt

CMD ["mvn","clean", "package", "-f", "my-k8s-client/pom.xml"]
