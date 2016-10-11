package com.toolkit.jms.activemqjms.test;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toolkit.jms.activemqjms.MessageConsumer;
import com.toolkit.jms.activemqjms.MessageProducer;

import junit.framework.Assert;

public class MessageProducerTest {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test(expected = NullPointerException.class)
	public void setUpQueueTest11() throws NullPointerException, JMSException {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, null);
		messageProducer.setUpQueue();

	}

	@Test(expected = NullPointerException.class)
	public void setUpQueueTest2() throws NullPointerException, JMSException {
		MessageProducer messageProducer = new MessageProducer(null, "GROOVY", null);
		messageProducer.setUpQueue();
	}

	@Test(expected = NullPointerException.class)
	public void setUpQueueTest3() throws NullPointerException, JMSException {
		MessageProducer messageProducer = new MessageProducer(null, null, "GROOVY");
		messageProducer.setUpQueue();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test
	public void setUpQueueTest4() throws NullPointerException, JMSException {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", "GROOVY", null);
		try {
			ConnectionFactory factory = Mockito.mock(ActiveMQConnectionFactory.class);
			Mockito.when(factory.createConnection()).thenThrow(JMSException.class);
			messageProducer.setUpQueue();
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@Test(expected = NullPointerException.class)
	public void setUpTopicTest1() throws NullPointerException, JMSException {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, null);
		messageProducer.setUpTopic();

	}

	@Test(expected = NullPointerException.class)
	public void setUpTopicTest2() throws NullPointerException, JMSException {
		MessageProducer messageProducer = new MessageProducer(null, "GROOVY", null);
		messageProducer.setUpTopic();
	}

	@Test(expected = NullPointerException.class)
	public void setUpTopicTest3() throws NullPointerException, JMSException {
		MessageProducer messageProducer = new MessageProducer(null, null, "GROOVY");
		messageProducer.setUpTopic();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void setUpTopicTest4() throws NullPointerException, JMSException {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageProducer.setUpTopic();
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void sendMessageToQueueTest1() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", "GROOVY", null);
		try {
			messageProducer.setUpQueue();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageProducer.sendMessageToQueue(messageProducer.getQueueSession().createMessage());
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void sendMessageToQueueTest2() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", "GROOVY", null);
		try {
			messageProducer.setUpQueue();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageProducer.sendMessageToQueue(messageProducer.getQueueSession().createMessage());
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(NullPointerException.class));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void sendMessageToQueueTest3() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", "GROOVY", null);
		try {
			messageProducer.setUpQueue();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageProducer.sendMessageToQueue(messageProducer.getQueueSession().createMessage());
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(NullPointerException.class));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void sendMessageToTopicTest1() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageProducer.setUpTopic();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageProducer.sendMessageToTopic(messageProducer.getTopicSession().createMessage());
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void sendMessageToTopicTest2() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageProducer.setUpTopic();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageProducer.sendMessageToTopic(messageProducer.getTopicSession().createMessage());
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void sendMessageToTopicTest3() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageProducer.setUpTopic();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageProducer.sendMessageToTopic(messageProducer.getTopicSession().createMessage());
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(JMSException.class));
		}
	}

	@Test
	public void stopQueueTest() {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", "GROOVY", null);
		try {
			messageConsumer.setUpQueue();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.stop();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void stopTopicTest() {
		MessageConsumer messageConsumer = new MessageConsumer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageConsumer.setUpTopic();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		try {
			messageConsumer.stop();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendMessageToQueueBranchTest() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", "GROOVY", null);
		try {
			try {
				messageProducer.sendMessageToQueue(messageProducer.getQueueSession().createTextMessage());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(NullPointerException.class));
		}
	}

	@Test
	public void sendMessageToTopicBranchTest() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, "GROOVY");
		try {
			try {
				messageProducer.sendMessageToTopic(messageProducer.getTopicSession().createTextMessage());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			Assert.assertTrue(e.getClass().equals(NullPointerException.class));
		}
	}

	@Test
	public void createQueueTest() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", "GROOVY", null);
		try {
			try {
				messageProducer.createQueue();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e) {
			Assert.assertTrue(e.getClass().equals(NullPointerException.class));
		}
	}

	@Test
	public void createQueueTest2() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", "GROOVY", null);
		try {
			messageProducer.setUpQueue();
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		} catch (JMSException e1) {
			e1.printStackTrace();
		}

		try {
			messageProducer.createQueue();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void createTopicTest() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, "GROOVY");
		try {
			try {
				messageProducer.createTopic();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} catch (NullPointerException e) {
			Assert.assertTrue(e.getClass().equals(NullPointerException.class));
		}
	}

	@Test
	public void createTopicTest2() {
		MessageProducer messageProducer = new MessageProducer("tcp://localhost:61616", null, "GROOVY");
		try {
			messageProducer.setUpTopic();
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
		try {
			messageProducer.createTopic();
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getQueueNameTest() {
		MessageProducer messageProducer = new MessageProducer();
		messageProducer.setUrl("tcp://localhost:61616");
		messageProducer.setQueueName("GROOVY");
		String name = messageProducer.getQueueName();
		Assert.assertEquals("GROOVY", name);
	}
	
	@Test
	public void getTopicNameTest() {
		MessageProducer messageProducer = new MessageProducer();
		messageProducer.setUrl("tcp://localhost:61616");
		messageProducer.setTopicName("GROOVY");
		String name = messageProducer.getTopicName();
		Assert.assertEquals("GROOVY", name);
	}
}
