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

## API接口文档

### 1. 认证服务 (auth-service)

#### 1.1 用户认证接口
- **请求邮件验证码**
  - 路径: `GET /auth/ask-code`
  - 参数: `email` (邮箱), `type` (类型: register|reset)
  - 功能: 发送邮件验证码用于注册或重置密码

- **用户注册**
  - 路径: `POST /auth/register`
  - 功能: 用户邮箱注册

- **密码重置确认**
  - 路径: `POST /auth/reset-confirm`
  - 功能: 确认密码重置验证码

- **密码重置**
  - 路径: `POST /auth/reset-password`
  - 功能: 执行密码重置操作

#### 1.2 用户管理接口
- **根据用户ID获取用户信息**
  - 路径: `GET /api/account/get-account-by-userId`
  - 参数: `userId` (用户ID)

- **支付操作**
  - 路径: `GET /api/account/pay`
  - 参数: `userId` (用户ID), `price` (金额)
  - 功能: 远程调用，扣除用户余额

- **退款操作**
  - 路径: `GET /api/account/refund`
  - 参数: `userId` (用户ID), `price` (金额)
  - 功能: 远程调用，退还用户余额

- **更新用户角色**
  - 路径: `POST /api/account/role/update`
  - 参数: `userId` (用户ID), `roleId` (角色ID)

- **根据用户名查询用户ID**
  - 路径: `GET /api/account/remote/get-user-id`
  - 参数: `userName` (用户名)
  - 功能: 远程调用，供其他服务使用

#### 1.3 管理员接口
- **分页查询所有用户**
  - 路径: `GET /api/account/admin/list`
  - 参数: `pageNum` (页码), `pageSize` (页大小), `roleId` (可选，角色ID)

- **新增用户**
  - 路径: `POST /api/account/admin/add`
  - 功能: 管理员新增用户

- **删除用户**
  - 路径: `POST /api/account/admin/delete`
  - 参数: `userId` (用户ID)
  - 功能: 逻辑删除用户

- **修改用户信息**
  - 路径: `POST /api/account/admin/update`
  - 功能: 管理员修改用户信息

### 2. 商家服务 (business-service)

#### 2.1 商家查询接口
- **获取所有点餐分类**
  - 路径: `GET /api/business/get-all-categories`
  - 功能: 返回所有可用的点餐分类

- **获取推荐商家**
  - 路径: `GET /api/business/get-recommend-business`
  - 功能: 获取推荐商家列表

- **根据分类获取商家**
  - 路径: `GET /api/business/get-business-by-orderTypeId`
  - 参数: `orderTypeId` (分类ID)

- **根据商家ID获取商家信息**
  - 路径: `GET /api/business/get-business-by-businessId`
  - 参数: `businessId` (商家ID)

- **获取商家配送费**
  - 路径: `GET /api/business/get-delivery-price`
  - 参数: `businessId` (商家ID)

- **搜索商家**
  - 路径: `GET /api/business/search`
  - 参数: `keyword` (搜索关键词)

#### 2.2 商家管理接口
- **根据用户ID获取商家信息**
  - 路径: `GET /api/business/list-by-user`
  - 参数: `userId` (用户ID)
  - 功能: 商家端查看自己的店铺列表

- **修改商家信息**
  - 路径: `POST /api/business/update-info-by-user`
  - 参数: `userId` (用户ID)
  - 功能: 商家修改自己的店铺信息

- **校验用户是否拥有商家**
  - 路径: `GET /api/business/user-business/check`
  - 参数: `userId` (用户ID), `businessId` (商家ID)
  - 功能: 远程调用，校验用户-商家归属

#### 2.3 远程调用接口
- **根据商家ID获取商家信息(远程)**
  - 路径: `GET /api/business/get-business-by-businessId-remote`
  - 参数: `businessId` (商家ID)

- **根据商家ID列表获取商家信息**
  - 路径: `GET /api/business/get-business-info`
  - 参数: `businessIds` (商家ID集合)

#### 2.4 管理员接口
- **禁用商家**
  - 路径: `POST /api/business/admin/disable`
  - 参数: `businessId` (商家ID)

- **启用商家**
  - 路径: `POST /api/business/admin/enable`
  - 参数: `businessId` (商家ID)

