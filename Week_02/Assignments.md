# 第一次课(20210113)

## 使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。

结合不同的-Xmx,-Xms参数和不同的垃圾收集器。

JDK8默认并行GC:
**-Xmx256m -Xms25m**

```html
C:\Users\DELL\Desktop\data>java -Xmx256m -Xms256m -XX:+PrintGCDetails  GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 65405K->10742K(76288K)] 65405K->23137K(251392K), 0.0045022 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
...
[GC (Allocation Failure) [PSYoungGen: 57950K->16912K(58368K)] 193200K->171128K(233472K), 0.0042345 secs] [Times: user=0.05 sys=0.02, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 16912K->0K(58368K)] [ParOldGen: 154215K->146266K(175104K)] 171128K->146266K(233472K), [Metaspace: 2688K->2688K(1056768K)], 0.0168209 secs] [Times: user=0.08 sys=0.00, real=0.02 secs]
...
[Full GC (Ergonomics) [PSYoungGen: 29505K->28793K(58368K)] [ParOldGen: 174883K->174687K(175104K)] 204389K->203481K(233472K), [Metaspace: 2688K->2688K(1056768K)], 0.0207476 secs] [Times: user=0.08 sys=0.00, real=0.02 secs]
...
[Full GC (Allocation Failure) [PSYoungGen: 29507K->29507K(58368K)] [ParOldGen: 175003K->174984K(175104K)] 204510K->204491K(233472K), [Metaspace: 2688K->2688K(1056768K)], 0.0204267 secs] [Times: user=0.03 sys=0.00, real=0.02 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:42)
        at GCLogAnalysis.main(GCLogAnalysis.java:25)
Heap
 PSYoungGen      total 58368K, used 29696K [0x00000000fab00000, 0x0000000100000000, 0x0000000100000000)
  eden space 29696K, 100% used [0x00000000fab00000,0x00000000fc800000,0x00000000fc800000)
  from space 28672K, 0% used [0x00000000fe400000,0x00000000fe400000,0x0000000100000000)
  to   space 28672K, 0% used [0x00000000fc800000,0x00000000fc800000,0x00000000fe400000)
 ParOldGen       total 175104K, used 174984K [0x00000000f0000000, 0x00000000fab00000, 0x00000000fab00000)
  object space 175104K, 99% used [0x00000000f0000000,0x00000000faae2170,0x00000000fab00000)
 Metaspace       used 2719K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 292K, capacity 386K, committed 512K, reserved 1048576K
```

**-Xmx1g -Xms1g**

```html
C:\Users\DELL\Desktop\data>java -Xmx1g -Xms1g -XX:+PrintGCDetails  GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 261753K->43504K(305664K)] 261753K->85390K(1005056K), 0.0101708 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
...
[GC (Allocation Failure) [PSYoungGen: 154712K->44556K(232960K)] 727351K->651922K(932352K), 0.0076653 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 44556K->0K(232960K)] [ParOldGen: 607365K->338307K(699392K)] 651922K->338307K(932352K), [Metaspace: 2688K->2688K(1056768K)], 0.0348399 secs] [Times: user=0.23 sys=0.00, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 116736K->40798K(232960K)] 455043K->379105K(932352K), 0.0040069 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
...
[GC (Allocation Failure) [PSYoungGen: 157735K->39589K(232960K)] 771417K->689852K(932352K), 0.0073051 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
...
[GC (Allocation Failure) [PSYoungGen: 198223K->47930K(259072K)] 832824K->724514K(958464K), 0.0083861 secs] [Times: user=0.11 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 47930K->0K(259072K)] [ParOldGen: 676583K->364846K(699392K)] 724514K->364846K(958464K), [Metaspace: 2688K->2688K(1056768K)], 0.0343802 secs] [Times: user=0.22 sys=0.00, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 164352K->50877K(254464K)] 529198K->415724K(953856K), 0.0048623 secs] [Times: user=0.09 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 215229K->50457K(265216K)] 580076K->460489K(964608K), 0.0083520 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 228633K->57161K(262144K)] 638665K->511103K(961536K), 0.0084265 secs] [Times: user=0.13 sys=0.00, real=0.01 secs]
执行结束!共生成对象次数:19819
Heap
 PSYoungGen      total 262144K, used 94291K [0x00000000eab00000, 0x0000000100000000, 0x0000000100000000)
  eden space 178176K, 20% used [0x00000000eab00000,0x00000000ecf42728,0x00000000f5900000)
  from space 83968K, 68% used [0x00000000f5900000,0x00000000f90d27f0,0x00000000fab00000)
  to   space 83968K, 0% used [0x00000000fae00000,0x00000000fae00000,0x0000000100000000)
 ParOldGen       total 699392K, used 453941K [0x00000000c0000000, 0x00000000eab00000, 0x00000000eab00000)
  object space 699392K, 64% used [0x00000000c0000000,0x00000000dbb4d6f8,0x00000000eab00000)
 Metaspace       used 2695K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 289K, capacity 386K, committed 512K, reserved 1048576K
```

