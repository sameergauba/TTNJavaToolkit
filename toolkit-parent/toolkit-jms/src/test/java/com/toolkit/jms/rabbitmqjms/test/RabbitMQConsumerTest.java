package com.toolkit.jms.rabbitmqjms.test;

import java.io.IOException;

import org.junit.Test;

import com.toolkit.jms.rabbitmqjms.RabbitMQConsumer;

public class RabbitMQConsumerTest {

	@Test
	public void receiveFromQueueTest() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer(null, "xyz", "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Test
	public void receiveFromQueueConitionalTest1() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer("", "xyz", "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Test(expected = NullPointerException.class)
	public void receiveFromQueueConitionalTest2() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer("", "", "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Test(expected = NullPointerException.class)
	public void receiveFromQueueConitionalTest3() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer("", null, "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Test
	public void receiveFromFanoutTest() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer("exchange", null, "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void receiveFromFanoutTest2() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer("exchange", "", "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void receiveFromDirectTest() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer("exchange", "routingkey", "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void receiveFromDirectTest2() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer("exchange", "routingkey", "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = NullPointerException.class)
	public void receiveFromQueue2() {
		RabbitMQConsumer consumer;
		try {
			consumer = new RabbitMQConsumer(null, "", "guest", "camelCase");
			consumer.receieveMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
