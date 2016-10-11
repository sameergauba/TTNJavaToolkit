package com.toolkit.jms.rabbitmqjms;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * Extends the EndPoint class. This class defines a consumer endpoint for the
 * messaging broker. Consumes message from exchanges. RabbitMQ provides four
 * types of exchanges: default, direct, fanout, topic. A consumer can pick up
 * messages from the exchange it is bound to. If the exchange name is not
 * specified, it picks up messages from the default exchange. If the exchange
 * name is specified but no routing key is specified, all the messages are
 * picked up by all the queues bound to the exchange. And hence, all the
 * consumers bound to that exchange. If both the exchange name and the routing
 * key are specified, the consumer picks up messages from the specified queue
 * based on the routing key.
 * 
 * @author anushree
 *
 */
public class RabbitMQConsumer extends EndPoint {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

	private String exchangeName;
	private String routingKey;

	/**
	 * 
	 * @param exchangeName
	 *            takes the exchange name from where to pick up messages
	 * @param routingKey
	 *            takes the routing key that binds the consumer to a queue with
	 *            the specified binding key
	 * @param username
	 *            takes the username
	 * @param password
	 *            takes the password
	 * @throws IOException
	 */
	public RabbitMQConsumer(String exchangeName, String routingKey, String username, String password)
			throws IOException {
		super(username, password);
		logger.info("Entering RabbitMQConsumer constructor");
		this.exchangeName = exchangeName;
		this.routingKey = routingKey;
		logger.info("Exiting RabbitMQConsumer constructor");
	}

	/**
	 * Called by the client to receive a message.
	 * 
	 * @throws IOException
	 */
	public void receieveMessage() throws IOException {
		logger.info("Entering RabbitMQConsumer receive message method");

		if ((exchangeName == null || exchangeName == "") && (routingKey == null || routingKey == ""))
			throw new NullPointerException();

		if ((exchangeName == null || exchangeName == "") && (routingKey != null && routingKey != "")) {
			receiveFromQueue();
		}
		if ((exchangeName != null && exchangeName != "") && (routingKey == null || routingKey == "")) {
			receiveFromFanout();
		}
		if ((exchangeName != null && exchangeName != "") && (routingKey != null && routingKey != "")) {
			receiveFromDirect();
		}

		logger.info("Exiting RabbitMQConsumer receive message method");
	}

	/**
	 * Called when no exchange name is specified
	 * 
	 * @throws IOException
	 */
	protected void receiveFromQueue() throws IOException {
		String queueName = declareQueue();
		receive(queueName);
	}

	/**
	 * Declares a queue and by default, binds it to the default exchange
	 * 
	 * @return Returns the name of the queue that is created
	 * @throws IOException
	 */
	protected String declareQueue() throws IOException {
		return channel.queueDeclare(routingKey, false, false, false, null).getQueue();
	}

	/**
	 * Called when no routing key is specified
	 * 
	 * @throws IOException
	 */
	protected void receiveFromFanout() throws IOException {
		declareFanoutExchange();
		String queueName = declareQueueFanout();
		receive(queueName);
	}

	/**
	 * Declares a fanout exchange
	 * 
	 * @throws IOException
	 */
	protected void declareFanoutExchange() throws IOException {
		channel.exchangeDeclare(exchangeName, "fanout");
	}

	/**
	 * Declares a queue and binds it to the fanout exchange with no routing key
	 * 
	 * @return Returns the name of the String that is created
	 * @throws IOException
	 */
	protected String declareQueueFanout() throws IOException {
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangeName, "");
		return queueName;
	}

	/**
	 * Called when both keys are specified.
	 * 
	 * @throws IOException
	 */
	protected void receiveFromDirect() throws IOException {
		declareDirectExchange();
		String queueName = declareQueueDirect();
		receive(queueName);
	}

	/**
	 * Declares a direct exchange of the specified name
	 * 
	 * @throws IOException
	 */
	protected void declareDirectExchange() throws IOException {
		channel.exchangeDeclare(exchangeName, "direct");
	}

	/**
	 * Declares a queue and binds it to the direct exchange with the specified
	 * binding key
	 * 
	 * @return Returns a String i.e. the name of the queue that is created
	 * @throws IOException
	 */
	protected String declareQueueDirect() throws IOException {
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, exchangeName, routingKey);
		return queueName;
	}

	/**
	 * Receives the messages asynchronously.
	 * 
	 * @param queueName
	 * @throws IOException
	 */
	protected void receive(String queueName) throws IOException {
		logger.info("Entering receive method");
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				logger.info("Received message " + message.toString());
			}
		};
		channel.basicConsume(queueName, true, consumer);
		logger.info("Exiting receive method");
	}

	/**
	 * 
	 * @return
	 */
	public String getExchangeName() {
		return exchangeName;
	}

	/**
	 * 
	 * @param exchangeName
	 */
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	/**
	 * 
	 * @return
	 */
	public String getRoutingKey() {
		return routingKey;
	}

	/**
	 * 
	 * @param routingKey
	 */
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

}
