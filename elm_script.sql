create table account
(
    userId   bigint auto_increment comment '用户编号'
        primary key,
    userName varchar(255)        not null comment '用户名字',
    password varchar(255)        not null comment '密码',
    userSex  int    default 1    not null comment '用户性别（1: 男, 0: 女）',
    email    varchar(255)        not null comment '用户邮箱',
    userImg  varchar(255)        null comment '用户头像',
    balance  double default 1000 not null comment '用户余额',
    delTag   int                 not null comment '删除标记（1: 正常, 0: 删除）',
    constraint uk_account_username unique (userName)
)
    comment '用户表';

create table business
(
    businessId      bigint auto_increment comment '商家编号'
        primary key,
    businessName    varchar(40)      not null comment '商家名称',
    businessAddress varchar(50)      not null comment '商家地址',
    businessExplain varchar(255)     not null comment '商家介绍',
    businessImg     varchar(255)     not null comment '商家图片',
    orderTypeId     int              not null comment '点餐分类',
    startPrice      double default 0 null comment '起送费',
    deliveryPrice   double default 0 null comment '配送费',
    remarks         varchar(40)      null comment '备注',
    status          int    default 1 not null comment '商家状态（1: 正常, 0: 禁用）'
)
    comment '商家表';

create table business_apply
(
    applyId         bigint auto_increment comment '申请编号'
        primary key,
    applicantId     bigint        not null comment '申请人用户ID',
    businessName    varchar(40)   not null comment '申请商家名称',
    businessAddress varchar(50)   not null comment '申请商家地址',
    contactPhone    varchar(20)   not null comment '联系电话',
    businessDesc    varchar(255)  null comment '商家简介',
    applyStatus     int default 0 not null comment '申请状态（0:待审核, 1:通过, 2:驳回）',
    reviewReason    varchar(255)  null comment '审核意见',
    applyTime       datetime      not null comment '申请时间',
    reviewTime      datetime      null comment '审核时间',
    constraint fk_apply_account
        foreign key (applicantId) references account (userId)
)
    comment '商家入驻申请表';

create table deliveryaddress
(
    daId        bigint auto_increment comment '送货地址编号'
        primary key,
    contactName varchar(20)   not null comment '联系人姓名',
    contactSex  int default 1 not null comment '联系人性别',
    contactTel  varchar(20)   not null comment '联系人电话',
    address     varchar(255)  not null comment '送货地址',
    userId      bigint        not null comment '用户编号',
    constraint deliveryaddress_account_userId_fk
        foreign key (userId) references account (userId)
)
    comment '送货地址表';

create table food
(
    foodId      bigint auto_increment comment '食品编号'
        primary key,
    foodName    varchar(30)                        not null comment '食品名称',
    foodExplain varchar(30)                        not null comment '食品介绍',
    foodImg     varchar(255)                       not null comment '食品图片',
    foodPrice   double   default 9.99              not null comment '食品价格',
    businessId  bigint                             not null comment '所属商家编号',
    remarks     varchar(40)                        null comment '备注',
    foodStatus  tinyint  default 0                 not null comment '菜品状态 0下架 1上架',
    isDeleted   tinyint  default 0                 not null comment '逻辑删除 0正常 1已删除',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime                           null comment '更新时间',
    constraint food_business_businessId_fk
        foreign key (businessId) references business (businessId)
)
    comment '食品表';

create table cart
(
    cartId     bigint auto_increment comment '购物车编号'
        primary key,
    foodId     bigint not null comment '食品编号',
    businessId bigint not null comment '所属商家编号',
    userId     bigint not null comment '所属用户编号',
    quantity   int    not null comment '购买数量',
    constraint cart___fk1
        foreign key (foodId) references food (foodId),
    constraint cart___fk2
        foreign key (businessId) references business (businessId),
    constraint cart___fk3
        foreign key (userId) references account (userId)
)
    comment '购物车表';

create table orders
(
    orderId    bigint auto_increment comment '订单编号'
        primary key,
    userId     bigint           not null comment '所属用户编号',
    businessId bigint           not null comment '所属商家编号',
    orderDate  varchar(255)     not null comment '订单日期',
    orderTotal double default 0 not null comment '订单总价',
    daId       bigint           not null comment '所属送货地址编号',
    orderState int    default 0 not null comment '订单状态（0: 未支付, 1: 已支付/待接单, 2: 已接单, 3: 已完成, 4: 已取消）',
    constraint orders___fk1
        foreign key (userId) references account (userId),
    constraint orders___fk2
        foreign key (businessId) references business (businessId),
    constraint orders___fk3
        foreign key (daId) references deliveryaddress (daId)
)
    comment '订单表';