**-Xmx2g -Xms2g**

```html
C:\Users\DELL\Desktop\data>java -Xmx2g -Xms2g -XX:+PrintGCDetails  GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 524696K->87038K(611840K)] 524696K->147607K(2010112K), 0.0191268 secs] [Times: user=0.05 sys=0.06, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 611838K->87037K(611840K)] 672407K->254776K(2010112K), 0.0253419 secs] [Times: user=0.03 sys=0.16, real=0.03 secs]
...
[GC (Allocation Failure) [PSYoungGen: 309604K->72736K(465920K)] 1221617K->1046306K(1864192K), 0.0139530 secs] [Times: user=0.02 sys=0.08, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 305696K->70722K(465920K)] 1279266K->1099658K(1864192K), 0.0122647 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
执行结束!共生成对象次数:19838
Heap
 PSYoungGen      total 465920K, used 209315K [0x00000000d5580000, 0x0000000100000000, 0x0000000100000000)
  eden space 232960K, 59% used [0x00000000d5580000,0x00000000ddcd8338,0x00000000e3900000)
  from space 232960K, 30% used [0x00000000f1c80000,0x00000000f6190a08,0x0000000100000000)
  to   space 232960K, 0% used [0x00000000e3900000,0x00000000e3900000,0x00000000f1c80000)
 ParOldGen       total 1398272K, used 1028936K [0x0000000080000000, 0x00000000d5580000, 0x00000000d5580000)
  object space 1398272K, 73% used [0x0000000080000000,0x00000000becd21e0,0x00000000d5580000)
 Metaspace       used 2695K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 289K, capacity 386K, committed 512K, reserved 1048576K
```

**-Xmx4g -Xms4g**

```html
C:\Users\DELL\Desktop\data>java -Xmx4g -Xms4g -XX:+PrintGCDetails  GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 1048576K->174584K(1223168K)] 1048576K->238321K(4019712K), 0.0269599 secs] [Times: user=0.08 sys=0.17, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 1223160K->174587K(1223168K)] 1286897K->367947K(4019712K), 0.0348217 secs] [Times: user=0.09 sys=0.16, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 1223163K->174586K(1223168K)] 1416523K->496258K(4019712K), 0.0282635 secs] [Times: user=0.03 sys=0.08, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 1223162K->174580K(1223168K)] 1544834K->621275K(4019712K), 0.0285829 secs] [Times: user=0.14 sys=0.11, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 1223156K->174577K(1223168K)] 1669851K->759392K(4019712K), 0.0295335 secs] [Times: user=0.16 sys=0.09, real=0.03 secs]
执行结束!共生成对象次数:23306
Heap
 PSYoungGen      total 1223168K, used 1127846K [0x000000076ab00000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 1048576K, 90% used [0x000000076ab00000,0x00000007a4ded430,0x00000007aab00000)
  from space 174592K, 99% used [0x00000007aab00000,0x00000007b557c768,0x00000007b5580000)
  to   space 174592K, 0% used [0x00000007b5580000,0x00000007b5580000,0x00000007c0000000)
 ParOldGen       total 2796544K, used 584814K [0x00000006c0000000, 0x000000076ab00000, 0x000000076ab00000)
  object space 2796544K, 20% used [0x00000006c0000000,0x00000006e3b1b930,0x000000076ab00000)
 Metaspace       used 2695K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 289K, capacity 386K, committed 512K, reserved 1048576K
```

**-Xmx8g -Xms8g**

```html
C:\Users\DELL\Desktop\data>java -Xmx8g -Xms8g -XX:+PrintGCDetails  GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 2097664K->315191K(2446848K)] 2097664K->315199K(8039424K), 0.0348899 secs] [Times: user=0.03 sys=0.22, real=0.04 secs]
[GC (Allocation Failure) [PSYoungGen: 2412855K->349182K(2446848K)] 2412863K->375792K(8039424K), 0.0460414 secs] [Times: user=0.06 sys=0.19, real=0.05 secs]
执行结束!共生成对象次数:15470
Heap
 PSYoungGen      total 2446848K, used 433388K [0x0000000715580000, 0x00000007c0000000, 0x00000007c0000000)
  eden space 2097664K, 4% used [0x0000000715580000,0x000000071a7bb640,0x0000000795600000)
  from space 349184K, 99% used [0x00000007aab00000,0x00000007bffffb80,0x00000007c0000000)
  to   space 349184K, 0% used [0x0000000795600000,0x0000000795600000,0x00000007aab00000)
 ParOldGen       total 5592576K, used 26609K [0x00000005c0000000, 0x0000000715580000, 0x0000000715580000)
  object space 5592576K, 0% used [0x00000005c0000000,0x00000005c19fc7a8,0x0000000715580000)
 Metaspace       used 2695K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 289K, capacity 386K, committed 512K, reserved 1048576K
```

