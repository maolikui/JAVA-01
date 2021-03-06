# 第十三次课(20210224)

## （必做）按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。

答案代码链接(https://github.com/maolikui/JAVA-01/tree/main/Week_07/code/insert-batch-demo)

# 第十四次课(20210302)

## 读写分离-动态切换数据源版本1.0

当前仅实现AOP和自定义注解自动切换数据源。

支持多个从库思路：需要解析application.yml配置文件，将多个从库数据源统一按照编号命名(secondary_1,secondary_2...)基于该版本，在AOP拦截的地方进行路由。

答案代码链接(https://github.com/maolikui/JAVA-01/tree/main/Week_07/code/dynamic-dbsource-switching)

## 读写分离-数据库框架版本2.0

暂时没有实现。

分析，1.0版本中使用了AOP和注解，有较强的侵入性。