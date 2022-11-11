<h1 align="center">德臻（成都）健康科技有限公司 基础框架</h1>

### 项目结构📂

```
dezhen-framework-parent:.
    ├─dezhentech-dependencies-------------------------顶级父项目
    └─dezhentech-framework----------------------------依赖版本管理，所有的版本信息在这里声明
        ├─dezhentech-api------------------------------远程调用，对外暴露的API接口
        │  └─dezhentech-api-system--------------------系统服务对外暴露的API
        │      
        ├─dezhentech-common---------------------------公共项目
        │  ├─dezhentech-common-core-------------------公共工具包项目
        │  │  
        │  └─dezhentech-common-security---------------公共权限切面
        │     
        ├─dezhentech-starter-admin--------------------安全健康中心
        │  
        ├─dezhentech-starter-auth---------------------认证服务
        │  
        ├─dezhentech-starter-cache--------------------缓存
        │  
        ├─dezhentech-starter-cloudweb-----------------基本Web应用
        │ 
        ├─dezhentech-starter-es-----------------------全文检索
        │ 
        ├─dezhentech-starter-gateway------------------网关服务
        │  
        ├─dezhentech-starter-id-----------------------全局ID服务
        │  
        ├─dezhentech-starter-log----------------------日志切面
        │  
        ├─dezhentech-starter-message------------------消息中心服务
        │  
        ├─dezhentech-starter-mq-----------------------队列
        │  
        ├─dezhentech-starter-mybatisplus--------------持久层框架
        │  
        ├─dezhentech-starter-redis--------------------Redis框架
        │  
        ├─dezhentech-starter-swagger------------------接口文档框架
        │  
        ├─dezhentech-starter-system-------------------系统服务
        │  
        └─dezhentech-starter-web----------------------单体Web框架
       
```

### 进度

|      基础框架名       |                   使用技术                   | 完成度 |            备注            |
| :-------------------: | :------------------------------------------: | :----: | :------------------------: |
|     `common-core`     |                                              | ■□□□□  |         各种工具类         |
|   `common-security`   |                    `AOP`                     | ■■■■□  |      公共权限校验切面      |
|    `starter-admin`    |              `SpringCloudAdmin`              | ■■■■■  |      服务健康监控中心      |
|    `starter-auth`     |                `JWT、OAuth2`                 | ■□□□□  |   认证中心，负责登陆认证   |
|    `starter-cache`    |                  `JetCache`                  | □□□□□  |            缓存            |
|  `starter-cloudweb`   | `Nacos、Sentinel、Zipkin、SpringWeb、、、、` | ■■■■□  |       基本应用启动器       |
|     `starter-es`      |                    `ELK`                     | □□□□□  |          全文检索          |
|   `starter-gateway`   |             `SpringCloudGateway`             | ■■■□□  |      网关，过滤，路由      |
|   `starter-system`    |           `SpringWeb、MyBatisPlus`           | ■□□□□  |          系统服务          |
|   `starter-swagger`   |                  `Knife4j`                   | ■■■■■  |        接口文档管理        |
| `starter-mybatisplus` |                `MyBatisPlus`                 | ■■■■■  |         持久层框架         |
|     `starter-mq`      |         `RabbitMQ、RocketMQ、Kafka`          | □□□□□  |        消息队列框架        |
|   `starter-message`   |                     `mq`                     | □□□□□  | 消息中心，用于推送各种消息 |
|     `starter-log`     |                     待定                     | ■■□□□  |          日志框架          |
|    `starter-seata`    |                   `Seata`                    | □□□□□  |         分布式事务         |

### 实施计划🛠

|     项目内容      |                        实施步骤或内容                        | 相关负责人 | 完成时间 |
| :---------------: | :----------------------------------------------------------: | :--------: | :------: |
|   `common-core`   |                      编写各种通用工具类                      |            |          |
|  `starter-auth`   | 登陆认证，支持用户名密码认证，code认证，第三方授权等认证方式 |            |          |
| `starter-system`  | 用户信息、角色、菜单、部门等用户管理的CRUD接口，并为其他服务提供API接口 |            |          |
| ``starter-cache`` |        缓存基础框架，用`Jetcache`去实现缓存该有的功能        |            |          |
|   `starter-mq`    | 我们自己的消息队列框架，统一的API接口，我们支持RabbitmQ、RocketMQ、Kafka等实现方式。用户使用时只用调用API接口，不用关心实现，且用户可以自己选择底层用哪种技术栈实现。 |            |          |
| `starter-message` | 消息中心服务，支持邮件推送、短信推送和APP消息推送。适配极光推送、个推、阿里云短信、腾讯云短信、云片短信等第三方云服务 |            |          |
|   `starter-es`    |                         全文检索服务                         |            |          |
|  `starter-seata`  |                          分布式事务                          |            |          |

