FROM openjdk:17
EXPOSE 80
ADD target/springboot-image-demoApp-2.jar springboot-image-demoApp-2.jar
ENTRYPOINT ["java","-jar","/springboot-image-demoApp-2.jar"]
