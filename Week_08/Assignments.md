# 第十五次课(20210303)

## 设计对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。并在新结构演示常见的增删改查操作。代码、SQL和配置文件，上传到Github。

解题过程：

1. 要求是水平分库分表，直接创建2个数据库，每个数据库分别创建16张订单表(可以采用存储过程或者复制，本例一个一个创建)。

2. IDEA 导入shardingsphere-proxy项目，以源码的方式启动。						

   - 本例中使用了MySQL，在启动项模块里添加MySQL驱动。

   - 配置Server.yaml文件

     ```html
     authentication:
       users:
         root:
           password: root
           hostname: '%'
     #    sharding:
     #      password: sharding
     
     #scaling:
     #  blockQueueSize: 10000
     #  workerThread: 40
     #
     props:
       max-connections-size-per-query: 1
       executor-size: 16  # Infinite by default.
       proxy-frontend-flush-threshold: 128  # The default value is 128.
         # LOCAL: Proxy will run with LOCAL transaction.
         # XA: Proxy will run with XA transaction.
         # BASE: Proxy will run with B.A.S.E transaction.
       proxy-transaction-type: LOCAL
       xa-transaction-manager-type: Atomikos
       proxy-opentracing-enabled: false
       proxy-hint-enabled: false
       query-with-cipher-column: true
       sql-show: false
       check-table-metadata-enabled: false
       lock-wait-timeout-milliseconds: 50000 # The maximum time to wait for a lock
     ```

   - 配置config-sharding.yaml文件

     ```html
     schemaName: sharding_oms_order
     
     dataSources:
       ds_0:
         url: jdbc:mysql://192.168.0.130:3306/liquidmall?serverTimezone=UTC&useSSL=false
         username: root
         password: 123456
         connectionTimeoutMilliseconds: 30000
         idleTimeoutMilliseconds: 60000
         maxLifetimeMilliseconds: 1800000
         maxPoolSize: 50
         minPoolSize: 1
         maintenanceIntervalMilliseconds: 30000
       ds_1:
         url: jdbc:mysql://192.168.0.131:3306/liquidmall?serverTimezone=UTC&useSSL=false
         username: root
         password: 123456
         connectionTimeoutMilliseconds: 30000
         idleTimeoutMilliseconds: 60000
         maxLifetimeMilliseconds: 1800000
         maxPoolSize: 50
         minPoolSize: 1
         maintenanceIntervalMilliseconds: 30000
     
     rules:
       - !SHARDING
         tables:
           oms_order:
             actualDataNodes: ds_${0..1}.oms_order_${0..15}
             tableStrategy:
               standard:
                 shardingColumn: user_id
                 shardingAlgorithmName: oms_order_inline
             keyGenerateStrategy:
               column: order_sn
               keyGeneratorName: snowflake
         bindingTables:
           - oms_order
         defaultDatabaseStrategy:
           standard:
             shardingColumn: user_id
             shardingAlgorithmName: database_inline
         defaultTableStrategy:
           none:
     
         shardingAlgorithms:
           database_inline:
             type: INLINE
             props:
               algorithm-expression: ds_${((user_id % 32) / 16) as int}
           oms_order_inline:
             type: INLINE
             props:
               algorithm-expression: oms_order_${(user_id % 32) % 16}
     
         keyGenerators:
           snowflake:
             type: SNOWFLAKE
             props:
               worker-id: 123
     ```

3. 可以将shardingsphere-proxy当作MySQL数据库操作，默认端口3307.测试了增删查改操作，重点测试指定user_id的订单插入，看有没有进入预期的数据库。

# 第十六次课(20210306)

## 基于hmily TCC或ShardingSphere的Atomikos  XA实现一个简单的分布式事务应用demo(二选一)，提交到Github。

hmily 项目里的关于spring cloud的项目已经Debug过。

本例参考Demo,使用Nacos注册中心实现。

答案代码链接(https://github.com/maolikui/JAVA-01/tree/main/Week_08/code/hmily-tcc-demo)

