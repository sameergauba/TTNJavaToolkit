package com.toolkit.jms.rabbitmqjms;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * This class defines the endpoints of the RabbitMQ broker. The endpoints
 * include the producer and the consumer of the messages/tasks.
 * 
 * @author anushree
 *
 */
public class EndPoint {

	private static final Logger logger = LoggerFactory.getLogger(EndPoint.class);

	protected Channel channel;
	protected Connection connection;
	protected String username;
	protected String password;

	/**
	 * This constructor creates a connection and a channel for an endpoint
	 * object created
	 * 
	 * @param username
	 *            takes the username of the user. RabbitMQ provides "guest" as
	 *            the default.
	 * @param password
	 *            takes the password of the user. RabbitMQ provides "guest" as
	 *            the default
	 * @throws IOException
	 */
	public EndPoint(String username, String password) throws IOException {
		logger.info("Entering Endpoint constructor");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername(username);
		factory.setPassword(password);
		connection = factory.newConnection();
		channel = connection.createChannel();

		logger.info("Exiting Endpoint constructor");
	}

	/**
	 * This constructor creates a connection and a channel for an endpoint
	 * object created
	 * 
	 * @throws IOException
	 */
	public EndPoint() throws IOException {
		logger.info("Entering default Endpoint constructor");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();

		logger.info("Exiting default Endpoint constructor");
	}

	/**
	 * Closes the channel and connection for an endpoint object
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		logger.info("Entering close method");
		this.channel.close();
		this.connection.close();
		logger.info("Exiting close method");
	}

	/**
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
