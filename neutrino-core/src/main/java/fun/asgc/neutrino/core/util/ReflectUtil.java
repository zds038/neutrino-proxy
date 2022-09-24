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

package fun.asgc.neutrino.core.util;

import com.google.common.collect.Sets;
import fun.asgc.neutrino.core.base.Orderly;
import fun.asgc.neutrino.core.cache.Cache;
import fun.asgc.neutrino.core.cache.MemoryCache;
import fun.asgc.neutrino.core.type.TypeMatchLevel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author: aoshiguchen
 * @date: 2022/6/16
 */
public class ReflectUtil {

	private static Cache<Class<?>, Set<Field>> fieldsCache = new MemoryCache<>();
	private static Cache<Class<?>, Set<Field>> declaredFieldsCache = new MemoryCache<>();
	private static Cache<Class<?>, Set<Field>> inheritChainDeclaredFieldSetCache = new MemoryCache<>();
	private static Cache<Class<?>, Set<Method>> methodsCache = new MemoryCache<>();
	private static Cache<Class<?>, Set<Method>> declaredMethodsCache = new MemoryCache<>();
	private static Cache<Field,Method> getMethodCache = new MemoryCache<>();
	private static Cache<Field,Method> setMethodCache = new MemoryCache<>();
	private static Cache<Method, Set<Class<?>>> methodExceptionTypesCache = new MemoryCache<>();

