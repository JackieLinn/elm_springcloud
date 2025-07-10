# API 详细文档

---

## 认证服务（auth-service）

### POST /auth/ask-code
说明：请求邮件验证码
请求体（Query参数）：
| 字段名 | 类型   | 必填 | 说明         |
|--------|--------|------|--------------|
| email  | String | 是   | 邮箱         |
| type   | String | 是   | 类型(register/reset) |
响应体（JSON）：
| 字段名  | 类型 | 说明     |
|---------|------|----------|
| code    | int  | 状态码   |
| message | String | 提示信息 |
| data    | null | 无数据   |

### POST /auth/register
说明：用户注册
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明     |
|-----------|--------|------|----------|
| email     | String | 是   | 邮箱     |
| code      | String | 是   | 邮箱验证码 |
| userName  | String | 是   | 用户名   |
| password  | String | 是   | 密码     |
响应体（JSON）：
| 字段名  | 类型 | 说明     |
|---------|------|----------|
| code    | int  | 状态码   |
| message | String | 提示信息 |
| data    | null | 无数据   |

### POST /auth/reset-confirm
说明：密码重置确认
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明     |
|-----------|--------|------|----------|
| email     | String | 是   | 邮箱     |
| code      | String | 是   | 邮箱验证码 |
响应体（JSON）：
| 字段名  | 类型 | 说明     |
|---------|------|----------|
| code    | int  | 状态码   |
| message | String | 提示信息 |
| data    | null | 无数据   |

### POST /auth/reset-password
说明：密码重置
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明     |
|-----------|--------|------|----------|
| email     | String | 是   | 邮箱     |
| code      | String | 是   | 邮箱验证码 |
| password  | String | 是   | 新密码   |
响应体（JSON）：
| 字段名  | 类型 | 说明     |
|---------|------|----------|
| code    | int  | 状态码   |
| message | String | 提示信息 |
| data    | null | 无数据   |

### GET /api/account/get-account-by-userId
说明：根据用户ID获取用户信息
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| userId | Long | 是   | 用户ID |
响应体（JSON）：
| 字段名  | 类型     | 说明         |
|---------|----------|--------------|
| code    | int      | 状态码       |
| message | String   | 提示信息     |
| data    | object   | 用户信息     |

### GET /api/account/pay
说明：支付操作
请求体（Query参数）：
| 字段名 | 类型   | 必填 | 说明   |
|--------|--------|------|--------|
| userId | Long   | 是   | 用户ID |
| price  | Double | 是   | 金额   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### GET /api/account/refund
说明：退款操作
请求体（Query参数）：
| 字段名 | 类型   | 必填 | 说明   |
|--------|--------|------|--------|
| userId | Long   | 是   | 用户ID |
| price  | Double | 是   | 金额   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/account/role/update
说明：更新用户角色
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| userId | Long | 是   | 用户ID |
| roleId | Long | 是   | 角色ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### GET /api/account/remote/get-user-id
说明：根据用户名查用户ID
请求体（Query参数）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userName | String | 是   | 用户名 |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | Long   | 用户ID   |

### GET /api/account/admin/list
说明：管理员分页查用户
请求体（Query参数）：
| 字段名  | 类型   | 必填 | 说明     |
|---------|--------|------|----------|
| pageNum | int    | 是   | 页码     |
| pageSize| int    | 是   | 页大小   |
| roleId  | Long   | 否   | 角色ID   |
响应体（JSON）：
| 字段名  | 类型     | 说明         |
|---------|----------|--------------|
| code    | int      | 状态码       |
| message | String   | 提示信息     |
| data    | object   | 分页用户信息 |

### POST /api/account/admin/add
说明：管理员新增用户
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明   |
|-----------|--------|------|--------|
| userName  | String | 是   | 用户名 |
| password  | String | 是   | 密码   |
| email     | String | 是   | 邮箱   |
| ...       | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/account/admin/delete
说明：管理员删除用户
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| userId | Long | 是   | 用户ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/account/admin/update
说明：管理员修改用户信息
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明   |
|-----------|--------|------|--------|
| userId    | Long   | 是   | 用户ID |
| ...       | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

---

## 商家服务（business-service）

### GET /api/business/get-all-categories
说明：获取所有点餐分类
请求体（无参数）
响应体（JSON）：
| 字段名  | 类型             | 说明         |
|---------|------------------|--------------|
| code    | int              | 状态码       |
| message | String           | 提示信息     |
| data    | object(Map)      | 分类映射     |

### GET /api/business/get-recommend-business
说明：获取推荐商家
请求体（无参数）
响应体（JSON）：
| 字段名  | 类型             | 说明         |
|---------|------------------|--------------|
| code    | int              | 状态码       |
| message | String           | 提示信息     |
| data    | array            | 商家列表     |

