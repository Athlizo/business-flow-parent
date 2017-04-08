# business-flow-parent
a simple FrameWork of work-flower
## Bus
a bus is contain BusContest which is store “Data”
## Station
a station can “accept” buses, get Data from bus,and put new Data into bus.
## Routing
every bus have a routing Key> according to the key Routing can select Station which bus next arrive to.
# example
we want:
* input list or number
* find Maximum and Minimum
* write file
* The difference between the two numbers is less than 10, print ok , else print no
xml config file can define Routing
```
 <bean id="findMax" class="com.lizo.demo.station.FindMax"/>
    <bean id="findMin" class="com.lizo.demo.station.FindMin"/>
    <bean id="soutOutOk" class="com.lizo.demo.station.SoutOutOk"/>
    <bean id="soutOutNo" class="com.lizo.demo.station.SoutOutNo"/>
    <bean id="getDiff" class="com.lizo.demo.station.GetDiff">
        <property name="numA" value="maxValue"/>
        <property name="numB" value="minValue"/>
    </bean>

    <bean id="fileWrite" class="com.lizo.demo.station.FileWrite">
        <property name="filePath" value="out.txt"/>
        <property name="writeDataFormContext">
            <util:list>
                <value>maxValue</value>
                <value>minValue</value>
            </util:list>
        </property>
    </bean>
    <bean id="end" class="com.lizo.demo.station.TestEndStation"/>

    <bf:busFactory/>

    <bf:bus id="testBus" start="findMaxStop" record="false" finish="end"/>

    <bf:stop id="findMaxStop" ref="findMax">
        <bf:routing to="findMinStop"/>
    </bf:stop>

    <bf:stop id="findMinStop" ref="findMin">
        <bf:routing to="fileWriteStop"/>
    </bf:stop>

    <bf:stop id="fileWriteStop" ref="fileWrite">
        <bf:routing to="getDiffStop"/>
    </bf:stop>

    <bf:stop id="getDiffStop" ref="getDiff">
        <bf:routing value="ok" to="soutOutOkStop"/>
        <bf:routing value="no" to="soutOutNoStop"/>
    </bf:stop>

    <bf:stop id="soutOutOkStop" ref="soutOutOk">
    </bf:stop>

    <bf:stop id="soutOutNoStop" ref="soutOutNo">
    </bf:stop>

```
 then we can do:
 
```
Bus testBus = BusFactory.createNewBus("testBus");
List<Integer> input = Arrays.asList(5, 7, 1, 0, 1, 3, 4, 5, 6, 4);
testBus.putContext("intList", input);
testBus.run();
```

detail see business-flow-demo in project
