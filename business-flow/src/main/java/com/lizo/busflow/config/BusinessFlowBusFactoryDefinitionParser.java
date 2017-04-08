package com.lizo.busflow.config;

import com.lizo.busflow.bus.BusFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by lizhou on 2017/3/14/014.
 */
public class BusinessFlowBusFactoryDefinitionParser implements BeanDefinitionParser {
    public BeanDefinition parse(Element element, ParserContext parserContext) {


        RootBeanDefinition bus = new RootBeanDefinition();
        bus.setBeanClass(BusFactory.class);

        BeanDefinitionHolder holder = new BeanDefinitionHolder(bus, "BusFactory");
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, parserContext.getRegistry());
        return bus;
    }


}
