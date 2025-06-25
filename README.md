# elm_springcloud

软件服务工程饿了么项目SpringCloud后端代码仓库

## Nacos 集群配置

```bash
docker compose up -d

docker cp mysql-schema.sql elm-springcloud-mysql:/tmp/mysql-schema.sql

docker exec -it elm-springcloud-mysql mysql -u root -p12345678

CREATE DATABASE nacos;

USE nacos;

source /tmp/mysql-schema.sql;

exit

docker compose -f nacos-compose.yaml up -d
```
