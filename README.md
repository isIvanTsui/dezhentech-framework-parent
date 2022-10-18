<h1 align="center">德臻（成都）健康科技有限公司 基础框架</h1>

### 目录结构

```
dezhentech-framework-parent --顶级父项目
    ├─dezhentech-dependencies  --依赖版本管理，所有的版本信息在这里声明
    └─dezhentech-framework  --基础框架
        ├─dezhentech-common  --一些公共的方法
        │  ├─dezhentech-common-cache  -- 缓存相关公共代码
        │  ├─dezhentech-common-database  -- 数据库连接相关公共代码
        │  ├─dezhentech-common-mq  -- 消息队列相关公共代码
        │  └─dezhentech-common-security  -- 安全相关公共代码
        ├─dezhentech-starter-es  --全文检索
        ├─dezhentech-starter-log  --日志
        ├─dezhentech-starter-message  --消息平台
        ├─dezhentech-starter-mq  --消息队列项目
        ├─dezhentech-starter-mybatisplus  --持久层框架
        ├─dezhentech-starter-redis  --缓存
        ├─dezhentech-starter-security  --安全框架
        └─dezhentech-starter-swagger --接口文档
```

