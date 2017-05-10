# business-flow-parent
一个基于Spring的轻量级的工作流框架
## Bus
一个Bus是保存一次业务流程的上下文环境
## Station
Station可以理解为一个业务流（处理链）中的一个单独的节点。这个节点应该是只依赖于Bus中的上下文环境，根据bus的上下文环境进行处理，并且把处理后的结果（如果有）也放入bus的上下文环境中，供下游的节点使用。
## Routing
由于Station之间并没有直接关联，因此Routing负责连接各个Station，每个Station都有一个Routing来负责处理bus到底哪个Station。
#如何使用

## Station
一个Station就是一个Spring容器管理的Bean（实现了com.lizo.busflow.station.Station接口）。一个station应该是独立的，有一定通用性的业务处理类，例如一个参数检查器，ip控制等等。
例如下面就是一个Station，从Bus上下文中获取maxValue和minValue,如果之间的差小于10则设置路由的key为OK(Routing根据这个进行路由）
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

<!--这个是一个Station-->
    <bf:stop id="getDiffStop" ref="getDiff" method="abstractCalculate">
        <bf:routing value="ok" to="soutOutOkStop"/>
        <bf:routing value="no" to="soutOutNoStop"/>
    </bf:stop>
```
## bus
一个bus的定义可以理解为定义了一个业务流程
```
<bf:bus id="testBus" start="findMaxStop" maxPath="1000" record="false"  exception="exceptionStation" finish="endStation"/>
```
其中: start确定了业务开始的Station，maxPath规定了bus如果处理的次数大于这个数就会跑出异常（防止死循环），record为true表示会为bus经过的每个Station记录一个上下文环境的快照，exception指定当发送异常的时候由哪个Station进行处理，例如一个打错误日志的Station，finish表示当整个流程处理完以后会由哪个Station最最后处理。

## 2017-5-10 更新功能
更新一：对于参数注解@BusParameter，新增require字段，默认为false，如果为true则会填充默认值（非原生类为null，原生类型则为默认值）
```
public void doBusiness(List<Integer> intList, @BusParameter(value = "test", require = false) char test, Bus bus) {
        if (intList.size() == 0) {
            return;
        }
        int min = intList.get(0);
        for (Integer integer : intList) {
            if (integer < min) {
                min = integer;
            }
        }
        bus.putContext(FindMinKey, min);
    }
```
更新二：把bus 和 busContext 抽象接口，允许对这2个类进行扩展
```
public interface Bus {
    /**
     * 获取bus上下文环境
     * @return
     */
    BusContext getBusContext();

    /**
     * 异常处理
     * @param e
     */
    void dealExcpetion(Exception e);

    /**
     * 对于每个Station在调用真正的Station业务逻辑之前进行操作
     * @param StationRoutingWrap 包含Station具体业务逻辑和Routing信息
     * @throws Exception
     */
    void arrive(StationRoutingWrap StationRoutingWrap) throws Exception;

    /**
     * 加入上下文环境
     * @param key
     * @param input
     */
    void putContext(String key, Object input);

    /**
     * 业务开始
     * @return
     */
    BusContext run();

    /**
     * 设置BusContext
     * @param busContext
     */
    void setBusContext(BusContext busContext);
}
```

```
public interface BusContext {


    /**
     * 根据key从BusContext中获取value
     *
     * @param key
     * @return
     */
    Object getValue(String key);

    /**
     * 新增一个key value到 context中（默认覆盖）
     *
     * @param key
     * @param value
     */
    void put(String key, Object value);

    /**
     * 设置路由key
     *
     * @param key
     */
    void setRoutingKey(String key);

    /**
     * 获取路由信息
     * 注意，如果一个Station没有设置，则为上一个Station的设置的值(如果上一个Station也没设置，以此类推，否则为null)
     *
     * @return
     */
    String getRoutingKey();

    /**
     * 保存运行过程中exception
     *
     * @param e
     */
    void holderException(Exception e);
}
```


对 bus 进行扩展，则在xml配置中指定自定义bus的类(class属性）
```
 <bf:bus id="testBus" start="findMaxStop" record="false" finish="end" class="com.lizo.busflow.bus.DefaultBus"/>
```
由于busContext抽象为了接口，可以set自定义的BusContext