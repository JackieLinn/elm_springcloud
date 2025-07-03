# elm_springcloud

软件服务工程饿了么项目SpringCloud后端代码仓库

## 项目概述

这是一个基于Spring Cloud的微服务架构外卖平台后端系统，包含以下核心功能：

### 核心服务
- **auth-service**: 用户认证与授权服务
- **business-service**: 商家管理服务
- **food-service**: 菜品管理服务
- **cart-service**: 购物车服务
- **orders-service**: 订单管理服务
- **address-service**: 地址管理服务
- **gateway**: API网关服务

### 商家入驻功能

#### 功能描述
商家入驻功能允许普通用户申请成为商家，经过管理员审核后自动升级为商家角色。

#### 核心特性
1. **无需登录申请**: 用户可以通过用户名直接申请入驻，无需先登录获取userId
2. **自动角色升级**: 审核通过后自动将用户角色从普通用户升级为商家
3. **数据一致性**: 确保用户-商家关联关系的正确建立

#### API接口

##### 提交入驻申请
```http
POST /api/business/merchant/apply
Content-Type: application/json

{
    "userName": "用户名",
    "businessName": "商家名称",
    "businessAddress": "商家地址",
    "contactPhone": "联系电话",
    "businessDesc": "商家简介"
}
```

##### 查询申请状态
```http
GET /api/business/merchant/status?userId={userId}
```

##### 管理员审核申请
```http
POST /api/business/admin/apply/review?applyId={applyId}&result={result}&reviewReason={reviewReason}
```

#### 技术实现
- 使用Feign客户端实现微服务间通信
- 通过userName查询userId解决未登录用户身份识别问题
- 数据库userName字段添加唯一约束确保数据一致性
- 审核通过后自动创建商家记录并建立用户-商家关联关系

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