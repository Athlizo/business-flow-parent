# 项目地址
- 码云：[https://git.oschina.net/null_584_3382/business-flow-parent](https://git.oschina.net/null_584_3382/business-flow-parent)
- github:[https://github.com/Athlizo/business-flow-parent](https://github.com/Athlizo/business-flow-parent)
# 先通俗的介绍一下框架
该框架的灵感来自于现实中的公交系统。公交系统的中最重要的几个元素，及其对工作流框架的对应：
- 乘客：对应工作流框架的中的数据（data）
- 公交车：数据的载体，
- 车站：一个车站可以看成工作流中的一个节点，负责处理“公交车”上的“乘客”。
- 线路：由哪些节点组成一个完整的工作流的处理链  
是不是感觉整个公交系统就是一个庞大的工作流处理网，每时每刻都公交车从车站出发，到达一个车站，上下乘客又开往下一个车站(当然前提是不出事故（exception））。  

# 框架中的一些重要接口
## BusContext
保存一个业务处理逻辑的上下文环境。
## Bus
一个Bus是保存一次业务流程的上下文环境，业务的起始节点、抛异常的时候怎么处理等等。一个业务流都会新建一个bus，让后顺着一个一个节点进行处理。
## Station
Station为一个业务流（处理链）中的一个单独的节点。这个节点应该是只依赖于Bus中的上下文环境，根据bus的上下文环境进行处理，并且把处理后的结果（如果有）也放入bus的上下文环境中，供下游的节点使用。
例如下面就是一个Station，从Bus上下文中获取maxValue和minValue,如果之间的差小于10则设置路由的key为OK(Routing根据这个进行路由）
## Routing
由于Station之间并没有直接关联，因此Routing负责连接各个Station，每个Station都有一个Routing来负责处理bus到底哪个Station，即可以动态的决定Bus的下一个Station
#如何使用
# 举例子
## Station
一个Station就是一个Spring容器管理的Bean（实现了com.lizo.busflow.station.Station接口）。一个station应该是独立的，有一定通用性的业务处理类，例如一个参数检查器，ip控制或一个相对对立的业务逻辑等等。
```
public class GetDiff implements Station {
    public void abstractCalculate(@BusParameter("maxValue") int a, @BusParameter("minValue") int b, Bus bus) {
        if (Math.abs(a - b) < 10) {
            bus.setRoutingKey("ok");
        } else {
            bus.setRoutingKey("no");
        }
    }
    @Override
    public String getName() {
        return null;
    }
}
```


## Routing
Routing的一定是要一个对应的Station的，例如可以在xml配置中，根据路由的key为进行选择下一个处理的Station
```
  <!--这个是一个Station-->
   <bean id="getDiff" class="com.lizo.demo.station.GetDiff"></bean>

<!--这个是一个Routing，包含了对应的Station Bean-->
    <bf:stop id="getDiffStop" ref="getDiff" method="abstractCalculate">
        <bf:routing value="ok" to="soutOutOkStop"/>
        <bf:routing value="no" to="soutOutNoStop"/>
    </bf:stop>
```
注意，
- **后面所说的Station默认是指包含了Routing的Bean（<bf:stop>标签），并不是Station那个Bean**
- 需要ref制定一个Spring bean，使用method制定是由那个method来处理。
- 默认会使用BusContext的key对应方法的参数名来自动注入，如果有特殊需要，可以使用@BusParameter注解，指定BusContext对应的key，是否是必须（默认是必须的，设置为非必须，会注入默认值）。  



## Bus
一个完整Bus在xml中定义，如下：

```
<bf:bus id="testBus" start="findMaxStop" maxPath="1000"  exception="exceptionStation" finish="endStation" class="xxx.xxx.xxx.myBus"/>
```
其中: 
- id: 对应的一个Spring Bean的name
- start: 对应工作流开始Routing
- maxPath:规定了bus如果处理的次数大于这个数就会跑出异常（防止死循环）
- exception:指定当发送异常的时候由哪个Station进行处理，例如一个打错误日志的Station
- finish:表示当整个流程处理完以后会由哪个Station最最后处理
- class:制定bus的类型，如果为空就使用默认的com.lizo.busflow.bus.DefaultBus    

# 看个DEMO
现在有一个业务需求，需要做以下处理
1. 输入一个整型的list
2. 找出最大值和最小值
3. 如果最大值和最小值的差大于10输出“no”，否则输入“ok”  、
当然真实项目中的业务流程不会这么简单，只是这里使用这个做个例子

## 第一步 编写独立的Station
```
<bean id="findMax" class="com.lizo.demo.station.FindMax"/>
    <bean id="findMin" class="com.lizo.demo.station.FindMin"/>
    <bean id="soutOutOk" class="com.lizo.demo.station.SoutOutOk"/>
    <bean id="soutOutNo" class="com.lizo.demo.station.SoutOutNo"/>
    <bean id="getDiff" class="com.lizo.demo.station.GetDiff">
```
例如getDiff的核心代码如下：
```
public class GetDiff implements Station {

    public void abstractCalculate(@BusParameter("maxValue") int a, @BusParameter("minValue") int b, BusContext busContext) {

        if (Math.abs(a - b) < 10) {
            busContext.setRoutingKey("ok");
        } else {
            busContext.setRoutingKey("no");
        }
    }

    @Override
    public String getName() {
        return null;
    }

}
```
## 把他们串起来吧
```
    <bf:stop id="findMaxStop" ref="findMax" method="doBusiness">
        <bf:routing to="findMinStop"/>
    </bf:stop>

    <bf:stop id="findMinStop" ref="findMin" method="doBusiness">
        <bf:routing to="getDiffStop"/>
    </bf:stop>

    <bf:stop id="getDiffStop" ref="getDiff" method="abstractCalculate">
        <bf:routing value="ok" to="soutOutOkStop"/>
        <bf:routing value="no" to="soutOutNoStop"/>
    </bf:stop>

    <bf:stop id="soutOutOkStop" ref="soutOutOk" method="printOk"/>

    <bf:stop id="soutOutNoStop" ref="soutOutNo" method="printNo"/>
```
## 创建一个bus，开车吧司机
```
<bf:bus id="testBus" start="findMaxStop" />
```
## 运行demo
```
public class DemoApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:bus-config.xml");

        Bus testBus = BusFactory.createNewBus("testBus");
        List<Integer> input = Arrays.asList(5, 7, 1, 0, 1, 3, 4, 5, 6, 4);
        testBus.putContext("intList", input);
        testBus.run();


        testBus = BusFactory.createNewBus("testBus");
        input = Arrays.asList(52, 7, 1, -10, 1, 3, 4, 5, 6, 4);
        testBus.putContext("intList", input);
        testBus.run();
    }
}
```