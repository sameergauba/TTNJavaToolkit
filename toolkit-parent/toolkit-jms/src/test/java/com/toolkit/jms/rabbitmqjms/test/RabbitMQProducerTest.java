package com.toolkit.jms.rabbitmqjms.test;

import java.io.IOException;

import org.junit.Test;

import com.toolkit.jms.rabbitmqjms.RabbitMQProducer;

public class RabbitMQProducerTest {

	@Test
	public void publishMessageTest1() {
		RabbitMQProducer producer;
		try {
			producer = new RabbitMQProducer("guest", "camelCase", null);
			producer.publishMessage("routingKey", "hi");

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void publishMessageTest2() {
		RabbitMQProducer producer;
		try {
			producer = new RabbitMQProducer("guest", "camelCase", "");
			producer.publishMessage("routingKey", "hi");

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void publishMessageTest3() {
		RabbitMQProducer producer;
		try {
			producer = new RabbitMQProducer("guest", "camelCase", "exchangeName");
			producer.publishMessage(null, "hi");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void publishMessageTest4() {
		RabbitMQProducer producer;
		try {
			producer = new RabbitMQProducer("guest", "camelCase", "exchangeName");
			producer.publishMessage("", "hi");

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test 
	public void publishMessageTest5(){
		RabbitMQProducer producer;
		try {
			producer = new RabbitMQProducer("guest", "camelCase");
			producer.setExchangeName("subject");
			producer.publishMessage("physics", "hello");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}