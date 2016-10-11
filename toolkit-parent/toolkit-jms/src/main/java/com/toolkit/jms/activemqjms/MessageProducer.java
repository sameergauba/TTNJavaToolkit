package com.toolkit.jms.activemqjms;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;

/**
 * Creates a queue or a topic producer that can send messages to a queue or
 * topic on the ActiveMQ broker.
 * 
 * @author anushree
 *
 */
public class MessageProducer {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MessageProducer.class);

	private String url;

	private String queueName;
	private QueueConnection queueConnection;
	private QueueSession queueSession;
	private Queue queue;
	private QueueSender queueSender;

	private String topicName;
	private TopicConnection topicConnection;
	private TopicSession topicSession;
	private Topic topic;
	private TopicPublisher topicPublisher;

	/**
	 * Sets up a connection to an ActiveMQ broker. Creates a connection to a
	 * queue and creates a queue session. Starts the queue connection
	 * 
	 * @throws JMSException
	 *             : This exception is thrown if the createConnection() or
	 *             createSession() methods encounter an exception.
	 * @throws NullPointerException
	 *             : This exception is thrown if the url or the queueName is not
	 *             set.
	 */

	/**
	 * No argument constructor
	 */
	public MessageProducer() {
		super();
	}

	/**
	 * Takes the url and either the queueName or topicName.
	 * 
	 * @param url
	 * @param queueName
	 * @param topicName
	 */
	public MessageProducer(String url, String queueName, String topicName) {
		super();
		this.url = url;
		this.queueName = queueName;
		this.topicName = topicName;
	}

	public void setUpQueue() throws JMSException {
		logger.info("Entering set up queue ");

		if (url != null && queueName != null && topicName == null) {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			queueConnection = (QueueConnection) connectionFactory.createConnection();
			queueSession = (QueueSession) queueConnection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
			queueConnection.start();
		} else if (url == null || queueName == null) {
			logger.error("Null Pointer Exception");
			throw new NullPointerException();

		}
		logger.info("Exiting set up topic ");

	}

	/**
	 * Sets up a connection to an ActiveMQ broker. Creates a connection to a
	 * topic and creates a queue session. Starts a queue connection.
	 * 
	 * @throws JMSException
	 *             : This exception is thrown if the createConnection() or
	 *             createSession() methods encounter an exception.
	 * @throws NullPointerException
	 *             : This exception is thrown if the url or the topicName is not
	 *             set.
	 */
	public void setUpTopic() throws JMSException {
		logger.info("Entering set up topic ");
		if (url != null && topicName != null && queueName == null) {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			topicConnection = (TopicConnection) connectionFactory.createConnection();
			topicSession = (TopicSession) topicConnection.createSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			topicConnection.start();
		} else if (url == null || topicName == null) {
			logger.error("Null Pointer Excception");
			throw new NullPointerException();
		}
		logger.info("Exiting set up topic ");
	}

	/**
	 * Sends message to a queue. Checks if the queue already exists. If it
	 * doesn't already exist, create a queue and then send a message.
	 * 
	 * @param message
	 *            : takes a message to send to the queue
	 * @throws JMSException
	 *             : This exception is thrown if the createQueue() or send()
	 *             method encounters an exception
	 */
	public void sendMessageToQueue(Message message) throws JMSException {
		logger.info("Entering send message to queue ");

		if (queueSession != null) {
			if (queueName != null) {
				logger.info("Sending message to queue: " + getQueueName());
			}
			if (queueName != null && queue == null) {
				createQueue();
			}
			queueSender.send(message);
			logger.info("Message Sent");
		} else {
			logger.error("Null Pointer Excception");
			throw new NullPointerException();
		}
		logger.info("Exiting send message to queue ");

	}

	/**
	 * 
	 * @param message
	 *            : takes a message to send to the topic
	 * @throws JMSException
	 *             : This exception is thrown if the createQueue() or send()
	 *             method encounters an exception
	 */
	public void sendMessageToTopic(Message message) throws JMSException {
		logger.info("Entering send message to topic ");
		if (topicSession != null) {
			if (topicName != null) {
				logger.info("Publishing Message to Topic : " + getTopicName());
			}
			if (topicName != null && topic == null) {
				createTopic();
			}
			topicPublisher.publish(message);
			logger.info("Message Published");
		}
		logger.info("Exiting send message to topic ");

	}

	/**
	 * Creates a queue in the ActiveMQ broker
	 * 
	 * @throws JMSException
	 *             : This exception is thrown if the createQueue() method
	 *             encounters an exception
	 */
	public void createQueue() throws JMSException {
		logger.info("Entering create queue");

		if (queueSession != null) {
			queue = queueSession.createQueue(queueName);
			createSender();
		} else
			throw new NullPointerException();
		logger.info("Exiting send message to topic ");

	}

	/**
	 * Creates a topic in the ActiveMQ broker
	 * 
	 * @throws JMSException
	 *             : This exception is thrown if the createQueue() method
	 *             encounters an exception
	 */
	public void createTopic() throws JMSException {
		logger.info("Entering create Topic ");

		if (topicSession != null) {
			topic = topicSession.createTopic(topicName);
			createPublisher();
		} else
			throw new NullPointerException();
		logger.info("Exiting create Topic ");

	}

	/**
	 * Creates a sender for the particular queue of the name that the user set.
	 * 
	 * @throws JMSException
	 *             : This exception is thrown if the createSender() method
	 *             encounters an exception.
	 */
	public void createSender() throws JMSException {
		logger.info("Entering create sender ");

		queueSender = queueSession.createSender(queue);

		logger.info("Entering create sender ");

	}

	/**
	 * Creates a sender for the particular queue of the name that the user set.
	 * 
	 * @throws JMSException
	 *             : This exception is thrown if the createSender() method
	 *             encounters an exception.
	 */
	public void createPublisher() throws JMSException {
		logger.info("Entering create Topic ");

		topicPublisher = topicSession.createPublisher(topic);

		logger.info("Exiting create Topic ");

	}

	/**
	 * 
	 * @throws JMSException
	 *             : This exception is thrown if the stop() or close() methods
	 *             encounter an exception
	 */
	public void stop() throws JMSException {
		logger.info("Entering Message Producer stop");

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

		logger.info("Exiting Message Producer stop");

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
	public QueueConnection getQueueConnection() {
		return queueConnection;
	}

	/**
	 * 
	 * @param queueConnection
	 */
	public void setQueueConnection(QueueConnection queueConnection) {
		this.queueConnection = queueConnection;
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
	public QueueSender getQueueSender() {
		return queueSender;
	}

	/**
	 * 
	 * @param queueSender
	 */
	public void setQueueSender(QueueSender queueSender) {
		this.queueSender = queueSender;
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
	public TopicConnection getTopicConnection() {
		return topicConnection;
	}

	/**
	 * 
	 * @param topicConnection
	 */
	public void setTopicConnection(TopicConnection topicConnection) {
		this.topicConnection = topicConnection;
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
	public TopicPublisher getTopicPublisher() {
		return topicPublisher;
	}

	/**
	 * 
	 * @param topicPublisher
	 */
	public void setTopicPublisher(TopicPublisher topicPublisher) {
		this.topicPublisher = topicPublisher;
	}

}