### GET /api/business/get-business-by-orderTypeId
说明：根据分类获取商家
请求体（Query参数）：
| 字段名      | 类型   | 必填 | 说明     |
|-------------|--------|------|----------|
| orderTypeId | int    | 是   | 分类ID   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | array  | 商家列表 |

### GET /api/business/get-business-by-businessId
说明：根据商家ID获取商家信息
请求体（Query参数）：
| 字段名      | 类型   | 必填 | 说明     |
|-------------|--------|------|----------|
| businessId  | Long   | 是   | 商家ID   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | object | 商家信息 |

### GET /api/business/get-delivery-price
说明：获取商家配送费
请求体（Query参数）：
| 字段名      | 类型   | 必填 | 说明     |
|-------------|--------|------|----------|
| businessId  | Long   | 是   | 商家ID   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | double | 配送费   |

### GET /api/business/search
说明：搜索商家
请求体（Query参数）：
| 字段名   | 类型   | 必填 | 说明     |
|----------|--------|------|----------|
| keyword  | String | 是   | 搜索关键词|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | array  | 商家列表 |

### GET /api/business/list-by-user
说明：根据用户ID获取商家信息
请求体（Query参数）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userId   | Long   | 是   | 用户ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | array  | 商家列表 |

### POST /api/business/update-info-by-user
说明：修改商家信息
请求体（Query参数+JSON）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userId   | Long   | 是   | 用户ID |
| business | object | 是   | 商家信息|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### GET /api/business/user-business/check
说明：校验用户是否拥有商家
请求体（Query参数）：
| 字段名    | 类型   | 必填 | 说明   |
|-----------|--------|------|--------|
| userId    | Long   | 是   | 用户ID |
| businessId| Long   | 是   | 商家ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否拥有 |

### GET /api/business/get-business-by-businessId-remote
说明：远程调用-根据商家ID获取商家信息
请求体（Query参数）：
| 字段名      | 类型   | 必填 | 说明     |
|-------------|--------|------|----------|
| businessId  | Long   | 是   | 商家ID   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| data    | object | 商家信息 |

### GET /api/business/get-business-info
说明：远程调用-根据商家ID列表获取商家信息
请求体（Query参数）：
| 字段名      | 类型     | 必填 | 说明     |
|-------------|----------|------|----------|
| businessIds | Set<Long>| 是   | 商家ID集合|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| data    | object | 商家信息映射|

### POST /api/business/admin/disable
说明：管理员禁用商家
请求体（Query参数）：
| 字段名      | 类型   | 必填 | 说明     |
|-------------|--------|------|----------|
| businessId  | Long   | 是   | 商家ID   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/business/admin/enable
说明：管理员启用商家
请求体（Query参数）：
| 字段名      | 类型   | 必填 | 说明     |
|-------------|--------|------|----------|
| businessId  | Long   | 是   | 商家ID   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### GET /api/business/admin/list
说明：管理员分页查找所有商家
请求体（Query参数）：
| 字段名   | 类型   | 必填 | 说明     |
|----------|--------|------|----------|
| pageNum  | int    | 是   | 页码     |
| pageSize | int    | 是   | 页大小   |
| status   | int    | 否   | 状态     |
| keyword  | String | 否   | 关键词   |
响应体（JSON）：
| 字段名  | 类型     | 说明         |
|---------|----------|--------------|
| code    | int      | 状态码       |
| message | String   | 提示信息     |
| data    | object   | 分页商家信息 |

### GET /api/business/admin/detail
说明：管理员查单个商家详情
请求体（Query参数）：
| 字段名      | 类型   | 必填 | 说明     |
|-------------|--------|------|----------|
| businessId  | Long   | 是   | 商家ID   |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | object | 商家信息 |

### POST /api/business/merchant/apply
说明：提交入驻申请（用户名）
请求体（JSON）：
| 字段名        | 类型   | 必填 | 说明     |
|---------------|--------|------|----------|
| userName      | String | 是   | 用户名   |
| businessName  | String | 是   | 商家名称 |
| businessAddress| String| 是   | 商家地址 |
| contactPhone  | String | 是   | 联系电话 |
| businessDesc  | String | 否   | 商家简介 |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/business/merchant/apply-by-id
说明：提交入驻申请（用户ID）
请求体（JSON）：
| 字段名        | 类型   | 必填 | 说明     |
|---------------|--------|------|----------|
| applicantId   | Long   | 是   | 用户ID   |
| businessName  | String | 是   | 商家名称 |
| businessAddress| String| 是   | 商家地址 |
| contactPhone  | String | 是   | 联系电话 |
| businessDesc  | String | 否   | 商家简介 |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### GET /api/business/merchant/status
说明：查询入驻申请状态
请求体（Query参数）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userId   | Long   | 是   | 用户ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | array  | 申请列表 |

