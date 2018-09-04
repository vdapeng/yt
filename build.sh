#!/usr/bin/env bash
# 新版本
NEW_TAG=0.0.4
# 工作空间  DOCKER-HUB 服务器，这里使用阿里云DOCKER-HUB服务器
WORKSPACE=registry-vpc.cn-beijing.aliyuncs.com
# 命名空间  对应阿里云 NAMESPACE
NAMESPACE=wdy
# 项目名
PROJECT=yt-api

# maven项目打包
mvn clean install

# 将打包好的项目容器化
docker build -t $WORKSPACE/$NAMESPACE/$PROJECT:$NEW_TAG .

# 推送至阿里云镜像仓库存储
docker push $WORKSPACE/$NAMESPACE/$PROJECT:$NEW_TAG

# 删除旧的镜像
docker rmi $WORKSPACE/$NAMESPACE/$PROJECT:$NEW_TAG
