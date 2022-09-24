/**
 * Copyright (c) 2022 aoshiguchen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package fun.asgc.neutrino.core.base.type;

import fun.asgc.neutrino.core.util.ConcurrentReferenceHashMap;
import fun.asgc.neutrino.core.util.ReflectUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.*;

/**
 * @author: aoshiguchen
 * @date: 2022/9/25
 */
abstract class SerializableTypeWrapper {

    private static final Class<?>[] SUPPORTED_SERIALIZABLE_TYPES = {
            GenericArrayType.class, ParameterizedType.class, TypeVariable.class, WildcardType.class};

    static final ConcurrentReferenceHashMap<Type, Type> cache = new ConcurrentReferenceHashMap<Type, Type>(256);


    /**
     * Return a {@link Serializable} variant of {@link Field#getGenericType()}.
     */
    public static Type forField(Field field) {
        return forTypeProvider(new FieldTypeProvider(field));
    }

    /**
     * Return a {@link Serializable} variant of
     * {@link MethodParameter#getGenericParameterType()}.
     */
    public static Type forMethodParameter(MethodParameter methodParameter) {
        return forTypeProvider(new MethodParameterTypeProvider(methodParameter));
    }

    /**
     * Return a {@link Serializable} variant of {@link Class#getGenericSuperclass()}.
     */
    @SuppressWarnings("serial")
    public static Type forGenericSuperclass(final Class<?> type) {
        return forTypeProvider(new SimpleTypeProvider() {
            @Override
            public Type getType() {
                return type.getGenericSuperclass();
            }
        });
    }

    /**
     * Return a {@link Serializable} variant of {@link Class#getGenericInterfaces()}.
     */
    @SuppressWarnings("serial")
    public static Type[] forGenericInterfaces(final Class<?> type) {
        Type[] result = new Type[type.getGenericInterfaces().length];
        for (int i = 0; i < result.length; i++) {
            final int index = i;
            result[i] = forTypeProvider(new SimpleTypeProvider() {
                @Override
                public Type getType() {
                    return type.getGenericInterfaces()[index];
                }
            });
        }
        return result;
    }

    /**
     * Return a {@link Serializable} variant of {@link Class#getTypeParameters()}.
     */
    @SuppressWarnings("serial")
    public static Type[] forTypeParameters(final Class<?> type) {
        Type[] result = new Type[type.getTypeParameters().length];
        for (int i = 0; i < result.length; i++) {
            final int index = i;
            result[i] = forTypeProvider(new SimpleTypeProvider() {
                @Override
                public Type getType() {
                    return type.getTypeParameters()[index];
                }
            });
        }
        return result;
    }

    /**
     * Unwrap the given type, effectively returning the original non-serializable type.
     * @param type the type to unwrap
     * @return the original non-serializable type
     */
    @SuppressWarnings("unchecked")
    public static <T extends Type> T unwrap(T type) {
        Type unwrapped = type;
        while (unwrapped instanceof SerializableTypeProxy) {
            unwrapped = ((SerializableTypeProxy) type).getTypeProvider().getType();
        }
        return (T) unwrapped;
    }

    /**
     * Return a {@link Serializable} {@link Type} backed by a {@link TypeProvider} .
     */
    static Type forTypeProvider(TypeProvider provider) {
        Type providedType = provider.getType();
        if (providedType == null || providedType instanceof Serializable) {
            // No serializable type wrapping necessary (e.g. for java.lang.Class)
            return providedType;
        }

        // Obtain a serializable type proxy for the given provider...
        Type cached = cache.get(providedType);
        if (cached != null) {
            return cached;
        }
        for (Class<?> type : SUPPORTED_SERIALIZABLE_TYPES) {
            if (type.isInstance(providedType)) {
                ClassLoader classLoader = provider.getClass().getClassLoader();
                Class<?>[] interfaces = new Class<?>[] {type, SerializableTypeProxy.class, Serializable.class};
                InvocationHandler handler = new TypeProxyInvocationHandler(provider);
                cached = (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
                cache.put(providedType, cached);
                return cached;
            }
        }
        throw new IllegalArgumentException("Unsupported Type class: " + providedType.getClass().getName());
    }


    /**
     * Additional interface implemented by the type proxy.
     */
    interface SerializableTypeProxy {

        /**
         * Return the underlying type provider.
         */
        TypeProvider getTypeProvider();
    }


    /**
     * A {@link Serializable} interface providing access to a {@link Type}.
     */
    interface TypeProvider extends Serializable {

        /**
         * Return the (possibly non {@link Serializable}) {@link Type}.
         */
        Type getType();

        /**
         * Return the source of the type or {@code null}.
         */
        Object getSource();
    }


    /**
     * Base implementation of {@link TypeProvider} with a {@code null} source.
     */
    @SuppressWarnings("serial")
    private static abstract class SimpleTypeProvider implements TypeProvider {

        @Override
        public Object getSource() {
            return null;
        }
    }


    /**
     * {@link Serializable} {@link InvocationHandler} used by the proxied {@link Type}.
     * Provides serialization support and enhances any methods that return {@code Type}
     * or {@code Type[]}.
     */
    @SuppressWarnings("serial")
    private static class TypeProxyInvocationHandler implements InvocationHandler, Serializable {

        private final TypeProvider provider;

        public TypeProxyInvocationHandler(TypeProvider provider) {
            this.provider = provider;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("equals")) {
                Object other = args[0];
                // Unwrap proxies for speed
                if (other instanceof Type) {
                    other = unwrap((Type) other);
                }
                return this.provider.getType().equals(other);
            }
            else if (method.getName().equals("hashCode")) {
                return this.provider.getType().hashCode();
            }
            else if (method.getName().equals("getTypeProvider")) {
                return this.provider;
            }

            if (Type.class == method.getReturnType() && args == null) {
                return forTypeProvider(new MethodInvokeTypeProvider(this.provider, method, -1));
            }
            else if (Type[].class == method.getReturnType() && args == null) {
                Type[] result = new Type[((Type[]) method.invoke(this.provider.getType(), args)).length];
                for (int i = 0; i < result.length; i++) {
                    result[i] = forTypeProvider(new MethodInvokeTypeProvider(this.provider, method, i));
                }
                return result;
            }

            try {
                return method.invoke(this.provider.getType(), args);
            }
            catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
        }
    }


