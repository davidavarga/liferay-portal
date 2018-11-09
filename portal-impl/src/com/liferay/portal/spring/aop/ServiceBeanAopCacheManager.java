/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.spring.aop;

import com.liferay.petra.reflect.AnnotationLocator;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class ServiceBeanAopCacheManager {

	public ServiceBeanAopCacheManager(MethodInterceptor methodInterceptor) {
		List<MethodInterceptor> classLevelMethodInterceptors =
			new ArrayList<>();
		List<MethodInterceptor> fullMethodInterceptors = new ArrayList<>();

		while (true) {
			if (!(methodInterceptor instanceof ChainableMethodAdvice)) {
				classLevelMethodInterceptors.add(methodInterceptor);
				fullMethodInterceptors.add(methodInterceptor);

				break;
			}

			ChainableMethodAdvice chainableMethodAdvice =
				(ChainableMethodAdvice)methodInterceptor;

			chainableMethodAdvice.setServiceBeanAopCacheManager(this);

			if (methodInterceptor instanceof AnnotationChainableMethodAdvice) {
				AnnotationChainableMethodAdvice<?>
					annotationChainableMethodAdvice =
						(AnnotationChainableMethodAdvice<?>)methodInterceptor;

				Class<? extends Annotation> annotationClass =
					annotationChainableMethodAdvice.getAnnotationClass();

				Target target = annotationClass.getAnnotation(Target.class);

				if (target == null) {
					classLevelMethodInterceptors.add(methodInterceptor);
				}
				else {
					for (ElementType elementType : target.value()) {
						if (elementType == ElementType.TYPE) {
							classLevelMethodInterceptors.add(methodInterceptor);

							break;
						}
					}
				}

				_registerAnnotationChainableMethodAdvice(
					annotationClass, annotationChainableMethodAdvice);
			}
			else {
				classLevelMethodInterceptors.add(methodInterceptor);
			}

			fullMethodInterceptors.add(methodInterceptor);

			methodInterceptor = chainableMethodAdvice.nextMethodInterceptor;
		}

		_classLevelMethodInterceptors = classLevelMethodInterceptors.toArray(
			new MethodInterceptor[classLevelMethodInterceptors.size()]);
		_fullMethodInterceptors = fullMethodInterceptors.toArray(
			new MethodInterceptor[fullMethodInterceptors.size()]);
	}

	public <T> T findAnnotation(
		MethodInvocation methodInvocation,
		Class<? extends Annotation> annotationType, T defaultValue) {

		T annotation = _findAnnotation(
			_methodAnnotations, methodInvocation, annotationType, defaultValue);

		if (annotation == null) {
			annotation = defaultValue;

			Method method = methodInvocation.getMethod();

			Object target = methodInvocation.getThis();

			List<Annotation> annotations = AnnotationLocator.locate(
				method, target.getClass());

			Iterator<Annotation> iterator = annotations.iterator();

			while (iterator.hasNext()) {
				Annotation curAnnotation = iterator.next();

				Class<? extends Annotation> curAnnotationType =
					curAnnotation.annotationType();

				if (!_annotationChainableMethodAdvices.containsKey(
						curAnnotationType)) {

					iterator.remove();
				}
				else if (annotationType == curAnnotationType) {
					annotation = (T)curAnnotation;
				}
			}

			if (annotations.isEmpty()) {
				_methodAnnotations.put(method, _nullAnnotations);
			}
			else {
				_methodAnnotations.put(
					method,
					annotations.toArray(new Annotation[annotations.size()]));
			}
		}

		return annotation;
	}

	public MethodInterceptor[] getMethodInterceptors(
		MethodInvocation methodInvocation) {

		MethodInterceptor[] methodInterceptors = _methodInterceptors.get(
			methodInvocation.getMethod());

		if (methodInterceptors == null) {
			methodInterceptors = _fullMethodInterceptors;

			_methodInterceptors.put(
				methodInvocation.getMethod(), methodInterceptors);
		}

		return methodInterceptors;
	}

	public void putMethodInterceptors(
		MethodInvocation methodInvocation,
		MethodInterceptor[] methodInterceptors) {

		_methodInterceptors.put(
			methodInvocation.getMethod(), methodInterceptors);
	}

	public void removeMethodInterceptor(
		MethodInvocation methodInvocation,
		MethodInterceptor methodInterceptor) {

		Method method = methodInvocation.getMethod();

		MethodInterceptor[] methodInterceptors = _methodInterceptors.get(
			method);

		if (methodInterceptors == null) {
			return;
		}

		int index = -1;

		for (int i = 0; i < methodInterceptors.length; i++) {
			if (methodInterceptors[i].equals(methodInterceptor)) {
				index = i;

				break;
			}
		}

		if (index < 0) {
			return;
		}

		int newLength = methodInterceptors.length - 1;

		MethodInterceptor[] newMethodInterceptors =
			new MethodInterceptor[newLength];

		if (index > 0) {
			System.arraycopy(
				methodInterceptors, 0, newMethodInterceptors, 0, index);
		}

		if (index < newLength) {
			System.arraycopy(
				methodInterceptors, index + 1, newMethodInterceptors, index,
				newLength - index);
		}

		if (Arrays.equals(
				newMethodInterceptors, _classLevelMethodInterceptors)) {

			newMethodInterceptors = _classLevelMethodInterceptors;
		}

		_methodInterceptors.put(method, newMethodInterceptors);
	}

	public void reset() {
		_annotations.clear();
		_methodInterceptors.clear();
	}

	private static <T> T _findAnnotation(
		Map<Method, Annotation[]> methodAnnotations,
		MethodInvocation methodInvocation,
		Class<? extends Annotation> annotationType, T defaultValue) {

		Annotation[] annotations = methodAnnotations.get(
			methodInvocation.getMethod());

		if (annotations == _nullAnnotations) {
			return defaultValue;
		}

		if (annotations == null) {
			return null;
		}

		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == annotationType) {
				return (T)annotation;
			}
		}

		return defaultValue;
	}

	private void _registerAnnotationChainableMethodAdvice(
		Class<? extends Annotation> annotationClass,
		AnnotationChainableMethodAdvice<?> annotationChainableMethodAdvice) {

		AnnotationChainableMethodAdvice<?>[] annotationChainableMethodAdvices =
			_annotationChainableMethodAdvices.get(annotationClass);

		if (annotationChainableMethodAdvices == null) {
			annotationChainableMethodAdvices =
				new AnnotationChainableMethodAdvice<?>[1];

			annotationChainableMethodAdvices[0] =
				annotationChainableMethodAdvice;
		}
		else {
			annotationChainableMethodAdvices = ArrayUtil.append(
				annotationChainableMethodAdvices,
				annotationChainableMethodAdvice);
		}

		_annotationChainableMethodAdvices.put(
			annotationClass, annotationChainableMethodAdvices);
	}

	private static final Map<Method, Annotation[]> _annotations =
		new ConcurrentHashMap<>();
	private static final Annotation[] _nullAnnotations = new Annotation[0];

	private final Map
		<Class<? extends Annotation>, AnnotationChainableMethodAdvice<?>[]>
			_annotationChainableMethodAdvices = new HashMap<>();
	private final MethodInterceptor[] _classLevelMethodInterceptors;
	private final MethodInterceptor[] _fullMethodInterceptors;
	private final Map<Method, Annotation[]> _methodAnnotations =
		new ConcurrentHashMap<>();
	private final Map<Method, MethodInterceptor[]> _methodInterceptors =
		new ConcurrentHashMap<>();

}