package com.toolkit.jms.rabbitmqjms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extends the EndPoint class. This class defines a producer endpoint for the
 * messaging broker. Produces message to exchanges. RabbitMQ provides four types
 * of exchanges: default, direct, fanout, topic. A producer only produces to an
 * exchange, thus, only the exchange name is set in the producer object.
 * 
 * @author anushree
 *
 */
public class RabbitMQProducer extends EndPoint {

	private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);

	private String exchangeName;

	/**
	 * Creates a producer object
	 * 
	 * @param username
	 *            Takes the username for the endpoint
	 * @param password
	 *            Takes the password for the endpoint
	 * @throws IOException
	 */
	public RabbitMQProducer(String username, String password) throws IOException {
		super(username, password);
		logger.info("In constructor");
	}

	/**
	 * Creates a producer object
	 * 
	 * @param username
	 *            Takes the username for the endpoint
	 * @param password
	 *            Takes the password for the endpoint
	 * @param exchangeName
	 *            Takes the name of the exchange the producer will publish
	 *            messages to.
	 * @throws IOException
	 */
	public RabbitMQProducer(String username, String password, String exchangeName) throws IOException {
		super(username, password);
		logger.info("In constructor that takes exchange name");
		this.exchangeName = exchangeName;
	}

	/**
	 * Publishes messages to exchanges
	 * 
	 * @param routingKey
	 *            If set, publishes messages to the exchange will the same
	 *            routing key.
	 * @param message
	 *            The message to be sent
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void publishMessage(String routingKey, String message) throws IOException {
		logger.info("Entering publish message method");
		if ((exchangeName == null || exchangeName == "") && (routingKey != null)) {
			publishToQueue(routingKey, message);
		}

		if ((exchangeName != null || exchangeName != "") && (routingKey == null || routingKey == "")) {
			publishToFanoutExchange(message);
		}

		if ((exchangeName != null && exchangeName != "") && (routingKey != null && routingKey != "")) {
			publishToDirectExchange(routingKey, message);
		}
		logger.info("Exiting publish message method");
	}

	/**
	 * Used when no exchange name is provided, in which case, the messages are
	 * bound to the default exchange (with no name). All queues are bound to the
	 * default exchange. This method declares a queue that is bound to the
	 * default exchange and has the binding key provided. Any consumer can pick
	 * up messages from this queue if it knows the binding key.
	 * 
	 * @param bindingKey
	 * @throws IOException
	 */
	protected void declareQueue(String bindingKey) throws IOException {
		channel.queueDeclare(bindingKey, false, false, false, null);
	}

	/**
	 * This method publishes messages to the default exchange (since it is not
	 * set) with the specified binding key.
	 * 
	 * @param bindingKey
	 *            Basically it is the queue name that is bound to the default
	 *            exchange where the messages will be published.
	 * @param message
	 *            The message to be sent.
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	protected void publishToQueue(String bindingKey, String message) throws IOException {
		declareQueue(bindingKey);
		channel.basicPublish("", bindingKey, null, message.getBytes("UTF-8"));
		logger.info("Message sent");
	}

	/**
	 * Declares a fanout exchange. In a fanout exchange all listeners of the
	 * exchange get to listen to all the messages produced to the exchange.
	 * While publishing to a fanout exchange, no routing key (i.e.queue name) is
	 * provided.
	 * 
	 */
	protected void declareFanoutExchange() {
		try {
			channel.exchangeDeclare(exchangeName, "fanout");
		} catch (IOException e) {
			logger.error("IOException", e);
		}
	}

	/**
	 * Declares a direct exchange. In a direct exchange all listeners of the
	 * exchange get to listen to selective messages that get filtered based on
	 * the routing keys of the messages they are disp While publishing to a
	 * fanout exchange, no routing key (i.e.queue name) is provided.
	 * 
	 * @throws IOException
	 */
	protected void declareDirectExchange() throws IOException {
		channel.exchangeDeclare(exchangeName, "direct");
	}

	/**
	 * Publishes a message to a fanout exchange.
	 * 
	 * @param exchangeName
	 * @param message
	 * @throws IOException
	 */
	protected void publishToFanoutExchange(String message) throws IOException {
		declareFanoutExchange();
		channel.basicPublish(exchangeName, "", null, message.getBytes());
	}

	/**
	 * Publishes a message to a direct exchange.
	 * 
	 * @param bindingKey
	 * @param message
	 */
	protected void publishToDirectExchange(String bindingKey, String message) {
		try {
			declareDirectExchange();
		} catch (IOException e1) {
			logger.error("IOException", e1);
		}
		try {
			channel.basicPublish(exchangeName, bindingKey, null, message.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
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

}