    /**
     * {@link TypeProvider} for {@link Type}s obtained from a {@link Field}.
     */
    @SuppressWarnings("serial")
    static class FieldTypeProvider implements TypeProvider {

        private final String fieldName;

        private final Class<?> declaringClass;

        private transient Field field;

        public FieldTypeProvider(Field field) {
            this.fieldName = field.getName();
            this.declaringClass = field.getDeclaringClass();
            this.field = field;
        }

        @Override
        public Type getType() {
            return this.field.getGenericType();
        }

        @Override
        public Object getSource() {
            return this.field;
        }

        private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
            inputStream.defaultReadObject();
            try {
                this.field = this.declaringClass.getDeclaredField(this.fieldName);
            }
            catch (Throwable ex) {
                throw new IllegalStateException("Could not find original class structure", ex);
            }
        }
    }


    /**
     * {@link TypeProvider} for {@link Type}s obtained from a {@link MethodParameter}.
     */
    @SuppressWarnings("serial")
    static class MethodParameterTypeProvider implements TypeProvider {

        private final String methodName;

        private final Class<?>[] parameterTypes;

        private final Class<?> declaringClass;

        private final int parameterIndex;

        private transient MethodParameter methodParameter;

        public MethodParameterTypeProvider(MethodParameter methodParameter) {
            if (methodParameter.getMethod() != null) {
                this.methodName = methodParameter.getMethod().getName();
                this.parameterTypes = methodParameter.getMethod().getParameterTypes();
            }
            else {
                this.methodName = null;
                this.parameterTypes = methodParameter.getConstructor().getParameterTypes();
            }
            this.declaringClass = methodParameter.getDeclaringClass();
            this.parameterIndex = methodParameter.getParameterIndex();
            this.methodParameter = methodParameter;
        }


        @Override
        public Type getType() {
            return this.methodParameter.getGenericParameterType();
        }

        @Override
        public Object getSource() {
            return this.methodParameter;
        }

        private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
            inputStream.defaultReadObject();
            try {
                if (this.methodName != null) {
                    this.methodParameter = new MethodParameter(
                            this.declaringClass.getDeclaredMethod(this.methodName, this.parameterTypes), this.parameterIndex);
                }
                else {
                    this.methodParameter = new MethodParameter(
                            this.declaringClass.getDeclaredConstructor(this.parameterTypes), this.parameterIndex);
                }
            }
            catch (Throwable ex) {
                throw new IllegalStateException("Could not find original class structure", ex);
            }
        }
    }


    /**
     * {@link TypeProvider} for {@link Type}s obtained by invoking a no-arg method.
     */
    @SuppressWarnings("serial")
    static class MethodInvokeTypeProvider implements TypeProvider {

        private final TypeProvider provider;

        private final String methodName;

        private final Class<?> declaringClass;

        private final int index;

        private transient Method method;

        private transient volatile Object result;

        public MethodInvokeTypeProvider(TypeProvider provider, Method method, int index) {
            this.provider = provider;
            this.methodName = method.getName();
            this.declaringClass = method.getDeclaringClass();
            this.index = index;
            this.method = method;
        }

        @Override
        public Type getType() {
            Object result = this.result;
            if (result == null) {
                // Lazy invocation of the target method on the provided type
                result = ReflectUtil.invokeMethod(this.method, this.provider.getType());
                // Cache the result for further calls to getType()
                this.result = result;
            }
            return (result instanceof Type[] ? ((Type[]) result)[this.index] : (Type) result);
        }

        @Override
        public Object getSource() {
            return null;
        }

        private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
            inputStream.defaultReadObject();
            this.method = ReflectUtil.findMethod(this.declaringClass, this.methodName);
            if (this.method.getReturnType() != Type.class && this.method.getReturnType() != Type[].class) {
                throw new IllegalStateException(
                        "Invalid return type on deserialized method - needs to be Type or Type[]: " + this.method);
            }
        }
    }

}

