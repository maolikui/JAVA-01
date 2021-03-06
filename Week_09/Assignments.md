# 第十七次课(20210310)

## （必做）改造自定义 RPC 的程序，提交到 GitHub：

**● 尝试将服务端写死查找接口实现类变成泛型和反射；**
**● 尝试将客户端动态代理改成 AOP，添加异常处理；**
**● 尝试使用 Netty+HTTP 作为 client 端传输方式。**

基于rpcfx改造，自定义spring boot starter方式集成框架。

类似MyBatis扫描Mapper接口文件，增加客户端扫描服务接口，自动注册到Spring容器中，

采用Netty TCP传输，并添加Kryo序列化。

答案代码链接(https://github.com/maolikui/JAVA-01/tree/main/Week_09/code/rpcfx)

# 第十八次课(20210314)

## （必做）结合 dubbo+hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:

● **用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;**
**● 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;**
**● 设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。**

题目分析：

用户A和用户B的货币账户分别在A，B两个库，那么A，B两个用户在一次交易过程中，要满足：

1.两个用户要分别满足各自的美元和人民币账户一个账户打钱和另个账户扣钱的事务；

2.对于交易系统而言，A和B用户要满足本次交易的事务。

设计本次交易不依赖于交易平台，即不使用交易平台周转。用户A和用户B使用不同的业务服务交易，以便使用dubbo框架进行远程调用。此外每个用户由可用余额和冻结余额组成账户余额。

答案代码链接(https://github.com/maolikui/JAVA-01/tree/main/Week_09/code/trading-sys-demo)
