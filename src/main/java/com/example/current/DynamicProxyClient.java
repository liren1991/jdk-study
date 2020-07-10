package com.example.current;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxyClient {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Subject rs=new RealSubject();//这里指定被代理类
		InvocationHandler ds=new DynamicSubject(rs);
		Class<?> cls=rs.getClass();
		//以下是一次性生成代理
		Subject subject=(Subject) Proxy.newProxyInstance(cls.getClassLoader(),cls.getInterfaces(), ds);
		subject.myRequest();
	}
}
