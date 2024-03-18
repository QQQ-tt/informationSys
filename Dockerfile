FROM openjdk:17

MAINTAINER wyler "1102214883@qq.com"

ADD target/informationSys-0.0.1-SNAPSHOT.jar java.jar

ENV MYSQL_HOST=120.55.165.76
ENV MYSQL_PASSWORD=password

#设置镜像对外暴露端口
EXPOSE 9898

# 执行启动命令
ENTRYPOINT ["java","-jar","/java.jar",">/java.log &"]


