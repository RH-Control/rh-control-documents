FROM openjdk:11
EXPOSE 9090
ENV ACESS_KEY=acessKey
ENV SECRET_KEY=secretKey
ADD target/rh-atestado.jar rh-atestado.jar
ENTRYPOINT ["java","-jar","rh-atestado.jar"]