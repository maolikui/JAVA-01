# 第二十三次课(20210331)

## （必做）配置redis的主从复制，sentinel高可用，Cluster集群。提交如下内容到github:

**1)  config配置文件**

**2）启动和操作、验证集群下数据读写的命令步骤。**

采用Docker方式配置

1. redis主从复制
   通过Docker 启动两个redis服务实例模拟主从复制，然后在一个实例cli终端命令行运行如下命令即可完成主从配置

   ```html
   SLAVEOF IP PORT
   ```

   解除主从配置可以通过如下命令

   ```html
   SLAVEOF no one
   ```

2. redis sentinel高可用

   通过Docker启动三个redis服务实例模拟sentinel高可用，并设置一主两从。另外启动配置一个sentinel服务实例(此处也可以为每一台实例配置一个sentinel)，sentinel.conf配置文件如下：

   ```html
   sentinel monitor mymaster 172.17.0.2 6379 1
   ```

   关闭主机服务实例，会自动选举产生新的主机。

3. redis cluster集群

   通过Docker启动六个Redis 实例，以--net=host模式启动。Redis实例需要配置：

   ```html
   cluster-enabled yes
   ```

   运行

   ```html
   root@LiquidOS:/data# redis-cli --cluster create 192.168.0.130:6379 192.168.0.130:6380 192.168.0.130:6381 192.168.0.130:6382 192.168.0.130:6383 192.168.0.130:6384 --cluster-replicas 1 -a root
   Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
   >>> Performing hash slots allocation on 6 nodes...
   Master[0] -> Slots 0 - 5460
   Master[1] -> Slots 5461 - 10922
   Master[2] -> Slots 10923 - 16383
   Adding replica 192.168.0.130:6383 to 192.168.0.130:6379
   Adding replica 192.168.0.130:6384 to 192.168.0.130:6380
   Adding replica 192.168.0.130:6382 to 192.168.0.130:6381
   >>> Trying to optimize slaves allocation for anti-affinity
   [WARNING] Some slaves are in the same host as their master
   M: 515ba5ab18a5cb2185fde4cdaaacfd2308f8fa98 192.168.0.130:6379
      slots:[0-5460] (5461 slots) master
   M: 524e66ef5faff046de91d7e0c2fbef2abdac340d 192.168.0.130:6380
      slots:[5461-10922] (5462 slots) master
   M: 9e010f7e709db9aba2e51e7851d9c27a2887042e 192.168.0.130:6381
      slots:[10923-16383] (5461 slots) master
   S: 6a9214b1e39db089d9151d7bfb74aafdf2a19bd2 192.168.0.130:6382
      replicates 524e66ef5faff046de91d7e0c2fbef2abdac340d
   S: b614f94bee357cf44ca4c3acb7733641f186e3bb 192.168.0.130:6383
      replicates 9e010f7e709db9aba2e51e7851d9c27a2887042e
   S: 72d2230cc92c3aab0ca4f87634d57790175153b1 192.168.0.130:6384
      replicates 515ba5ab18a5cb2185fde4cdaaacfd2308f8fa98
   Can I set the above configuration? (type 'yes' to accept): yes
   >>> Nodes configuration updated
   >>> Assign a different config epoch to each node
   >>> Sending CLUSTER MEET messages to join the cluster
   Waiting for the cluster to join
   .
   >>> Performing Cluster Check (using node 192.168.0.130:6379)
   M: 515ba5ab18a5cb2185fde4cdaaacfd2308f8fa98 192.168.0.130:6379
      slots:[0-5460] (5461 slots) master
      1 additional replica(s)
   S: 72d2230cc92c3aab0ca4f87634d57790175153b1 192.168.0.130:6384
      slots: (0 slots) slave
      replicates 515ba5ab18a5cb2185fde4cdaaacfd2308f8fa98
   S: 6a9214b1e39db089d9151d7bfb74aafdf2a19bd2 192.168.0.130:6382
      slots: (0 slots) slave
      replicates 524e66ef5faff046de91d7e0c2fbef2abdac340d
   M: 9e010f7e709db9aba2e51e7851d9c27a2887042e 192.168.0.130:6381
      slots:[10923-16383] (5461 slots) master
      1 additional replica(s)
   M: 524e66ef5faff046de91d7e0c2fbef2abdac340d 192.168.0.130:6380
      slots:[5461-10922] (5462 slots) master
      1 additional replica(s)
   S: b614f94bee357cf44ca4c3acb7733641f186e3bb 192.168.0.130:6383
      slots: (0 slots) slave
      replicates 9e010f7e709db9aba2e51e7851d9c27a2887042e
   [OK] All nodes agree about slots configuration.
   >>> Check for open slots...
   >>> Check slots coverage...
   [OK] All 16384 slots covered.
   ```

   命令客户端连接集群

   ```html
   ./redis-cli -p port -c
   ```

   查看集群状态

   ```html
   127.0.0.1:6379> cluster info
   cluster_state:ok
   cluster_slots_assigned:16384
   cluster_slots_ok:16384
   cluster_slots_pfail:0
   cluster_slots_fail:0
   cluster_known_nodes:6
   cluster_size:3
   cluster_current_epoch:6
   cluster_my_epoch:1
   cluster_stats_messages_ping_sent:33
   cluster_stats_messages_pong_sent:38
   cluster_stats_messages_sent:71
   cluster_stats_messages_ping_received:33
   cluster_stats_messages_pong_received:33
   cluster_stats_messages_meet_received:5
   cluster_stats_messages_received:71
   ```

   查看集群中的节点

   ```html
   192.168.0.130:6381> cluster nodes
   524e66ef5faff046de91d7e0c2fbef2abdac340d 192.168.0.130:6380@16380 master - 0 1617471501109 2 connected 5461-10922
   6a9214b1e39db089d9151d7bfb74aafdf2a19bd2 192.168.0.130:6382@16382 slave 524e66ef5faff046de91d7e0c2fbef2abdac340d 0 1617471498107 2 connected
   b614f94bee357cf44ca4c3acb7733641f186e3bb 192.168.0.130:6383@16383 slave 9e010f7e709db9aba2e51e7851d9c27a2887042e 0 1617471500000 3 connected
   9e010f7e709db9aba2e51e7851d9c27a2887042e 192.168.0.130:6381@16381 myself,master - 0 1617471500000 3 connected 10923-16383
   515ba5ab18a5cb2185fde4cdaaacfd2308f8fa98 192.168.0.130:6379@16379 master - 0 1617471500108 1 connected 0-5460
   72d2230cc92c3aab0ca4f87634d57790175153b1 192.168.0.130:6384@16384 slave 515ba5ab18a5cb2185fde4cdaaacfd2308f8fa98 0 1617471498000 1 connected
   ```

   

# 第二十四次课(20210404)

## 搭建ActiveMQ服务，基于JMS,写代码分别实现对于queue和topic的消息生产和消费，代码提交到github.

答案代码链接(https://github.com/maolikui/JAVA-01/tree/main/Week_12)