### GET /api/business/admin/apply/list
说明：管理员分页查询入驻申请列表
请求体（Query参数）：
| 字段名   | 类型   | 必填 | 说明     |
|----------|--------|------|----------|
| pageNum  | int    | 是   | 页码     |
| pageSize | int    | 是   | 页大小   |
| status   | int    | 否   | 状态     |
响应体（JSON）：
| 字段名  | 类型     | 说明         |
|---------|----------|--------------|
| code    | int      | 状态码       |
| message | String   | 提示信息     |
| data    | object   | 分页申请信息 |

### POST /api/business/admin/apply/review
说明：管理员审核入驻申请
请求体（Query参数）：
| 字段名      | 类型   | 必填 | 说明     |
|-------------|--------|------|----------|
| applyId     | Long   | 是   | 申请ID   |
| result      | int    | 是   | 审核结果 |
| reviewReason| String | 是   | 审核原因 |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

---

## 菜品服务（food-service）

### GET /api/food/list-food-by-BusinessId
说明：根据商家ID获取上架菜品（客户端用）
请求体（Query参数）：
| 字段名     | 类型 | 必填 | 说明   |
|------------|------|------|--------|
| businessId | Long | 是   | 商家ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | array  | 菜品列表 |

### GET /api/food/list-all-food-by-BusinessId
说明：根据商家ID获取所有菜品（商家/管理端用）
请求体（Query参数）：
| 字段名     | 类型 | 必填 | 说明   |
|------------|------|------|--------|
| businessId | Long | 是   | 商家ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | array  | 菜品列表 |

### GET /api/food/list-food-by-FoodId
说明：根据菜品ID获取菜品（远程调用）
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| foodId | Long | 是   | 菜品ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| data    | object | 菜品信息 |

### GET /api/food/get-food-info
说明：根据菜品ID列表获取菜品（远程调用）
请求体（Query参数）：
| 字段名 | 类型     | 必填 | 说明     |
|--------|----------|------|----------|
| foodIds| Set<Long>| 是   | 菜品ID集合|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| data    | object | 菜品信息映射|

### POST /api/food/add
说明：新增菜品
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明   |
|-----------|--------|------|--------|
| businessId| Long   | 是   | 商家ID |
| foodName  | String | 是   | 菜品名 |
| ...       | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/food/update
说明：修改菜品
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明   |
|-----------|--------|------|--------|
| foodId    | Long   | 是   | 菜品ID |
| ...       | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/food/delete
说明：逻辑删除菜品
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| foodId | Long | 是   | 菜品ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/food/status
说明：上下架菜品
请求体（Query参数）：
| 字段名    | 类型 | 必填 | 说明     |
|-----------|------|------|----------|
| foodId    | Long | 是   | 菜品ID   |
| foodStatus| int  | 是   | 状态     |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/food/admin/disable
说明：管理员下架菜品
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| foodId | Long | 是   | 菜品ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/food/admin/enable
说明：管理员恢复菜品上架
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| foodId | Long | 是   | 菜品ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

---

## 购物车服务（cart-service）

### POST /api/cart/list-cart
说明：获取购物车信息
请求体（JSON）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userId   | Long   | 是   | 用户ID |
| ...      | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | array  | 购物车列表|

### POST /api/cart/save-cart
说明：保存购物车信息
请求体（JSON）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userId   | Long   | 是   | 用户ID |
| ...      | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | int    | 影响行数 |

### POST /api/cart/update-cart
说明：更新购物车信息
请求体（JSON）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userId   | Long   | 是   | 用户ID |
| ...      | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | int    | 影响行数 |

### POST /api/cart/remove-cart
说明：移除购物车信息
请求体（JSON）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userId   | Long   | 是   | 用户ID |
| ...      | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | int    | 影响行数 |

### GET /api/cart/get-cart-quantity
说明：获取购物车数量
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| userId | Long | 是   | 用户ID |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| code    | int    | 状态码       |
| message | String | 提示信息     |
| data    | array  | 购物车数量列表|

### GET /api/cart/get-cart-map
说明：远程调用-获取购物车映射
请求体（Query参数）：
| 字段名     | 类型 | 必填 | 说明   |
|------------|------|------|--------|
| userId     | Long | 是   | 用户ID |
| businessId | Long | 是   | 商家ID |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| data    | object | 购物车映射   |

### POST /api/cart/delete-by-cid
说明：远程调用-删除购物车记录
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明     |
|--------|------|------|----------|
| cartId | Long | 是   | 购物车ID |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| data    | int    | 影响行数     |

---

## 订单服务（orders-service）

### POST /api/orders/create-orders
说明：创建订单
请求体（JSON）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| userId   | Long   | 是   | 用户ID |
| ...      | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | Long   | 订单ID   |