通过默认并行收集器结合不同的-Xmx和-Xms参数启动运行对比，堆内存太大和太小都没有很高的吞吐量。选择适当的启动参数是考虑垃圾收集频率和单次垃圾收集时间平衡的结果。

## 使用压测工具（wrk或sb），演练gateway-server-0.0.1-SNAPSHOT.jar示例。 

启动gateway-server-0.0.1-SNAPSHOT.jar项目：

```html
java -Xmx1g -Xms1g -XX:+PrintGCDetails  -jar gateway-server-0.0.1-SNAPSHOT.jar
```

```html
C:\Users\DELL\Desktop\data>java -Xmx1g -Xms1g -XX:+PrintGCDetails  -jar gateway-server-0.0.1-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.4.RELEASE)

[GC (Metadata GC Threshold) [PSYoungGen: 262144K->15223K(305664K)] 262144K->15247K(1005056K), 0.0117297 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Metadata GC Threshold) [PSYoungGen: 15223K->0K(305664K)] [ParOldGen: 24K->14378K(699392K)] 15247K->14378K(1005056K), [Metaspace: 20444K->20444K(1067008K)], 0.0216237 secs] [Times: user=0.17 sys=0.08, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 262144K->15852K(305664K)] 276522K->30303K(1005056K), 0.0087843 secs] [Times: user=0.13 sys=0.00, real=0.01 secs]
[GC (Metadata GC Threshold) [PSYoungGen: 192784K->15792K(305664K)] 207235K->30251K(1005056K), 0.0073549 secs] [Times: user=0.13 sys=0.00, real=0.01 secs]
[Full GC (Metadata GC Threshold) [PSYoungGen: 15792K->0K(305664K)] [ParOldGen: 14458K->20170K(699392K)] 30251K->20170K(1005056K), [Metaspace: 33814K->33814K(1079296K)], 0.0404330 secs] [Times: user=0.16 sys=0.00, real=0.04 secs]
```

启动后，多次GC后稳定。

使用sb压测如下：

```html
C:\Users\DELL>sb -u http://127.0.0.1:8088/api/hello -c 40 -N 20
Starting at 2021/1/16 20:54:03
[Press C to stop the test]
80209   (RPS: 3423.6)
---------------Finished!----------------
Finished at 2021/1/16 20:54:27 (took 00:00:23.5810026)
Status 200:    80213

RPS: 3790.5 (requests/second)
Max: 255ms
Min: 0ms
Avg: 1.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 4ms
  95%   below 8ms
  98%   below 16ms
  99%   below 21ms
99.9%   below 41ms
```

```html
C:\Users\DELL\Desktop\data>java -Xmx1g -Xms1g -XX:+PrintGCDetails  -jar gateway-server-0.0.1-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.0.4.RELEASE)

[GC (Metadata GC Threshold) [PSYoungGen: 262144K->15223K(305664K)] 262144K->15247K(1005056K), 0.0117297 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Metadata GC Threshold) [PSYoungGen: 15223K->0K(305664K)] [ParOldGen: 24K->14378K(699392K)] 15247K->14378K(1005056K), [Metaspace: 20444K->20444K(1067008K)], 0.0216237 secs] [Times: user=0.17 sys=0.08, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 262144K->15852K(305664K)] 276522K->30303K(1005056K), 0.0087843 secs] [Times: user=0.13 sys=0.00, real=0.01 secs]
[GC (Metadata GC Threshold) [PSYoungGen: 192784K->15792K(305664K)] 207235K->30251K(1005056K), 0.0073549 secs] [Times: user=0.13 sys=0.00, real=0.01 secs]
[Full GC (Metadata GC Threshold) [PSYoungGen: 15792K->0K(305664K)] [ParOldGen: 14458K->20170K(699392K)] 30251K->20170K(1005056K), [Metaspace: 33814K->33814K(1079296K)], 0.0404330 secs] [Times: user=0.16 sys=0.00, real=0.04 secs]
=======================================================================================================
压测后，发生了几次YoungGC,因为年轻代空间足够，废弃的对象在回收之后被丢弃(可被覆盖使用)，没有提升到老年代，老年代空间稳定。
=======================================================================================================
[GC (Allocation Failure) [PSYoungGen: 262144K->1649K(305664K)] 282314K->21828K(1005056K), 0.0025447 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 263793K->864K(305664K)] 283972K->21050K(1005056K), 0.0020763 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 263008K->912K(326144K)] 283194K->21106K(1025536K), 0.0022414 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 305040K->912K(326656K)] 325234K->21114K(1026048K), 0.0019540 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 305040K->800K(327680K)] 325242K->21002K(1027072K), 0.0020413 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
```



## (选做)如果自己本地有可以运行的项目，可以按照2的方式进行演练。 

启动本地项目

使用默认参数启动(内存32G),并压测

