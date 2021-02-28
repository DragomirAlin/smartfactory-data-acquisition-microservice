FROM openjdk:latest
 
COPY target/*.jar /srv/

COPY docker-entrypoint.sh /

EXPOSE 8002

ENTRYPOINT ["java","-jar","/srv/data.acquisition-0.0.1-SNAPSHOT.jar"]

