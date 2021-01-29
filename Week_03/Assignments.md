# 第一次课(20210120)

## （必做）整合你上次作业的 httpclient/okhttp。

客户端访问Netty网关后，通过OkHttpClient转发。

```html
Starting at 2021/1/26 19:07:43
[Press C to stop the test]
26569   (RPS: 1071.9)
---------------Finished!----------------
Finished at 2021/1/26 19:08:08 (took 00:00:24.9314563)
Status 200:    26569

RPS: 1257.1 (requests/second)
Max: 775ms
Min: 0ms
Avg: 5.8ms

  50%   below 3ms
  60%   below 4ms
  70%   below 6ms
  80%   below 8ms
  90%   below 12ms
  95%   below 17ms
  98%   below 25ms
  99%   below 35ms
99.9%   below 83ms
```

答案链接源码(https://github.com/maolikui/JAVA-01/tree/main/Week_03/code/netty-gateway)

## （选做）使用 netty 实现后端 http 访问（代替上一步骤）。

答案链接源码(https://github.com/maolikui/JAVA-01/tree/main/Week_03/code/netty-gateway)

# 第二次课(20210124)

## （必做）实现过滤器。 

答案链接源码(https://github.com/maolikui/JAVA-01/tree/main/Week_03/code/netty-gateway)

## （选做）实现路由。

```html
Starting at 2021/1/26 19:18:51
[Press C to stop the test]
31704   (RPS: 1357.5)
---------------Finished!----------------
Finished at 2021/1/26 19:19:14 (took 00:00:23.4874552)
Status 200:    31706

RPS: 1499.9 (requests/second)
Max: 117ms
Min: 0ms
Avg: 2.8ms

  50%   below 2ms
  60%   below 3ms
  70%   below 3ms
  80%   below 5ms
  90%   below 6ms
  95%   below 9ms
  98%   below 12ms
  99%   below 15ms
99.9%   below 42ms
```

答案链接源码(https://github.com/maolikui/JAVA-01/tree/main/Week_03/code/netty-gateway)