create table orderdetailed
(
    odId     bigint auto_increment comment '订单明细编号'
        primary key,
    orderId  bigint not null comment '所属订单编号',
    foodId   bigint not null comment '所属食品编号',
    quantity int    not null comment '数量',
    constraint orderdetailet___fk1
        foreign key (orderId) references orders (orderId),
    constraint orderdetailet___fk2
        foreign key (foodId) references food (foodId)
)
    comment '订单明细表';

create table role
(
    roleId   bigint auto_increment comment '角色编号'
        primary key,
    roleName varchar(20) not null comment '角色名字'
)
    comment '角色表';

create table account_role
(
    arId   bigint auto_increment comment '用户角色编号'
        primary key,
    userId bigint not null comment '用户编号',
    roleId bigint not null comment '角色编号',
    constraint account_role___fk1
        foreign key (userId) references account (userId),
    constraint account_role___fk2
        foreign key (roleId) references role (roleId)
)
    comment '用户角色表';

create table user_business
(
    id         bigint auto_increment comment '主键'
        primary key,
    userId     bigint not null comment '用户ID',
    businessId bigint not null comment '商家ID',
    constraint uk_user_business
        unique (userId, businessId),
    constraint fk_ub_business
        foreign key (businessId) references business (businessId),
    constraint fk_ub_user
        foreign key (userId) references account (userId)
)
    comment '用户-商家关联表';


INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (1, '万家饺子(云大店)', '云南省昆明市云南白药街001号', '各种饺子', '/img/sj01.png', 1, 15, 3, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (2, '营养早餐店', '云南省昆明市云南白药街002号', '各种早餐', '/img/sj02.png', 2, 8, 2, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (3, '宜良烤鸭', '云南省昆明市云南白药街003号', '面皮烤鸭', '/img/sj03.png', 3, 19, 2, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (4, '至尊汉堡王', '云南省昆明市云南白药街004号', '汉堡披萨', '/img/sj04.png', 4, 16, 3, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (5, '蜜雪冰城', '云南省昆明市云南白药街005号', '奶茶果茶', '/img/sj05.png', 5, 12, 2, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (6, '便捷快餐店', '云南省昆明市云南白药街006号', '各种快餐', '/img/sj06.png', 6, 9, 1, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (7, '广东肠粉店', '云南省昆明市云南白药街007号', '广东肠粉', '/img/sj07.png', 7, 12, 3, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (8, '桂林米粉', '云南省昆明市云南白药街008号', '面条米线', '/img/sj08.png', 8, 17, 2, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (9, '东北包子铺', '云南省昆明市云南白药街009号', '各种包子', '/img/sj09.png', 9, 6, 3, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (10, '广东烤鸡饭店', '云南省昆明市云南白药街010号', '各种烤鸡', '/img/sj01.png', 10, 20, 2, '无', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (11, '新店名', '新地址', '新介绍', 'http://xxx.com/img.jpg', 2, 20, 3, '新公告', 1);
INSERT INTO elm_springcloud.business (businessId, businessName, businessAddress, businessExplain, businessImg, orderTypeId, startPrice, deliveryPrice, remarks, status) VALUES (12, '新测试商家', '新地址', '新介绍', 'http://example.com/img.jpg', 3, 20, 3, '新备注', 0);

INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (1, '纯肉鲜肉（水饺）', '新鲜猪肉', '/img/sp01.png', 15, 1, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (2, '玉米鲜肉（水饺）', '新鲜玉米水饺', '/img/sp02.png', 16, 1, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (3, '虾仁三鲜（蒸饺）', '虾仁蒸饺', '/img/sp03.png', 22, 1, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (4, '素三鲜（蒸饺）', '美味素三鲜', '/img/sp04.png', 13, 1, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (5, '角瓜鸡蛋（蒸饺）', '新鲜角瓜和鸡蛋', '/img/sp05.png', 15, 1, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (6, '小白菜肉（水饺）', '新鲜小白菜', '/img/sp06.png', 13, 1, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (7, '芹菜牛肉（水饺）', '新鲜芹菜', '/img/sp07.png', 17, 1, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (8, '青椒鲜肉（蒸饺）', '新鲜青椒和猪肉', '/img/sp08.png', 16, 1, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (9, '皮蛋瘦肉粥', '鲜美味粥', '/img/sp01.png', 5, 2, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (10, '鲜牛奶', '新鲜牛奶', '/img/sp02.png', 3, 2, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (11, '油条', '现炸油条', '/img/sp03.png', 2.5, 2, '无', 1, 0, '2025-06-27 08:40:43', null);
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (12, '可乐鸡翅（大份）', '美味可乐鸡翅，分量加大', 'http://xxx.com/kelejichi_big.jpg', 38.5, 1, '大份', 1, 0, '2025-06-27 10:03:54', '2025-06-27 18:51:08');
INSERT INTO elm_springcloud.food (foodId, foodName, foodExplain, foodImg, foodPrice, businessId, remarks, foodStatus, isDeleted, createTime, updateTime) VALUES (13, '大鸡腿', '美味鸡腿', 'http://xxx.com/kelejichi.jpg', 28.5, 2, '新品推荐', 0, 0, '2025-06-28 02:38:59', null);

INSERT INTO elm_springcloud.account (userId, userName, password, userSex, email, userImg, balance, delTag) VALUES (1, 'Yumeky', '$2a$10$2GYiuJe1gjpCLlyWzJCRouTrKWAhqXrFNHpZV3mC93wfgOreMULm6', 1, '1029375749@qq.com', '/img/userImg/yhtx01.png', 1034, 1);
INSERT INTO elm_springcloud.account (userId, userName, password, userSex, email, userImg, balance, delTag) VALUES (2, 'merchant', '$2a$10$2GYiuJe1gjpCLlyWzJCRouTrKWAhqXrFNHpZV3mC93wfgOreMULm6', 1, '1029375749@qq.com', '/img/userImg/yhtx01.png', 1000, 1);
INSERT INTO elm_springcloud.account (userId, userName, password, userSex, email, userImg, balance, delTag) VALUES (3, 'admin', '$2a$10$2GYiuJe1gjpCLlyWzJCRouTrKWAhqXrFNHpZV3mC93wfgOreMULm6', 1, '1029375749@qq.com', '/img/userImg/yhtx01.png', 1000, 1);

INSERT INTO elm_springcloud.role (roleId, roleName) VALUES (1, '管理员');
INSERT INTO elm_springcloud.role (roleId, roleName) VALUES (2, '商家');
INSERT INTO elm_springcloud.role (roleId, roleName) VALUES (3, '普通用户');

INSERT INTO elm_springcloud.deliveryaddress (daId, contactName, contactSex, contactTel, address, userId) VALUES (1, 'Yumeky', 1, '13888888888', '楸苑', 1);

INSERT INTO elm_springcloud.account_role (arId, userId, roleId) VALUES (1, 1, 3);
INSERT INTO elm_springcloud.account_role (arId, userId, roleId) VALUES (2, 2, 2);
INSERT INTO elm_springcloud.account_role (arId, userId, roleId) VALUES (3, 3, 1);

INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (1, 2, 1);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (2, 2, 2);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (3, 2, 3);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (4, 2, 4);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (5, 2, 5);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (6, 2, 6);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (7, 2, 7);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (8, 2, 8);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (9, 2, 9);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (10, 2, 10);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (11, 2, 11);
INSERT INTO elm_springcloud.user_business (id, userId, businessId) VALUES (13, 2, 12);

INSERT INTO elm_springcloud.business_apply (applyId, applicantId, businessName, businessAddress, contactPhone, businessDesc, applyStatus, reviewReason, applyTime, reviewTime) VALUES (1, 2, '测试商家', '测试地址', '13800000000', '测试商家简介', 1, '审核通过', '2025-06-28 02:52:21', '2025-06-28 03:22:41');

INSERT INTO elm_springcloud.orders (orderId, userId, businessId, orderDate, orderTotal, daId, orderState) VALUES (1, 1, 1, '2025-06-27T21:12:43.008811100', 34, 1, 4);

INSERT INTO elm_springcloud.orderdetailed (odId, orderId, foodId, quantity) VALUES (1, 1, 1, 1);
INSERT INTO elm_springcloud.orderdetailed (odId, orderId, foodId, quantity) VALUES (2, 1, 2, 1);



