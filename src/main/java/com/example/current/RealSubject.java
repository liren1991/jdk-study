package com.example.current;

public class RealSubject implements Subject{

	@Override
	public void myRequest() {
		System.out.println("myRequest invoked");
	}
}
