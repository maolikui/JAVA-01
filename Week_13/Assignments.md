# 第二十五次课(20210407)

## （必做）搭建一个3节点Kafka集群，测试功能和性能；实现Spring kafka下对Kafka集群的操作，将代码提交到github.

本题目通过Docker方式搭建三个节点的集群,端口依次配置成9090,9091,9092,运行命令如下：

```html
docker run  -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.0.131:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.0.131:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka
```

启动kafka管理客户端kafka-manager:

```html
docker run -d --name kafka-manager -p 9000:9000 -e ZK_HOSTS=192.168.0.131:2181 sheepkiller/kafka-manager
```

创建Topic

```html
./kafka-topics.sh --create --zookeeper 192.168.0.131:2181 --replication-factor 2 --partitions 2 --topic test
```

答案代码链接(https://github.com/maolikui/JAVA-01/tree/main/Week_13/code/kafka-demo)

# 第二十六次课(20210411)

## 思考和设计自定义MQ第二个版本或第三个版本，写代码实现其中至少一个功能点，把设计思路和实现代码，提交到github.

1.项目由broker,client和sample组成，

2.sample集成client生产消息，通过rpc框架(依赖本地自定义rpc框架)远程调用broker server，将消息push至broker server并存储在server的内存中，

3.client端启动消费者线程定时从broker server pull 消息

答案代码链接(https://github.com/maolikui/JAVA-01/tree/main/Week_13/code/kafka-demo/liquid-mq)

