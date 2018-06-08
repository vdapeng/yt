FROM registry.cn-hangzhou.aliyuncs.com/wdytools/maven-jdk:lastet
ADD src/main/java /app/src/main/java
ADD src/main/resources /app/src/main/resources
ADD pom.xml /app/pom.xml
WORKDIR /app
RUN mvn install
WORKDIR /app/target
RUN cp system-api-1.0.0.jar app.jar
ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
