package com.lizo.busflow.station;

import com.lizo.busflow.bus.Bus;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

/**
 * Created by lizhou on 2017/5/4/004.
 */
@Component
public class BusParameterResolve {
    private static ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public static Object[] resolve(MethodParameter[] parameters, Bus bus) {
        Object[] res = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter methodParameter = parameters[i];
            methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
            if (methodParameter.getParameterType().isAssignableFrom(Bus.class)) {
                res[i] = bus;
                continue;
            }
            String parameterName = methodParameter.getParameterName();
            if (methodParameter.getParameterAnnotation(BusParameter.class) != null) {
                parameterName = methodParameter.getParameterAnnotation(BusParameter.class).value();
            }
            Object candicate = bus.getContext(parameterName);
            if (candicate == null) {
                throw new IllegalArgumentException("can not resolve parameter:" + parameterName);
            }
            if (candicate.getClass().isAssignableFrom(methodParameter.getParameterType())) {
                throw new IllegalArgumentException("can not resolve parameter:" + parameterName);
            }
            res[i] = candicate;
        }
        return res;
    }
}
