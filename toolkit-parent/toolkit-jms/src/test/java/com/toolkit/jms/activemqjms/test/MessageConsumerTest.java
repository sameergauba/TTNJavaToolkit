package com.toolkit.jms.activemqjms.test;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.junit.Test;
import org.mockito.Mockito;

import com.toolkit.jms.activemqjms.MessageConsumer;

import junit.framework.Assert;

public class MessageConsumerTest {

	@Test(expected = NullPointerException.class)
	public void setUpQueueTest1() throws JMSException {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", null, null);
		messageConsumer.setUpQueue();

	}

	@Test(expected = NullPointerException.class)
	public void setUpQueueTest2() throws JMSException {
		MessageConsumer messageConsumer = new MessageConsumer(null, "GROOVY", null);
		messageConsumer.setUpQueue();
	}

	@Test(expected = NullPointerException.class)
	public void setUpQueueTest3() throws JMSException {
		MessageConsumer messageConsumer = new MessageConsumer(null, null, "GROOVY");
		messageConsumer.setUpQueue();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test
	public void setUpQueueTest4() throws NullPointerException, JMSException {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", "GROOVY", null);
		try {
			ConnectionFactory factory = Mockito.mock(ActiveMQConnectionFactory.class);
			Mockito.when(factory.createConnection()).thenThrow(JMSException.class);
			messageConsumer.setUpQueue();
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@Test(expected = NullPointerException.class)
	public void setUpTopicTest1() throws JMSException {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", null, null);
		messageConsumer.setUpTopic();

	}

	@Test(expected = NullPointerException.class)
	public void setUpTopicTest2() throws JMSException {
		MessageConsumer messageConsumer = new MessageConsumer(null, "GROOVY", null);
		messageConsumer.setUpTopic();
	}

	@Test(expected = NullPointerException.class)
	public void setUpTopicTest3() throws NullPointerException, JMSException {
		MessageConsumer messageConsumer = new MessageConsumer(null, null, "GROOVY");
		messageConsumer.setUpTopic();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void setUpTopicTest4() throws JMSException {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageConsumer.setUpTopic();
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void receiveMessageAsynchronouslyTestTopic() {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageConsumer.setUpTopic();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.receiveMessageAsynchronously();
		} catch (JMSException e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void receiveMessageAsynchronouslyTestQueue() {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", "GROOVY", null);
		try {
			messageConsumer.setUpQueue();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.receiveMessageAsynchronously();
		} catch (JMSException e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@Test
	public void onMessageTest() {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", "GROOVY", null);
		try {
			messageConsumer.setUpQueue();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.onMessage(messageConsumer.getQueueSession().createTextMessage("hi"));
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.onMessage(messageConsumer.getQueueSession().createBytesMessage());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.onMessage(messageConsumer.getQueueSession().createMapMessage());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.onMessage(messageConsumer.getQueueSession().createObjectMessage());
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.onMessage(messageConsumer.getQueueSession().createStreamMessage());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	// @Test
	// public void receieveMessageSynchronouslyTestQueue() {
	// MessageConsumer messageConsumer = new
	// MessageConsumer("tcp://localhost:61616", "GROOVY", null);
	// try {
	// messageConsumer.setUpQueue();
	// } catch (JMSException e1) {
	// e1.printStackTrace();
	// }
	// try {
	// messageConsumer.receiveMessageSynchronously();
	// } catch (JMSException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//
	// @Test
	// public void receieveMessageSynchronouslyTestTopic() {
	//
	// MessageConsumer messageConsumer = new
	// MessageConsumer("tcp://localhost:61616", null, "GROOVY");
	//
	// try {
	// messageConsumer.setUpTopic();
	// } catch (JMSException e1) {
	// e1.printStackTrace();
	// }
	// try {
	// messageConsumer.receiveMessageSynchronously();
	// } catch (JMSException e) {
	// e.printStackTrace();
	// }
	//
	// }

	@Test
	public void stopQueueTest() {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", "GROOVY", null);
		try {
			messageConsumer.setUpQueue();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			messageConsumer.stop();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void stopTopicTest() {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageConsumer.setUpTopic();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			messageConsumer.stop();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
