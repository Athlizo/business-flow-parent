package com.lizo.busflow.bus;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 工厂模式创建bus
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