```html
Java HotSpot(TM) 64-Bit Server VM (25.221-b11) for windows-amd64 JRE (1.8.0_221-b11), built on Jul  4 2019 04:39:29 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 33312884k(22670856k free), swap 38293620k(25146628k free)
CommandLine flags: -XX:-BytecodeVerificationLocal -XX:-BytecodeVerificationRemote -XX:InitialHeapSize=533006144 -XX:+ManagementServer -XX:MaxHeapSize=8528098304 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:TieredStopAtLevel=1 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC 
1.737: [GC (Allocation Failure) [PSYoungGen: 131072K->12657K(152576K)] 131072K->12737K(500736K), 0.0169807 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
1.977: [GC (Metadata GC Threshold) [PSYoungGen: 59319K->9289K(152576K)] 59399K->9377K(500736K), 0.0186526 secs] [Times: user=0.03 sys=0.02, real=0.02 secs] 
1.996: [Full GC (Metadata GC Threshold) [PSYoungGen: 9289K->0K(152576K)] [ParOldGen: 88K->9045K(178176K)] 9377K->9045K(330752K), [Metaspace: 20550K->20548K(1067008K)], 0.0350791 secs] [Times: user=0.06 sys=0.00, real=0.04 secs] 
2.617: [GC (Allocation Failure) [PSYoungGen: 131072K->10856K(152576K)] 140117K->19910K(330752K), 0.0043594 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
...
494.197: [GC (Allocation Failure) [PSYoungGen: 1581888K->336K(1605120K)] 1628416K->46920K(1964544K), 0.0020769 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
495.759: [GC (Allocation Failure) [PSYoungGen: 1581904K->448K(1666560K)] 1628488K->47040K(2025984K), 0.0018936 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

```html
C:\Users\DELL>sb -u  http://localhost:8088/alarm/list  -t C:\Users\DELL\Desktop\post.txt -c 20 -N 20
Starting at 2021/1/18 20:23:19
[Press C to stop the test]
49377   (RPS: 2125.1)
---------------Finished!----------------
Finished at 2021/1/18 20:23:43 (took 00:00:23.3910999)
Status 200:    49390

RPS: 2335.9 (requests/second)
Max: 250ms
Min: 0ms
Avg: 2.3ms

  50%   below 1ms
  60%   below 2ms
  70%   below 2ms
  80%   below 3ms
  90%   below 4ms
  95%   below 6ms
  98%   below 8ms
  99%   below 10ms
99.9%   below 32ms

