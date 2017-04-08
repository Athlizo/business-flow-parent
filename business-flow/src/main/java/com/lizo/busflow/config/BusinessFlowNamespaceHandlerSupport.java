package com.lizo.busflow.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by lizhou on 2017/3/14/014.
 */
public class BusinessFlowNamespaceHandlerSupport extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("stop",
                new BusinessFlowStopDefinitionParser());

        registerBeanDefinitionParser("bus",
                new BusinessFlowBusDefinitionParser());

        registerBeanDefinitionParser("busFactory",
                new BusinessFlowBusFactoryDefinitionParser());
    }
}