- **分页查询所有商家**
  - 路径: `GET /api/business/admin/list`
  - 参数: `pageNum` (页码), `pageSize` (页大小), `status` (可选，状态), `keyword` (可选，关键词)

- **查询商家详情**
  - 路径: `GET /api/business/admin/detail`
  - 参数: `businessId` (商家ID)

### 3. 商家入驻申请接口

#### 3.1 申请接口
- **提交入驻申请(使用用户名)**
  - 路径: `POST /api/business/merchant/apply`
  - 功能: 通过用户名提交商家入驻申请，无需用户登录

- **提交入驻申请(使用用户ID)**
  - 路径: `POST /api/business/merchant/apply-by-id`
  - 功能: 通过用户ID提交商家入驻申请，需要用户已登录

- **查询申请状态**
  - 路径: `GET /api/business/merchant/status`
  - 参数: `userId` (用户ID)

#### 3.2 管理员审核接口
- **分页查询申请列表**
  - 路径: `GET /api/business/admin/apply/list`
  - 参数: `pageNum` (页码), `pageSize` (页大小), `status` (可选，状态)

- **审核申请**
  - 路径: `POST /api/business/admin/apply/review`
  - 参数: `applyId` (申请ID), `result` (审核结果), `reviewReason` (审核原因)

### 4. 菜品服务 (food-service)

#### 4.1 菜品查询接口
- **根据商家ID获取上架菜品**
  - 路径: `GET /api/food/list-food-by-BusinessId`
  - 参数: `businessId` (商家ID)
  - 功能: 客户端用，只返回上架菜品

- **根据商家ID获取所有菜品**
  - 路径: `GET /api/food/list-all-food-by-BusinessId`
  - 参数: `businessId` (商家ID)
  - 功能: 商家/管理端用，返回所有菜品

#### 4.2 远程调用接口
- **根据菜品ID获取菜品**
  - 路径: `GET /api/food/list-food-by-FoodId`
  - 参数: `foodId` (菜品ID)

- **根据菜品ID列表获取菜品**
  - 路径: `GET /api/food/get-food-info`
  - 参数: `foodIds` (菜品ID集合)

#### 4.3 商家菜品管理接口
- **新增菜品**
  - 路径: `POST /api/food/add`
  - 功能: 商家新增菜品

- **修改菜品**
  - 路径: `POST /api/food/update`
  - 功能: 商家修改菜品

- **删除菜品**
  - 路径: `POST /api/food/delete`
  - 参数: `foodId` (菜品ID)
  - 功能: 逻辑删除菜品

- **上下架菜品**
  - 路径: `POST /api/food/status`
  - 参数: `foodId` (菜品ID), `foodStatus` (状态)
  - 功能: 商家上下架菜品

#### 4.4 管理员接口
- **下架菜品**
  - 路径: `POST /api/food/admin/disable`
  - 参数: `foodId` (菜品ID)

- **恢复菜品上架**
  - 路径: `POST /api/food/admin/enable`
  - 参数: `foodId` (菜品ID)

### 5. 购物车服务 (cart-service)

#### 5.1 购物车操作接口
- **获取购物车信息**
  - 路径: `POST /api/cart/list-cart`
  - 功能: 获取用户购物车中的商品列表

- **保存购物车信息**
  - 路径: `POST /api/cart/save-cart`
  - 功能: 添加商品到购物车

- **更新购物车信息**
  - 路径: `POST /api/cart/update-cart`
  - 功能: 更新购物车中商品数量

- **移除购物车信息**
  - 路径: `POST /api/cart/remove-cart`
  - 功能: 从购物车中移除商品

- **获取购物车数量**
  - 路径: `GET /api/cart/get-cart-quantity`
  - 参数: `userId` (用户ID)

#### 5.2 远程调用接口
- **获取购物车映射**
  - 路径: `GET /api/cart/get-cart-map`
  - 参数: `userId` (用户ID), `businessId` (商家ID)

- **删除购物车记录**
  - 路径: `POST /api/cart/delete-by-cid`
  - 参数: `cartId` (购物车ID)

### 6. 订单服务 (orders-service)

#### 6.1 订单操作接口
- **创建订单**
  - 路径: `POST /api/orders/create-orders`
  - 功能: 创建新订单

- **支付操作**
  - 路径: `POST /api/orders/payment`
  - 功能: 订单支付