```

刚启动的时候因为Metadata GC Threshold触发了Full GC，可以设置-XX:MetaspaceSize大小避免。

调整-Xmx和-Xms大小

```
-Xmx4g -Xms4g
```

```html
Java HotSpot(TM) 64-Bit Server VM (25.221-b11) for windows-amd64 JRE (1.8.0_221-b11), built on Jul  4 2019 04:39:29 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 33312884k(22570980k free), swap 38293620k(25606656k free)
CommandLine flags: -XX:-BytecodeVerificationLocal -XX:-BytecodeVerificationRemote -XX:InitialHeapSize=2147483648 -XX:+ManagementServer -XX:MaxHeapSize=2147483648 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:TieredStopAtLevel=1 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC 
1.337: [GC (Metadata GC Threshold) [PSYoungGen: 220474K->17312K(611840K)] 220474K->17392K(2010112K), 0.0171715 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
1.354: [Full GC (Metadata GC Threshold) [PSYoungGen: 17312K->0K(611840K)] [ParOldGen: 80K->16710K(1398272K)] 17392K->16710K(2010112K), [Metaspace: 20566K->20564K(1067008K)], 0.0362508 secs] [Times: user=0.06 sys=0.02, real=0.04 secs] 
2.674: [GC (Metadata GC Threshold) [PSYoungGen: 331791K->22827K(611840K)] 348501K->39545K(2010112K), 0.0137993 secs] [Times: user=0.09 sys=0.00, real=0.01 secs] 
2.688: [Full GC (Metadata GC Threshold) [PSYoungGen: 22827K->0K(611840K)] [ParOldGen: 16718K->28005K(1398272K)] 39545K->28005K(2010112K), [Metaspace: 33648K->33648K(1079296K)], 0.0342784 secs] [Times: user=0.20 sys=0.00, real=0.03 secs] 
4.428: [GC (Allocation Failure) [PSYoungGen: 524800K->26115K(611840K)] 552805K->54136K(2010112K), 0.0123798 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
4.941: [GC (Allocation Failure) [PSYoungGen: 550915K->48583K(611840K)] 578936K->76613K(2010112K), 0.0161618 secs] [Times: user=0.13 sys=0.00, real=0.02 secs] 
32.980: [GC (Metadata GC Threshold) [PSYoungGen: 365467K->28391K(611840K)] 393496K->56428K(2010112K), 0.0108217 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
32.991: [Full GC (Metadata GC Threshold) [PSYoungGen: 28391K->0K(611840K)] [ParOldGen: 28037K->38820K(1398272K)] 56428K->38820K(2010112K), [Metaspace: 56303K->56243K(1099776K)], 0.1057003 secs] [Times: user=0.39 sys=0.02, real=0.11 secs] 
33.888: [GC (Allocation Failure) [PSYoungGen: 524800K->3843K(646656K)] 563620K->42679K(2044928K), 0.0032704 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
...
52.164: [GC (Allocation Failure) [PSYoungGen: 668544K->224K(686080K)] 709918K->41598K(2084352K), 0.0017702 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
52.757: [GC (Allocation Failure) [PSYoungGen: 672480K->320K(685056K)] 713854K->41694K(2083328K), 0.0018807 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

```html
C:\Users\DELL>sb -u  http://localhost:8088/alarm/list  -t C:\Users\DELL\Desktop\post.txt -c 20 -N 20
Starting at 2021/1/18 20:35:38
[Press C to stop the test]
54369   (RPS: 2315.1)
---------------Finished!----------------
Finished at 2021/1/18 20:36:02 (took 00:00:23.5399961)
Status 200:    54375

RPS: 2583.6 (requests/second)
Max: 291ms
Min: 0ms
Avg: 1.8ms

  50%   below 1ms
  60%   below 1ms
  70%   below 2ms
  80%   below 2ms
  90%   below 3ms
  95%   below 5ms
  98%   below 6ms
  99%   below 7ms
99.9%   below 18ms
```

YoungGC次数因为年轻代大小减小变得频繁，RPS反而提升了一些。

使用G1收集器(-Xmx4g,-Xms4g)

```html
Java HotSpot(TM) 64-Bit Server VM (25.221-b11) for windows-amd64 JRE (1.8.0_221-b11), built on Jul  4 2019 04:39:29 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 33312884k(22541684k free), swap 38293620k(25509720k free)
CommandLine flags: -XX:-BytecodeVerificationLocal -XX:-BytecodeVerificationRemote -XX:InitialHeapSize=4294967296 -XX:+ManagementServer -XX:MaxHeapSize=4294967296 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:TieredStopAtLevel=1 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation 
1.360: [GC pause (Metadata GC Threshold) (young) (initial-mark), 0.0142078 secs]
   [Parallel Time: 5.7 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 1360.6, Avg: 1360.6, Max: 1360.6, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.8, Avg: 1.2, Max: 1.4, Diff: 0.6, Sum: 9.7]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 1.2, Max: 4.2, Diff: 4.2, Sum: 9.3]
      [Object Copy (ms): Min: 0.0, Avg: 3.1, Max: 4.5, Diff: 4.5, Sum: 24.7]
      [Termination (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.4]
         [Termination Attempts: Min: 1, Avg: 1.1, Max: 2, Diff: 1, Sum: 9]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.1, Sum: 0.4]
      [GC Worker Total (ms): Min: 5.5, Avg: 5.6, Max: 5.6, Diff: 0.1, Sum: 44.6]
      [GC Worker End (ms): Min: 1366.2, Avg: 1366.2, Max: 1366.2, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.1 ms]
   [Other: 8.3 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 7.8 ms]
      [Ref Enq: 0.1 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.1 ms]
   [Eden: 158.0M(204.0M)->0.0B(186.0M) Survivors: 0.0B->18.0M Heap: 157.0M(4096.0M)->16.4M(4096.0M)]
 [Times: user=0.00 sys=0.02, real=0.02 secs] 
1.375: [GC concurrent-root-region-scan-start]
1.380: [GC concurrent-root-region-scan-end, 0.0052352 secs]
1.380: [GC concurrent-mark-start]
1.381: [GC concurrent-mark-end, 0.0009881 secs]
1.381: [GC remark 1.381: [Finalize Marking, 0.0000953 secs] 1.381: [GC ref-proc, 0.0002875 secs] 1.382: [Unloading, 0.0017825 secs], 0.0023905 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
1.384: [GC cleanup 18M->18M(4096M), 0.0008304 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
2.146: [GC pause (G1 Evacuation Pause) (young), 0.0135492 secs]
   [Parallel Time: 6.8 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 2146.4, Avg: 2146.5, Max: 2146.5, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 0.1, Avg: 0.4, Max: 2.0, Diff: 1.9, Sum: 3.4]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.3, Max: 1.4, Diff: 1.4, Sum: 2.0]
      [Object Copy (ms): Min: 4.5, Avg: 5.8, Max: 6.3, Diff: 1.7, Sum: 46.3]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
         [Termination Attempts: Min: 1, Avg: 96.4, Max: 125, Diff: 124, Sum: 771]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 6.5, Avg: 6.5, Max: 6.6, Diff: 0.1, Sum: 51.9]
      [GC Worker End (ms): Min: 2153.0, Avg: 2153.0, Max: 2153.0, Diff: 0.0]
   [Code Root Fixup: 0.1 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.6 ms]
   [Other: 6.0 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 5.4 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.3 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.1 ms]
   [Eden: 186.0M(186.0M)->0.0B(358.0M) Survivors: 18.0M->26.0M Heap: 202.4M(4096.0M)->25.0M(4096.0M)]
 [Times: user=0.13 sys=0.00, real=0.01 secs] 
...
4.593: [GC pause (G1 Evacuation Pause) (young), 0.0324300 secs]
   [Parallel Time: 24.3 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 4593.6, Avg: 4593.7, Max: 4593.7, Diff: 0.1]
      [Ext Root Scanning (ms): Min: 1.4, Avg: 1.8, Max: 3.6, Diff: 2.2, Sum: 14.0]
      [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
      [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.2]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.9, Max: 2.2, Diff: 2.2, Sum: 7.0]
      [Object Copy (ms): Min: 20.1, Avg: 21.3, Max: 22.2, Diff: 2.1, Sum: 170.7]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.2]
         [Termination Attempts: Min: 1, Avg: 157.8, Max: 204, Diff: 203, Sum: 1262]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.2]
      [GC Worker Total (ms): Min: 24.0, Avg: 24.0, Max: 24.1, Diff: 0.1, Sum: 192.2]
      [GC Worker End (ms): Min: 4617.7, Avg: 4617.7, Max: 4617.7, Diff: 0.0]
   [Code Root Fixup: 0.3 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.2 ms]
   [Other: 7.7 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 6.9 ms]
      [Ref Enq: 0.1 ms]
      [Redirty Cards: 0.2 ms]
      [Humongous Register: 0.0 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.3 ms]
   [Eden: 596.0M(596.0M)->0.0B(520.0M) Survivors: 24.0M->58.0M Heap: 620.0M(4096.0M)->58.0M(4096.0M)]
 [Times: user=0.13 sys=0.02, real=0.03 secs] 
...
40.451: [GC pause (G1 Evacuation Pause) (young), 0.0057395 secs]
   [Parallel Time: 4.3 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 40450.8, Avg: 40450.8, Max: 40450.9, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.3, Avg: 0.4, Max: 1.1, Diff: 0.9, Sum: 3.4]
      [Update RS (ms): Min: 0.0, Avg: 0.6, Max: 0.9, Diff: 0.9, Sum: 4.8]
         [Processed Buffers: Min: 0, Avg: 2.6, Max: 6, Diff: 6, Sum: 21]
      [Scan RS (ms): Min: 0.3, Avg: 0.4, Max: 0.5, Diff: 0.2, Sum: 3.2]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.4, Diff: 0.4, Sum: 1.1]
      [Object Copy (ms): Min: 2.5, Avg: 2.6, Max: 2.7, Diff: 0.3, Sum: 21.0]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 8]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.1, Diff: 0.0, Sum: 0.3]
      [GC Worker Total (ms): Min: 4.2, Avg: 4.2, Max: 4.3, Diff: 0.0, Sum: 33.9]
      [GC Worker End (ms): Min: 40455.1, Avg: 40455.1, Max: 40455.1, Diff: 0.0]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.4 ms]
   [Other: 1.0 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.2 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.5 ms]
   [Eden: 2442.0M(2442.0M)->0.0B(2444.0M) Survivors: 14.0M->12.0M Heap: 2496.7M(4096.0M)->52.7M(4096.0M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs] 
43.066: [GC pause (G1 Evacuation Pause) (young), 0.0058123 secs]
   [Parallel Time: 4.5 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 43066.3, Avg: 43066.4, Max: 43066.4, Diff: 0.0]
      [Ext Root Scanning (ms): Min: 0.3, Avg: 0.4, Max: 1.2, Diff: 0.9, Sum: 3.3]
      [Update RS (ms): Min: 0.0, Avg: 0.7, Max: 0.9, Diff: 0.9, Sum: 5.6]
         [Processed Buffers: Min: 0, Avg: 3.1, Max: 11, Diff: 11, Sum: 25]
      [Scan RS (ms): Min: 0.2, Avg: 0.3, Max: 0.4, Diff: 0.2, Sum: 2.3]
      [Code Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.4, Diff: 0.4, Sum: 1.2]
      [Object Copy (ms): Min: 2.7, Avg: 2.8, Max: 3.0, Diff: 0.3, Sum: 22.7]
      [Termination (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
         [Termination Attempts: Min: 1, Avg: 1.3, Max: 2, Diff: 1, Sum: 10]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.1, Max: 0.1, Diff: 0.1, Sum: 0.4]
      [GC Worker Total (ms): Min: 4.4, Avg: 4.4, Max: 4.4, Diff: 0.0, Sum: 35.5]
      [GC Worker End (ms): Min: 43070.8, Avg: 43070.8, Max: 43070.8, Diff: 0.1]
   [Code Root Fixup: 0.0 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.3 ms]
   [Other: 0.9 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.1 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.1 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.4 ms]
   [Eden: 2444.0M(2444.0M)->0.0B(2444.0M) Survivors: 12.0M->12.0M Heap: 2496.7M(4096.0M)->52.7M(4096.0M)]
 [Times: user=0.00 sys=0.00, real=0.00 secs] 
```

```html
C:\Users\DELL>sb -u  http://localhost:8088/alarm/list  -t C:\Users\DELL\Desktop\post.txt -c 20 -N 20
Starting at 2021/1/18 20:47:00
[Press C to stop the test]
51177   (RPS: 2170.9)
---------------Finished!----------------
Finished at 2021/1/18 20:47:23 (took 00:00:23.7328483)
Status 200:    51183

RPS: 2419.8 (requests/second)
Max: 235ms
Min: 0ms
Avg: 2ms

  50%   below 1ms
  60%   below 1ms
  70%   below 2ms
  80%   below 3ms
  90%   below 4ms
  95%   below 5ms
  98%   below 6ms
  99%   below 8ms
99.9%   below 27ms
```

使用CMS收集器(-Xmx4g,-Xms4g)

```html
Java HotSpot(TM) 64-Bit Server VM (25.221-b11) for windows-amd64 JRE (1.8.0_221-b11), built on Jul  4 2019 04:39:29 by "java_re" with MS VC++ 10.0 (VS2010)
Memory: 4k page, physical 33312884k(24487448k free), swap 38293620k(24905788k free)
CommandLine flags: -XX:-BytecodeVerificationLocal -XX:-BytecodeVerificationRemote -XX:InitialHeapSize=4294967296 -XX:+ManagementServer -XX:MaxHeapSize=4294967296 -XX:MaxNewSize=697933824 -XX:MaxTenuringThreshold=6 -XX:NewSize=697933824 -XX:OldPLABSize=16 -XX:OldSize=1395867648 -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:TieredStopAtLevel=1 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC 
2.233: [GC (CMS Initial Mark) [1 CMS-initial-mark: 0K(3512768K)] 479903K(4126208K), 0.0565759 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
2.290: [CMS-concurrent-mark-start]
2.291: [CMS-concurrent-mark: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2.291: [CMS-concurrent-preclean-start]
2.294: [CMS-concurrent-preclean: 0.003/0.003 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
2.294: [CMS-concurrent-abortable-preclean-start]
2.591: [GC (Allocation Failure) 2.591: [ParNew: 545344K->37187K(613440K), 0.0242938 secs] 545344K->37187K(4126208K), 0.0243368 secs] [Times: user=0.13 sys=0.02, real=0.02 secs] 
3.646: [CMS-concurrent-abortable-preclean: 0.916/1.352 secs] [Times: user=2.47 sys=0.26, real=1.35 secs] 
3.646: [GC (CMS Final Remark) [YG occupancy: 312093 K (613440 K)]3.646: [Rescan (parallel) , 0.0290906 secs]3.675: [weak refs processing, 0.0000212 secs]3.675: [class unloading, 0.0033895 secs]3.679: [scrub symbol table, 0.0060336 secs]3.685: [scrub string table, 0.0003532 secs][1 CMS-remark: 0K(3512768K)] 312093K(4126208K), 0.0398716 secs] [Times: user=0.14 sys=0.00, real=0.04 secs] 
3.686: [CMS-concurrent-sweep-start]
3.686: [CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
3.686: [CMS-concurrent-reset-start]
3.702: [CMS-concurrent-reset: 0.016/0.016 secs] [Times: user=0.01 sys=0.02, real=0.02 secs] 
4.261: [GC (Allocation Failure) 4.261: [ParNew: 582531K->33014K(613440K), 0.0530462 secs] 582531K->46184K(4126208K), 0.0530921 secs] [Times: user=0.25 sys=0.05, real=0.05 secs] 
4.853: [GC (Allocation Failure) 4.853: [ParNew: 578358K->56145K(613440K), 0.0194962 secs] 591528K->69314K(4126208K), 0.0195475 secs] [Times: user=0.13 sys=0.00, real=0.02 secs] 
...
37.938: [GC (Allocation Failure) 37.938: [ParNew: 545514K->258K(613440K), 0.0043565 secs] 587765K->42509K(4126208K), 0.0044244 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
```

```html
C:\Users\DELL>sb -u  http://localhost:8088/alarm/list  -t C:\Users\DELL\Desktop\post.txt -c 20 -N 20
Starting at 2021/1/18 20:54:19
[Press C to stop the test]
50449   (RPS: 2172.7)
---------------Finished!----------------
Finished at 2021/1/18 20:54:43 (took 00:00:23.3478709)
Status 200:    50449

RPS: 2388.7 (requests/second)
Max: 256ms
Min: 0ms
Avg: 2.2ms

  50%   below 1ms
  60%   below 2ms
  70%   below 2ms
  80%   below 3ms
  90%   below 4ms
  95%   below 5ms
  98%   below 7ms
  99%   below 8ms
99.9%   below 25ms
```

老年代并发收集时间短。

## (必做)根据上述自己对于1和2的演示，写一段对于不同GC和堆内存的总结，提交到 github。

堆内存越大，收集时间会变长，堆内存小了会造成内存不够使用，收集次数频繁。堆内存是综合垃圾收集时间和垃圾收集频率平衡的结果。

垃圾收集器是按照串行，并行和并发的历程发展的。

**串行收集器**，一个收集线程完成垃圾收集工作，并且STW。

**并行收集器**，使用多线程完成垃圾收集，可以充分利用多核特性，大幅降低gc时间。提升吞吐量需要以系统响应变慢和更多的内存消耗作为代价。

**并发收集器(CMS)**,老年代垃圾回收时除了初始标记和最终标记STW，其它时候不暂停用户线程。

**并发收集器(G1)**,多核，JVM占用较大内存的应用；想要更可控，可预期的GC停顿周期；运行过程中会产生大量内存碎片。

# 第二次课(20210117)

## （可选）运行课上的例子，以及 Netty 的例子，分析相关现象。 

### HttpServer01

```html
C:\Users\DELL>sb -u http://localhost:8801 -c 40 -N 20
Starting at 2021/1/19 19:28:06
[Press C to stop the test]
56101   (RPS: 2400.6)
---------------Finished!----------------
Finished at 2021/1/19 19:28:30 (took 00:00:23.5513277)
Status 200:    35161
Status 303:    20949

RPS: 2649.5 (requests/second)
Max: 101ms
Min: 0ms
Avg: 3.8ms

  50%   below 1ms
  60%   below 3ms
  70%   below 5ms
  80%   below 7ms
  90%   below 11ms
  95%   below 15ms
  98%   below 21ms
  99%   below 25ms
99.9%   below 50ms
```

每个连接都由主线程(测试main线程)处理，每次只能处理一个连接。没有连接时，主线程阻塞。

### HttpServer02

```html
C:\Users\DELL>sb -u http://localhost:8802 -c 40 -N 20
Starting at 2021/1/19 19:37:16
[Press C to stop the test]
62685   (RPS: 2679.1)
---------------Finished!----------------
Finished at 2021/1/19 19:37:39 (took 00:00:23.5679710)
Status 200:    60779
Status 303:    1915

RPS: 2962.7 (requests/second)
Max: 122ms
Min: 0ms
Avg: 3.4ms

  50%   below 1ms
  60%   below 2ms
  70%   below 4ms
  80%   below 6ms
  90%   below 10ms
  95%   below 13ms
  98%   below 18ms
  99%   below 21ms
99.9%   below 39ms
```

RPS由于单线程Server，主线程在获取Socket后，开启异步多线程处理，这样能够迅速响应后面的连接，能够同时处理多个用户请求。由于系统的资源是有限的，那么创建线程的数量有限，同时处理用户数也有限。

### HttpServer03

```html
C:\Users\DELL>sb -u http://localhost:8803 -c 40 -N 20
Starting at 2021/1/19 19:46:07
[Press C to stop the test]
70021   (RPS: 2996.4)
---------------Finished!----------------
Finished at 2021/1/19 19:46:31 (took 00:00:23.5400859)
Status 200:    48667
Status 303:    21362

RPS: 3308.7 (requests/second)
Max: 111ms
Min: 0ms
Avg: 2.4ms

  50%   below 0ms
  60%   below 0ms
  70%   below 2ms
  80%   below 4ms
  90%   below 8ms
  95%   below 12ms
  98%   below 18ms
  99%   below 22ms
99.9%   below 40ms
```

使用线程池优化，可以达到线程复用的目的，这样可以减少线程的反复创建和销毁带来的开销。

### NettyHttpServer

```html
C:\Users\DELL>sb -u http://localhost:8808/test -c 40 -N 20
Starting at 2021/1/19 19:57:56
[Press C to stop the test]
126780  (RPS: 5417.7)
---------------Finished!----------------
Finished at 2021/1/19 19:58:19 (took 00:00:23.5680696)
Status 200:    126784

RPS: 5990.8 (requests/second)
Max: 168ms
Min: 0ms
Avg: 0.4ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 2ms
  98%   below 6ms
  99%   below 9ms
99.9%   below 22ms
```

Netty基于JDK的NIO，有更高的RPS。bossGroup中的EventLoop处理连接请求，当有用户连接后，为其创建socketChannel并且与workerGroup中的EventLoop绑定，处理读写事件。这样就实现了单个线程可以同时处理很多个请求，性能得到极大提升。

## 写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801，代码提交到 Github。

答案链接源码(https://github.com/maolikui/JAVA-01/tree/main/Week_02)