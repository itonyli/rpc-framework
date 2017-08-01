**Talk is cheap. Show me the code**.

总想着写个大点的工程，但是架构能力有限。还有好多TODO没完成，感兴趣的化可以fork走。（小菜刚接触dubbo不久，一个月左右～）

对比了一下性能：
                    dubbo   rpc-framework
     初始化时间      2000左右    5000左右 
     传输10个对象    平均2ms      平均8ms

（单机测试，可能会出现差异）

优化方向： 
    （1）序列化， 数据压缩 
    （2）注册中心，provider和consuer节点的watch 
    （3）注解的加载方式（一开始想剔除spring，自定义classloader） 
    （4）socket直传会比netty快
    （5）日志优化
    （6）异常处理

最后，Dubbo架构的还是很NB的，小道消息最近阿里重新开始维护Dubbo啦，Happy！


测试须知：netty通信端口为8888，zk地址：127.0.0.1:2181
    demo启动顺序
        （1）com.github.itonyli.demo.SimpleProvider
        （2）com.github.itonyli.demo.SimpleConsumer


Good Idea ToMail：429284840@qq.com
