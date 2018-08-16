FROM ibmjava:jre
ADD ./target /app/target
WORKDIR /app/target
RUN cp system-api-0.0.2-A.jar app.jar
ENV LANG C.UTF-8
ENV TZ=Asia/Shanghai
ENV CONFIG_PROFILE=prod
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 80
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
