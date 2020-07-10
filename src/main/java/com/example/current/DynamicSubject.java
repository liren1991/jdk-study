package com.example.current;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
 */
public class DynamicSubject implements InvocationHandler {

	private Object sub;

	public DynamicSubject(Object sub) {
		this.sub = sub;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("proxy: " + proxy.getClass());
		System.out.println("before: " + proxy.getClass());
		Object invoke = method.invoke(sub, args);
		System.out.println("after: " + proxy.getClass());
		return invoke;
	}
}
