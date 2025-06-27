# elm_springcloud

软件服务工程饿了么项目SpringCloud后端代码仓库

## Nacos 集群配置

```bash
docker compose up -d

docker cp mysql-schema.sql elm-mysql-master:/tmp/mysql-schema.sql

docker exec -it elm-mysql-master mysql -u root -p12345678

CREATE DATABASE nacos;

USE nacos;

source /tmp/mysql-schema.sql;

exit

docker compose -f nacos-compose.yaml up -d
```

## Shardingsphere 集群配置

```bash
docker compose up -d

docker exec -it elm-mysql-master bash

mysql -u root -p12345678

CREATE USER 'repl'@'%' IDENTIFIED BY 'repl123';

GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';

FLUSH PRIVILEGES;

SHOW MASTER STATUS;

docker exec -it elm-mysql-slave bash

mysql -u root -p12345678

CHANGE MASTER TO
    MASTER_HOST='mysql-master',
    MASTER_USER='repl',
    MASTER_PASSWORD='repl123',
    MASTER_LOG_FILE='mysql-bin.000003',
    MASTER_LOG_POS=747;
    
START SLAVE;

SHOW SLAVE STATUS\G

docker compose up -d
```

## Seata 配置

```sql
CREATE TABLE `undo_log` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `branch_id` bigint(20) NOT NULL,
                            `xid` varchar(100) NOT NULL,
                            `context` varchar(128) NOT NULL,
                            `rollback_info` longblob NOT NULL,
                            `log_status` int(11) NOT NULL,
                            `log_created` datetime NOT NULL,
                            `log_modified` datetime NOT NULL,
                            `ext` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```