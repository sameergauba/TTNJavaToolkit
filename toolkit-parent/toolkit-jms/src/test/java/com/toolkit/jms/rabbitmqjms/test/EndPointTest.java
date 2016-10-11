package com.toolkit.jms.rabbitmqjms.test;

import java.io.IOException;

import org.junit.Test;

import com.toolkit.jms.rabbitmqjms.EndPoint;

public class EndPointTest {

	@Test
	public void defaultConstructorTest() {
		try {
			EndPoint endPoint = new EndPoint("guest", "camelCase");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void argumentConstructorTest() {
		try {
			EndPoint endPoint = new EndPoint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void closeTest() {
		try {
			EndPoint endPoint = new EndPoint("guest", "camelCase");
			endPoint.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
