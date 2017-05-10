package com.lizo.busflow.station;

import com.lizo.busflow.bus.Bus;
import com.lizo.busflow.bus.DefaultBus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.*;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lizhou on 2017/5/4/004.
 */
public class BusHandlerMethod {
    /**
     * Logger that is available to subclasses
     */
    protected final Log logger = LogFactory.getLog(getClass());

    private final Object bean;

    private final Class<?> beanType;

    private Method method;

    private final Method bridgedMethod;

    private final MethodParameter[] parameters;


    public BusHandlerMethod(Object bean, String methodName) throws NoSuchMethodException {
        Assert.notNull(bean, "Bean is required");
        Assert.notNull(methodName, "Method name is required");
        this.bean = bean;
        this.beanType = ClassUtils.getUserClass(bean);
//        this.method = station.getClass().getMethod(methodName, parameterTypes);
        for (Method tempMethod : bean.getClass().getMethods()) {
            if (tempMethod.getName().equals(methodName)) {
                if (this.method != null) {
                    throw new RuntimeException("class：" + bean.getClass() + "have more than one method:" + methodName);
                }
                this.method = tempMethod;
            }
        }
        if (this.method == null) {
            throw new RuntimeException("class：" + bean.getClass() + "have not method:" + methodName);
        }
        this.bridgedMethod = BridgeMethodResolver.findBridgedMethod(this.method);
        this.parameters = initMethodParameters();
    }

    public void invokeForBus(Bus Bus) throws Exception {
        Object[] agrs = BusParameterResolve.resolve(parameters, Bus);
        doInvoke(agrs);
    }

    /**
     * Invoke the handler method with the given argument values.
     */
    protected void doInvoke(Object... args) throws Exception {
        ReflectionUtils.makeAccessible(getBridgedMethod());
        try {
            getBridgedMethod().invoke(bean, args);
        } catch (IllegalArgumentException ex) {
            String text = (ex.getMessage() != null ? ex.getMessage() : "Illegal argument");
            throw new IllegalStateException(ex);
        } catch (InvocationTargetException ex) {
            // Unwrap for HandlerExceptionResolvers ...
            Throwable targetException = ex.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw (RuntimeException) targetException;
            } else if (targetException instanceof Error) {
                throw (Error) targetException;
            } else if (targetException instanceof Exception) {
                throw (Exception) targetException;
            } else {
                String text = "Failed to invoke handler method";
                throw new IllegalStateException(text, targetException);
            }
        }
    }


    private MethodParameter[] initMethodParameters() {
        int count = this.bridgedMethod.getParameterTypes().length;
        MethodParameter[] result = new MethodParameter[count];
        for (int i = 0; i < count; i++) {
            HandlerMethodParameter parameter = new HandlerMethodParameter(i);
            GenericTypeResolver.resolveParameterType(parameter, this.beanType);
            result[i] = parameter;
        }
        return result;
    }

    public Object getBean() {
        return bean;
    }

    /**
     * Returns the method for this handler method.
     */
    public Method getMethod() {
        return this.method;
    }

    /**
     * This method returns the type of the handler for this handler method.
     * <p>Note that if the station type is a CGLIB-generated class, the original
     * user-defined class is returned.
     */
    public Class<?> getBeanType() {
        return this.beanType;
    }

    /**
     * If the station method is a bridge method, this method returns the bridged
     * (user-defined) method. Otherwise it returns the same method as {@link #getMethod()}.
     */
    protected Method getBridgedMethod() {
        return this.bridgedMethod;
    }

    /**
     * Returns the method parameters for this handler method.
     */
    public MethodParameter[] getMethodParameters() {
        return this.parameters;
    }


    /**
     * Return the BusHandlerMethod return type.
     */
    public MethodParameter getReturnType() {
        return new HandlerMethodParameter(-1);
    }


    /**
     * Returns {@code true} if the method return type is void, {@code false} otherwise.
     */
    public boolean isVoid() {
        return Void.TYPE.equals(getReturnType().getParameterType());
    }

    /**
     * Returns a single annotation on the underlying method traversing its super methods
     * if no annotation can be found on the given method itself.
     * <p>Also supports <em>merged</em> composed annotations with attribute
     * overrides as of Spring Framework 4.2.2.
     *
     * @param annotationType the type of annotation to introspect the method for
     * @return the annotation, or {@code null} if none found
     * @see AnnotatedElementUtils#findMergedAnnotation
     */
    public <A extends Annotation> A getMethodAnnotation(Class<A> annotationType) {
        return AnnotatedElementUtils.findMergedAnnotation(this.method, annotationType);
    }

    /**
     * Return whether the parameter is declared with the given annotation type.
     *
     * @param annotationType the annotation type to look for
     * @see AnnotatedElementUtils#hasAnnotation
     * @since 4.3
     */
    public <A extends Annotation> boolean hasMethodAnnotation(Class<A> annotationType) {
        return AnnotatedElementUtils.hasAnnotation(this.method, annotationType);
    }


    /**
     * Return a short representation of this handler method for log message purposes.
     *
     * @since 4.3
     */
    public String getShortLogMessage() {
        int args = this.method.getParameterTypes().length;
        return getBeanType().getName() + "#" + this.method.getName() + "[" + args + " args]";
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BusHandlerMethod)) {
            return false;
        }
        BusHandlerMethod otherMethod = (BusHandlerMethod) other;
        return (this.bean.equals(otherMethod.bean) && this.method.equals(otherMethod.method));
    }

    @Override
    public int hashCode() {
        return (this.bean.hashCode() * 31 + this.method.hashCode());
    }

    @Override
    public String toString() {
        return this.method.toGenericString();
    }


    /**
     * A MethodParameter with BusHandlerMethod-specific behavior.
     */
    protected class HandlerMethodParameter extends SynthesizingMethodParameter {

        public HandlerMethodParameter(int index) {
            super(BusHandlerMethod.this.bridgedMethod, index);
        }

        protected HandlerMethodParameter(HandlerMethodParameter original) {
            super(original);
        }

        @Override
        public Class<?> getContainingClass() {
            return BusHandlerMethod.this.getBeanType();
        }

        @Override
        public <T extends Annotation> T getMethodAnnotation(Class<T> annotationType) {
            return BusHandlerMethod.this.getMethodAnnotation(annotationType);
        }

        @Override
        public <T extends Annotation> boolean hasMethodAnnotation(Class<T> annotationType) {
            return BusHandlerMethod.this.hasMethodAnnotation(annotationType);
        }

        @Override
        public HandlerMethodParameter clone() {
            return new HandlerMethodParameter(this);
        }
    }
}
