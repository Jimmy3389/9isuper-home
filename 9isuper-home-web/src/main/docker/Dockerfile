#基于那个镜像
FROM frolvlad/alpine-oraclejdk8:slim

#将本地文件挂在当前容器
VOLUME /tmp

#复制文件到容器
ADD 9isuper-home-web-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'

#声明需要暴露的端口
EXPOSE 8890

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]