package com.lizo.busflow.config;

import com.lizo.busflow.bus.Bus;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import static org.springframework.beans.factory.xml.BeanDefinitionParserDelegate.ID_ATTRIBUTE;

/**
 * Created by lizhou on 2017/3/14/014.
 */
public class BusinessFlowBusDefinitionParser implements BeanDefinitionParser {
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute(ID_ATTRIBUTE);
        String start = element.getAttribute("start");
        String maxPath = element.getAttribute("maxPath");
        String exception = element.getAttribute("exception");
        String finish = element.getAttribute("finish");

        RootBeanDefinition bus = new RootBeanDefinition();
        bus.setBeanClass(Bus.class);
        //保证每次获取的都是新的对象
        bus.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);

        RuntimeBeanReference startBean = new RuntimeBeanReference(start);
        bus.getPropertyValues().add("start", startBean);

        bus.getPropertyValues().add("maxPath", Integer.valueOf(maxPath));

        if (!StringUtils.isEmpty(exception)) {
            RuntimeBeanReference exceptionBean = new RuntimeBeanReference(exception);
            bus.getPropertyValues().add("exception", exceptionBean);
        }

        if (!StringUtils.isEmpty(finish)) {
            RuntimeBeanReference finishBean = new RuntimeBeanReference(finish);
            bus.getPropertyValues().add("finish", finishBean);
        }


        BeanDefinitionHolder holder = new BeanDefinitionHolder(bus, id);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, parserContext.getRegistry());
        return bus;
    }


}
