FROM adoptopenjdk/openjdk11:latest

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /opt/app/logs
RUN mkdir -p /opt/app/arquivo

WORKDIR /opt/app

COPY target/*.jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 8080