### GET /api/orders/get-business-info
说明：根据订单编号获取商家信息
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| orderId| Long | 是   | 订单ID |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| code    | int    | 状态码       |
| message | String | 提示信息     |
| data    | object | 商家信息     |

### GET /api/orders/get-food-info
说明：根据订单编号获取食物信息
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| orderId| Long | 是   | 订单ID |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| code    | int    | 状态码       |
| message | String | 提示信息     |
| data    | array  | 食物信息列表 |

### POST /api/orders/payment
说明：支付操作
请求体（JSON）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| orderId  | Long   | 是   | 订单ID |
| ...      | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### GET /api/orders/get-all-order-info
说明：获取所有订单信息
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| userId | Long | 是   | 用户ID |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| code    | int    | 状态码       |
| message | String | 提示信息     |
| data    | object | 订单信息列表 |

### POST /api/orders/update-address
说明：更新送餐地址
请求体（JSON）：
| 字段名   | 类型   | 必填 | 说明   |
|----------|--------|------|--------|
| orderId  | Long   | 是   | 订单ID |
| ...      | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | int    | 影响行数 |

### GET /api/orders/list-by-business
说明：商家端-根据商家ID查询订单列表
请求体（Query参数）：
| 字段名    | 类型 | 必填 | 说明     |
|-----------|------|------|----------|
| userId    | Long | 是   | 用户ID   |
| businessId| Long | 是   | 商家ID   |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| code    | int    | 状态码       |
| message | String | 提示信息     |
| data    | array  | 订单列表     |

### GET /api/orders/detail
说明：商家端-订单详情
请求体（Query参数）：
| 字段名  | 类型 | 必填 | 说明   |
|---------|------|------|--------|
| userId  | Long | 是   | 用户ID |
| orderId | Long | 是   | 订单ID |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| code    | int    | 状态码       |
| message | String | 提示信息     |
| data    | object | 订单详情     |

### POST /api/orders/accept
说明：商家端-接单
请求体（Query参数）：
| 字段名  | 类型 | 必填 | 说明   |
|---------|------|------|--------|
| userId  | Long | 是   | 用户ID |
| orderId | Long | 是   | 订单ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/orders/finish
说明：商家端-完成订单
请求体（Query参数）：
| 字段名  | 类型 | 必填 | 说明   |
|---------|------|------|--------|
| userId  | Long | 是   | 用户ID |
| orderId | Long | 是   | 订单ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### POST /api/orders/reject
说明：商家端-拒单
请求体（Query参数）：
| 字段名  | 类型 | 必填 | 说明   |
|---------|------|------|--------|
| userId  | Long | 是   | 用户ID |
| orderId | Long | 是   | 订单ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | bool   | 是否成功 |

### GET /api/orders/admin/list-by-business
说明：管理员端-按商家ID分页查订单
请求体（Query参数）：
| 字段名    | 类型 | 必填 | 说明     |
|-----------|------|------|----------|
| businessId| Long | 是   | 商家ID   |
| pageNum   | int  | 是   | 页码     |
| pageSize  | int  | 是   | 页大小   |
| orderState| int  | 否   | 订单状态 |
响应体（JSON）：
| 字段名  | 类型     | 说明         |
|---------|----------|--------------|
| code    | int      | 状态码       |
| message | String   | 提示信息     |
| data    | object   | 分页订单信息 |

---

## 地址服务（address-service）

### GET /api/deliveryAddress/get-all-address
说明：获取用户所有地址信息
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| userId | Long | 是   | 用户ID |
响应体（JSON）：
| 字段名  | 类型   | 说明         |
|---------|--------|--------------|
| code    | int    | 状态码       |
| message | String | 提示信息     |
| data    | array  | 地址列表     |

### POST /api/deliveryAddress/save-address
说明：保存地址信息
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明   |
|-----------|--------|------|--------|
| userId    | Long   | 是   | 用户ID |
| ...       | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | int    | 影响行数 |

### POST /api/deliveryAddress/update-address
说明：更新地址信息
请求体（JSON）：
| 字段名    | 类型   | 必填 | 说明   |
|-----------|--------|------|--------|
| userId    | Long   | 是   | 用户ID |
| ...       | ...    | ...  | 其他字段|
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | int    | 影响行数 |

### POST /api/deliveryAddress/remove-address
说明：移除地址信息
请求体（Query参数）：
| 字段名 | 类型 | 必填 | 说明   |
|--------|------|------|--------|
| daId   | Long | 是   | 地址ID |
响应体（JSON）：
| 字段名  | 类型   | 说明     |
|---------|--------|----------|
| code    | int    | 状态码   |
| message | String | 提示信息 |
| data    | int    | 影响行数 |

</rewritten_file> 