- **更新送餐地址**
  - 路径: `POST /api/orders/update-address`
  - 功能: 更新订单的送餐地址

#### 6.2 订单查询接口
- **根据订单编号获取商家信息**
  - 路径: `GET /api/orders/get-business-info`
  - 参数: `orderId` (订单ID)

- **根据订单编号获取食物信息**
  - 路径: `GET /api/orders/get-food-info`
  - 参数: `orderId` (订单ID)

- **获取所有订单信息**
  - 路径: `GET /api/orders/get-all-order-info`
  - 参数: `userId` (用户ID)

#### 6.3 商家端接口
- **根据商家ID查询订单列表**
  - 路径: `GET /api/orders/list-by-business`
  - 参数: `userId` (用户ID), `businessId` (商家ID)
  - 功能: 商家端查看该商家所有订单

- **订单详情**
  - 路径: `GET /api/orders/detail`
  - 参数: `userId` (用户ID), `orderId` (订单ID)

- **接单**
  - 路径: `POST /api/orders/accept`
  - 参数: `userId` (用户ID), `orderId` (订单ID)

- **完成订单**
  - 路径: `POST /api/orders/finish`
  - 参数: `userId` (用户ID), `orderId` (订单ID)

- **拒单**
  - 路径: `POST /api/orders/reject`
  - 参数: `userId` (用户ID), `orderId` (订单ID)
  - 功能: 商家拒单，已支付需退款

#### 6.4 管理员接口
- **按商家ID分页查订单**
  - 路径: `GET /api/orders/admin/list-by-business`
  - 参数: `businessId` (商家ID), `pageNum` (页码), `pageSize` (页大小), `orderState` (可选，订单状态)

### 7. 地址服务 (address-service)

#### 7.1 地址管理接口
- **获取用户所有地址信息**
  - 路径: `GET /api/deliveryAddress/get-all-address`
  - 参数: `userId` (用户ID)

- **保存地址信息**
  - 路径: `POST /api/deliveryAddress/save-address`
  - 功能: 新增配送地址

- **更新地址信息**
  - 路径: `POST /api/deliveryAddress/update-address`
  - 功能: 修改配送地址

- **移除地址信息**
  - 路径: `POST /api/deliveryAddress/remove-address`
  - 参数: `daId` (地址ID)

## 网关路由配置

所有接口通过网关统一访问，路由规则如下：

- **认证服务**: `/auth/**`, `/api/account/**` → `auth-service`
- **商家服务**: `/api/business/**` → `business-service`
- **菜品服务**: `/api/food/**` → `food-service`
- **地址服务**: `/api/deliveryAddress/**` → `address-service`
- **购物车服务**: `/api/cart/**` → `cart-service`
- **订单服务**: `/api/orders/**` → `orders-service`

## 技术特性

### 1. 微服务架构
- 基于Spring Cloud Gateway的API网关
- 服务注册与发现使用Nacos
- 服务间通信使用OpenFeign
- 分布式事务使用Seata

### 2. 高可用保障
- 熔断器(Circuit Breaker)
- 限流器(Rate Limiter)
- 舱壁隔离(Bulkhead)
- 统一降级处理

### 3. 数据存储
- MySQL主从复制
- Redis缓存
- Elasticsearch搜索
- ShardingSphere分库分表

### 4. 安全认证
- JWT令牌认证
- 角色权限控制
- 密码加密存储

## 商家入驻功能

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

## 项目反思与改进建议

### 已完成的功能
1. ✅ 完整的微服务架构设计
2. ✅ 用户认证与授权系统
3. ✅ 商家入驻申请流程
4. ✅ 菜品管理功能
5. ✅ 购物车操作
6. ✅ 订单管理流程
7. ✅ 地址管理功能
8. ✅ API网关统一入口
9. ✅ 高可用保障机制

### 潜在改进方向
1. **监控与日志**: 建议集成ELK日志系统和Prometheus监控
2. **缓存优化**: 可以增加更多Redis缓存策略
3. **消息队列**: 考虑引入RabbitMQ处理异步任务
4. **容器化部署**: 完善Docker和Kubernetes部署方案
5. **API文档**: 集成Swagger UI提供在线API文档
6. **测试覆盖**: 增加单元测试和集成测试
7. **性能优化**: 数据库索引优化和查询性能调优