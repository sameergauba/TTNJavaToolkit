package com.toolkit.jms.activemqjms;

import javax.jms.BytesMessage;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Consumes messages from a topic or a queue from the ActiveMQ broker
 * synchronously or asynchronously. The class implements MessageListener so that
 * a consumer has the choice to receive messages asynchronously.
 * 
 * @author anushree
 *
 */
public class MessageConsumer implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	private String url;

	private Queue queue;
	private String queueName;
	private QueueConnection queueConnection;
	private QueueSession queueSession;

	private Topic topic;
	private String topicName;
	private TopicConnection topicConnection;
	private TopicSession topicSession;

	/**
	 * Takes the url and queueName to establish a connection to the ActiveMQ
	 * broker Creates a queue connection, queue session and a queue. It starts a
	 * queue connection.
	 * 
	 * @throws JMSException
	 */

	/**
	 * No argument constructor
	 */
	public MessageConsumer() {
		super();
	}

	/**
	 * Takes a queueName or topicName and a url
	 * 
	 * @param url
	 * @param queueName
	 * @param topicName
	 */
	public MessageConsumer(String url, String queueName, String topicName) {
		super();
		this.url = url;
		this.queueName = queueName;
		this.topicName = topicName;
	}

	public void setUpQueue() throws JMSException {
		logger.info("Entering set up queue ");

		if (url != null && queueName != null) {

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			queueConnection = (QueueConnection) connectionFactory.createConnection();
			queueSession = (QueueSession) queueConnection.createSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			queue = queueSession.createQueue(queueName);
			queueConnection.start();

		}
		if (url == null || queueName == null) {
			logger.error("Null Pointer Exception");
			throw new NullPointerException();
		}

		logger.info("Exiting set up topic ");

	}

	/**
	 * Takes the url and topicName to establish a connection to the ActiveMQ
	 * broker Creates a topic connection, topic session and a queue. It starts a
	 * topic connection.
	 * 
	 * @throws JMSException
	 */
	public void setUpTopic() throws JMSException {
		logger.info("Entering set up topic ");

		if (url != null && topicName != null) {

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			topicConnection = (TopicConnection) connectionFactory.createConnection();
			topicSession = (TopicSession) topicConnection.createSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			topic = topicSession.createTopic(topicName);
			topicConnection.start();

		}
		if (url == null || topicName == null) {
			logger.error("Null Pointer Exception");
			throw new NullPointerException();

		}
		logger.info("Exiting set up topic ");

	}

	/**
	 * Receives messages from a queue or topic asynchronously
	 * 
	 * @throws JMSException
	 */
	public void receiveMessageAsynchronously() throws JMSException {
		logger.info("Entering receive message asynchronously");

		if (queue != null) {
			QueueReceiver recv = queueSession.createReceiver(getQueue());
			recv.setMessageListener(this);
		} else if (topic != null) {
			TopicSubscriber recv = topicSession.createSubscriber(getTopic());
			recv.setMessageListener(this);
		}
		logger.info("Exiting receive message asynchronously");

	}

	/**
	 * Receives messages from a queue or topic synchronously
	 * 
	 * @throws JMSException
	 */
	public void receiveMessageSynchronously() throws JMSException {
		logger.info("Entering receive message synchronously");

		if (queue != null) {
			QueueReceiver recv = queueSession.createReceiver(getQueue());
			recv.receive();
			logger.info("Exiting receive message synchronously");
		} else if (topic != null) {
			TopicSubscriber recv = topicSession.createSubscriber(getTopic());
			recv.receive();
			logger.info("Exiting receive message synchronously");
		}
		logger.info("Exiting receive message synchronously");
	}

	/**
	 * Stops the connection and session and closes the connection
	 * 
	 * @throws JMSException
	 */
	public void stop() throws JMSException {
		logger.info("Entering Message Consumer stop");
		if (queue != null) {
			queueConnection.stop();
			queueSession.close();
			queueConnection.close();
		}
		if (topic != null) {
			topicConnection.stop();
			topicSession.close();
			topicConnection.close();
		}
		logger.info("Exiting Message Consumer stop");

	}

	/**
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return
	 */
	public Queue getQueue() {
		return queue;
	}

	/**
	 * 
	 * @param queue
	 */
	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	/**
	 * 
	 * @return
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * 
	 * @param queueName
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * 
	 * @return
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * 
	 * @param topic
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	/**
	 * 
	 * @return
	 */
	public String getTopicName() {
		return topicName;
	}

	/**
	 * 
	 * @param topicName
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	/**
	 * 
	 * @return
	 */
	public QueueSession getQueueSession() {
		return queueSession;
	}

	/**
	 * 
	 * @param queueSession
	 */
	public void setQueueSession(QueueSession queueSession) {
		this.queueSession = queueSession;
	}

	/**
	 * 
	 * @return
	 */
	public TopicSession getTopicSession() {
		return topicSession;
	}

	/**
	 * 
	 * @param topicSession
	 */
	public void setTopicSession(TopicSession topicSession) {
		this.topicSession = topicSession;
	}

	/**
	 * This method gets called automatically after a message is received
	 * asynchronously. It downcasts the the message received to its actual class
	 * and prints the corresponding message
	 */
	public void onMessage(Message message) {
		logger.info("Entering onMessage");
		if (message instanceof TextMessage) {
			logger.info("The message received is a text message");
			TextMessage msg = (TextMessage) message;
			logger.info("Received Text Message:" + msg.toString());
		} else if (message instanceof BytesMessage) {
			logger.info("The message received is a bytes message");
			BytesMessage msg = (BytesMessage) message;
			logger.info("Received Bytes Message:" + msg.toString());
		} else if (message instanceof MapMessage) {
			logger.info("The message received is a map message");
			MapMessage msg = (MapMessage) message;
			logger.info("Received Map Message:" + msg.toString());
		} else if (message instanceof ObjectMessage) {
			logger.info("The message received is a object message");
			ObjectMessage msg = (ObjectMessage) message;
			logger.info("Received Object Message:" + msg.toString());
		} else if (message instanceof StreamMessage) {
			logger.info("The message received is a stream message");
			StreamMessage msg = (StreamMessage) message;
			logger.info("Received Stream Message:" + msg.toString());
		}

	}

}
