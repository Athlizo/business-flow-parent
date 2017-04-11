package com.lizo.busflow.bus;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 使用工厂模式创建bus,每次创建都是一个全新的Bus
 * 该bean在定义的时候是SCOPE_PROTOTYPE
 * Created by lizhou on 2017/4/8/008.
 */
public class BusFactory implements BeanFactoryAware {
    private static BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BusFactory.beanFactory = beanFactory;
    }

    public static Bus createNewBus(String id) {
        return beanFactory.getBean(id, Bus.class);
    }
}
