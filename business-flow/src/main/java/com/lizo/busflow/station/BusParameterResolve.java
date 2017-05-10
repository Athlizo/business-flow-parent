package com.lizo.busflow.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.context.BusContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhou on 2017/5/4/004.
 */
@Component
public class BusParameterResolve {
    private static ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    private static final DefaultValue DEFAULT_VALUE = new DefaultValue();
    private static final Map primitiveDefaultValueMap = new HashMap() {{
        put(boolean.class, DEFAULT_VALUE.defaultBoolean);
        put(byte.class, DEFAULT_VALUE.defaultByte);
        put(char.class, DEFAULT_VALUE.defaultChar);
        put(double.class, DEFAULT_VALUE.defaultDouble);
        put(float.class, DEFAULT_VALUE.defaultFloat);
        put(int.class, DEFAULT_VALUE.defaultInt);
        put(long.class, DEFAULT_VALUE.defaultLong);
        put(short.class, DEFAULT_VALUE.defaultShort);
    }};

    public static Object[] resolve(MethodParameter[] parameters, Bus bus) {
        Object[] res = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            MethodParameter methodParameter = parameters[i];
            methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
            if (methodParameter.getParameterType().isAssignableFrom(Bus.class)) {
                res[i] = bus;
                continue;
            } else if (methodParameter.getParameterType().isAssignableFrom(BusContext.class)) {
                res[i] = bus.getBusContext();
                continue;
            }
            String parameterName = methodParameter.getParameterName();
            boolean requeire = true;
            if (methodParameter.getParameterAnnotation(BusParameter.class) != null) {
                String parameterNameTemp = methodParameter.getParameterAnnotation(BusParameter.class).value();
                if (!StringUtils.isEmpty(parameterNameTemp)) {
                    parameterName = parameterNameTemp;
                }
                requeire = methodParameter.getParameterAnnotation(BusParameter.class).require();
            }
            BusContext busContext = bus.getBusContext();
            Object candicate = busContext.getValue(parameterName);
            if (candicate == null) {
                if (requeire) {
                    throw new IllegalArgumentException("can not resolve parameter:" + parameterName);
                } else {
                    candicate = nullValue(methodParameter.getParameterType());
                }
            } else if (candicate.getClass().isAssignableFrom(methodParameter.getParameterType())) {
                throw new IllegalArgumentException("can not resolve parameter:" + parameterName);
            }
            res[i] = candicate;
        }
        return res;
    }

    private static Object nullValue(Class<?> parameterType) {

        if (parameterType.isPrimitive()) {
            return primitiveDefaultValueMap.get(parameterType);
        }
        return null;
    }

    final private static class DefaultValue {
        private byte defaultByte;
        private char defaultChar;
        private boolean defaultBoolean;
        private double defaultDouble;
        private float defaultFloat;
        private int defaultInt;
        private long defaultLong;
        private short defaultShort;
    }


}