	/**
	 * 获取字段列表
	 * @param clazz
	 * @return
	 */
	public static Set<Field> getFields(Class<?> clazz) {
		try {
			return kvProcess(fieldsCache, clazz, c -> {
				Field[] fields = c.getFields();
				Set<Field> fieldSet = new HashSet<>();
				if (ArrayUtil.notEmpty(fields)) {
					fieldSet = Stream.of(fields).collect(Collectors.toSet());
				}
				return fieldSet;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取字段列表
	 * @param clazz
	 * @return
	 */
	public static Set<Field> getDeclaredFields(Class<?> clazz) {
		try {
			return kvProcess(declaredFieldsCache, clazz, c -> {
				Field[] fields = c.getDeclaredFields();
				Set<Field> fieldSet = new HashSet<>();
				if (ArrayUtil.notEmpty(fields)) {
					fieldSet = Stream.of(fields).collect(Collectors.toSet());
				}
				return fieldSet;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取继承链字段列表
	 * @param clazz
	 * @return
	 */
	public static Set<Field> getInheritChainDeclaredFieldSet(Class<?> clazz) {
		return getInheritChainDeclaredFieldSet(clazz, Sets.newHashSet(Object.class));
	}

	/**
	 * 获取继承链字段列表
	 * @param clazz
	 * @return
	 */
	public static Set<Field> getInheritChainDeclaredFieldSet(Class<?> clazz, Set<Class<?>> ignoreClasses) {
		try {
			return kvProcess(inheritChainDeclaredFieldSetCache,
				clazz,
				(Class<?> c) -> {
					Set<String> nameSet = new HashSet<>();
					Set<Field> set = new HashSet<>();
					Set<Class<?>> ignores = null == ignoreClasses ? new HashSet<>() : ignoreClasses;
					while (null != c && !ignores.contains(c)) {
						Set<Field> fields = getDeclaredFields(c);
						if (CollectionUtil.notEmpty(fields)) {
							for (Field field : fields) {
								if (field.getName().equals("this$0") || nameSet.contains(field.getName())) {
									continue;
								}
								nameSet.add(field.getName());
								set.add(field);
							}
						}

						c = c.getSuperclass();
					}
					return set;
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取方法列表
	 * @param clazz
	 * @return
	 */
	public static Set<Method> getMethods(Class<?> clazz) {
		try {
			return kvProcess(methodsCache, clazz, c -> {
				Method[] methods = c.getMethods();
				Set<Method> methodSet = new HashSet<>();
				if (ArrayUtil.notEmpty(methods)) {
					methodSet = Stream.of(methods).collect(Collectors.toSet());
				}
				return methodSet;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取方法列表
	 * @param clazz
	 * @return
	 */
	public static Set<Method> getDeclaredMethods(Class<?> clazz) {
		try {
			return kvProcess(declaredMethodsCache, clazz, c -> {
				Method[] methods = c.getDeclaredMethods();
				Set<Method> methodSet = new HashSet<>();
				if (ArrayUtil.notEmpty(methods)) {
					methodSet = Stream.of(methods).collect(Collectors.toSet());
				}
				return methodSet;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取异常集合
	 * @param method
	 * @return
	 */
	public static Set<Class<?>> getExceptionTypes(Method method) {
		try {
			return kvProcess(methodExceptionTypesCache, method, c -> {
				Class<?>[] classes = method.getExceptionTypes();
				Set<Class<?>> classSet = new HashSet<>();
				if (ArrayUtil.notEmpty(classes)) {
					classSet = Stream.of(classes).collect(Collectors.toSet());
				}
				return classSet;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * kv处理逻辑封装
	 * @param cache
	 * @param k
	 * @param fn
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	private static <K,V> V kvProcess(Cache<K,V> cache, K k, Function<K,V>fn) throws Exception {
		return LockUtil.doubleCheckProcess(
			() -> !cache.containsKey(k),
			k,
			() -> cache.set(k, fn.apply(k)),
			() -> cache.get(k)
		);
	}

	/**
	 * 获取Get方法名称
	 * @param field
	 * @return
	 */
	private static String getGetMethodName(Field field) {
		String fieldName = field.getName();
		String methodName = fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));
		return TypeUtil.isBoolean(field) ? "is" + methodName : "get" + methodName;
	}

	/**
	 * 获取Set方法名称
	 * @param field
	 * @return
	 */
	private static String getSetMethodName(Field field) {
		String fieldName = field.getName();
		return "set" + fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));
	}

	/**
	 * 获取Get方法
	 * @param field
	 * @return
	 */
	public static Method getGetMethod(Field field) {
		try {
			return kvProcess(getMethodCache, field, f -> {
				String getMethodName = getGetMethodName(field);
				return getMethods(field.getDeclaringClass()).stream()
					.filter(method -> method.getName().equals(getMethodName) && method.getParameters().length == 0)
					.findFirst().get();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取valueof方法
	 * @param clazz
	 * @param params
	 * @return
	 */
	public static Method getValueOfMethod(Class<?> clazz, Class<?> ...params) {

		try {
			return clazz.getMethod("valueOf", params);
		} catch (NoSuchMethodException e) {
			// ignore
		}
		return null;
	}

	/**
	 * 获取set方法
	 * @param field
	 * @return
	 */
	public static Method getSetMethod(Field field) {
		try {
			return kvProcess(setMethodCache, field, f -> {
				String setMethodName = getSetMethodName(field);
				Optional<Method> methodOptional = getMethods(field.getDeclaringClass()).stream()
					.filter(method -> method.getName().equals(setMethodName) && method.getParameters().length == 1 && method.getParameterTypes()[0] == field.getType())
					.findFirst();
				if (methodOptional.isPresent()) {
					return methodOptional.get();
				}
				int level = TypeMatchLevel.NOT.getDistanceMax();
				Method method = null;
				for (Method m : getMethods(field.getDeclaringClass())) {
					if (m.getName().equals(setMethodName) && m.getParameters().length == 1) {
						int curLevel = TypeUtil.typeMatch(m.getParameterTypes()[0], field.getType()).getTypeDistance();
						if (curLevel < level) {
							level = curLevel;
							method = m;
						}
					}
				}
				if (level < TypeMatchLevel.NOT.getDistanceMin() && method != null) {
					return method;
				}
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过方法设置字段的值
	 * @param field
	 * @param obj
	 * @param value
	 */
	public static boolean setFieldValueByMethod(Field field, Object obj, Object value) {
		Assert.notNull(field, "field不能为空");
		Assert.notNull(obj, "对象不能为空");
		Assert.notNull(value, "值不能为空");

		try {
			Method setMethod = getSetMethod(field);
			if (null == setMethod) {
				return false;
			}
			if (null != value) {
				value = TypeUtil.conversion(value, field.getType());
				if (null == value) {
					return false;
				}
			}
			setMethod.invoke(obj, value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置字段的值
	 * @param fieldList
	 * @param obj
	 * @param value
	 * @return
	 */
	public static boolean setFieldValue(List<Field> fieldList, Object obj, Object value) {
		if (CollectionUtil.isEmpty(fieldList)) {
			return false;
		}
		boolean result = true;
		for (Field field : fieldList) {
			if (!setFieldValue(field, obj, value)) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * 设置字段的值
	 * 先尝试通过set方法设置，若设置失败则通过字段直接设置
	 * @param field
	 * @param obj
	 * @param value
	 * @return
	 */
	public static boolean setFieldValue(Field field, Object obj, Object value) {
		Assert.notNull(field, "field不能为空");
		Assert.notNull(obj, "对象不能为空");

		try {
			return setFieldValueByMethod(field, obj , value) || setFieldValueByField(field, obj, value);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取字段的值
	 * @param field
	 * @param obj
	 * @return
	 */
	public static Object getFieldValue(Field field, Object obj) {
		Assert.notNull(field, "field不能为空");
		Assert.notNull(obj, "对象不能为空");
		try {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			return field.get(obj);
		} catch (Exception e) {
			// ignore
		}
		return null;
	}

	/**
	 * 通过字段设置值
	 * @param field
	 * @param obj
	 * @param value
	 * @return
	 */
	public static boolean setFieldValueByField(Field field, Object obj, Object value) {
		Assert.notNull(field, "field不能为空!");
		Assert.notNull(obj, "对象不能为空!");

		try {
			Class<?> fieldType = field.getType();

			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			if (null == value) {
				if (TypeUtil.isStrictBasicType(fieldType)) {
					return false;
				}
				field.set(obj, null);
				return true;
			}
			value = TypeUtil.conversion(value, fieldType);
			if (null != value) {
				field.set(obj, value);
				return true;
			}

		} catch (Exception e) {
			// ignore
		}
		return false;
	}

	/**
	 * 获取该类的所有接口,并保持按照接口层级的顺序（顶级在最前、其次是第二级、...）
	 * @param clazz
	 * @return
	 */
	public static List<Class<?>> getInterfaceAll(Class<?> clazz) {
		List<Orderly<Class<?>>> list = new ArrayList<>();
		if (ArrayUtil.notEmpty(clazz.getInterfaces())) {
			addTo(list, clazz.getInterfaces(), 0);
			int current = 0;
			while (current < list.size()) {
				Orderly<Class<?>> peek = list.get(current);
				if (ArrayUtil.notEmpty(peek.getData().getInterfaces())) {
					addTo(list, peek.getData().getInterfaces(), peek.getOrder() + 1);
				}
				current++;
			}
		}
		return list.stream().sorted().map(Orderly::getData).collect(Collectors.toList());
	}

	private static void addTo(List<Orderly<Class<?>>> list, Class<?>[] classes, int level) {
		if (ArrayUtil.notEmpty(classes)) {
			for (Class<?> clazz : classes) {
				list.add(new Orderly<Class<?>>().setData(clazz).setOrder(Integer.MAX_VALUE - level));
			}
		}
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object with no arguments.
	 * The target object can be {@code null} when invoking a static {@link Method}.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @return the invocation result, if any
	 * @see #invokeMethod(java.lang.reflect.Method, Object, Object[])
	 */
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, new Object[0]);
	}

	/**
	 * Invoke the specified {@link Method} against the supplied target object with the
	 * supplied arguments. The target object can be {@code null} when invoking a
	 * static {@link Method}.
	 * <p>Thrown exceptions are handled via a call to {@link #handleReflectionException}.
	 * @param method the method to invoke
	 * @param target the target object to invoke the method on
	 * @param args the invocation arguments (may be {@code null})
	 * @return the invocation result, if any
	 */
	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			return method.invoke(target, args);
		}
		catch (Exception ex) {
			handleReflectionException(ex);
		}
		throw new IllegalStateException("Should never get here");
	}

	/**
	 * Handle the given reflection exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of an
	 * InvocationTargetException with such a root cause. Throws an
	 * IllegalStateException with an appropriate message or
	 * UndeclaredThrowableException otherwise.
	 * @param ex the reflection exception to handle
	 */
	public static void handleReflectionException(Exception ex) {
		if (ex instanceof NoSuchMethodException) {
			throw new IllegalStateException("Method not found: " + ex.getMessage());
		}
		if (ex instanceof IllegalAccessException) {
			throw new IllegalStateException("Could not access method: " + ex.getMessage());
		}
		if (ex instanceof InvocationTargetException) {
			handleInvocationTargetException((InvocationTargetException) ex);
		}
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Handle the given invocation target exception. Should only be called if no
	 * checked exception is expected to be thrown by the target method.
	 * <p>Throws the underlying RuntimeException or Error in case of such a root
	 * cause. Throws an UndeclaredThrowableException otherwise.
	 * @param ex the invocation target exception to handle
	 */
	public static void handleInvocationTargetException(InvocationTargetException ex) {
		rethrowRuntimeException(ex.getTargetException());
	}

	/**
	 * Rethrow the given {@link Throwable exception}, which is presumably the
	 * <em>target exception</em> of an {@link InvocationTargetException}.
	 * Should only be called if no checked exception is expected to be thrown
	 * by the target method.
	 * <p>Rethrows the underlying exception cast to a {@link RuntimeException} or
	 * {@link Error} if appropriate; otherwise, throws an
	 * {@link UndeclaredThrowableException}.
	 * @param ex the exception to rethrow
	 * @throws RuntimeException the rethrown exception
	 */
	public static void rethrowRuntimeException(Throwable ex) {
		if (ex instanceof RuntimeException) {
			throw (RuntimeException) ex;
		}
		if (ex instanceof Error) {
			throw (Error) ex;
		}
		throw new UndeclaredThrowableException(ex);
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name
	 * and no parameters. Searches all superclasses up to {@code Object}.
	 * <p>Returns {@code null} if no {@link Method} can be found.
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @return the Method object, or {@code null} if none found
	 */
	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class<?>[0]);
	}

	/**
	 * Attempt to find a {@link Method} on the supplied class with the supplied name
	 * and parameter types. Searches all superclasses up to {@code Object}.
	 * <p>Returns {@code null} if no {@link Method} can be found.
	 * @param clazz the class to introspect
	 * @param name the name of the method
	 * @param paramTypes the parameter types of the method
	 * (may be {@code null} to indicate any signature)
	 * @return the Method object, or {@code null} if none found
	 */
	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.notNull(name, "Method name must not be null");
		Class<?> searchType = clazz;
		while (searchType != null) {
			Set<Method> methods = (searchType.isInterface() ? Sets.newHashSet(searchType.getMethods()) : getDeclaredMethods(searchType));
			for (Method method : methods) {
				if (name.equals(method.getName()) &&
						(paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
}
