# 新版本
NEW_TAG=0.0.1
# 老版本
OLD_TAG=0.0.0
# 工作空间  DOCKER-HUB 服务器，这里使用阿里云DOCKER-HUB服务器
WORKSPACE=registry.cn-beijing.aliyuncs.com
# 命名空间  对应阿里云 NAMESPACE
NAMESPACE=hq-zhgd
# 项目名
PROJECT=system-api
# 映射至主机接口
 FROM_PORT=28005
# 容器内部暴露接口
 TO_PORT=28005

mvn clean
mvn test
mvn install

# /usr/local/bin/mvn-entrypoint.sh mvn verify clean --fail-never

# /usr/local/bin/mvn-entrypoint.sh mvn verify

# 将打包好的项目容器化
docker build -t $WORKSPACE/$NAMESPACE/$PROJECT:$NEW_TAG .

# 停止运行中旧的版本容器
if docker ps -a | grep -i $PROJECT.$OLD_TAG; then
  docker stop $PROJECT.$OLD_TAG || true
  docker rm $PROJECT.$OLD_TAG || true
fi

# 停止运行中的重复版本
if docker ps -a | grep -i $PROJECT.$NEW_TAG; then
  docker stop $PROJECT.$NEW_TAG || true
  docker rm $PROJECT.$NEW_TAG   || true

fi

# 开启新的版本
if [ ! -n "$FROM_PORT" ] || [ ! -n "$TO_PORT"]; then
  docker run -d -it --name $PROJECT.$NEW_TAG $WORKSPACE/$NAMESPACE/$PROJECT:$NEW_TAG
else
  docker run -d -it --name $PROJECT.$NEW_TAG -p $FROM_PORT:$TO_PORT $WORKSPACE/$NAMESPACE/$PROJECT:$NEW_TAG
fi

# 删除旧的镜像
docker rmi $WORKSPACE/$NAMESPACE/$PROJECT:$OLD_TAG || true