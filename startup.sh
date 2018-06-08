docker stop system-api || true 
docker rm system-api || true   
docker rmi registry.cn-beijing.aliyuncs.com/hq-zhgd/system-api:1.0.0 || true  

docker pull registry.cn-beijing.aliyuncs.com/hq-zhgd/system-api:1.0.0
docker run -d -it --name system-api -e 'CONFIG_HOST=39.106.104.232' -e 'CONFIG_PORT=28088' -e 'CONFIG_LABEL=zhgd' -e 'CONFIG_NAME=system-api' -e 'CONFIG_PROFILE=prod'  -p 28005:28005 registry.cn-beijing.aliyuncs.com/hq-zhgd/system-api:1.0.0
