package com.toolkit.email.impl;

import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.MimeMultipart;

import com.toolkit.email.SendEmail;
import com.toolkit.exceptions.InvalidAddressException;

/**
 * This service class is designed to send emails with or without attachments. A
 * user can send a simple mail or an html mail. To send an e-mail the userName,
 * password, host and port fields of the class must be set.
 * 
 * @author Nidhi
 *
 */

public class SendEmailImpl implements SendEmail {

	private static final Logger logger = LoggerFactory.getLogger(SendEmailImpl.class);
	private String userName;
	private String host;
	private String port;

	private Properties properties;
	private Session session;
	private String password;
	private String[] bcc;
	private String[] cc;

	/**
	 * This service class is designed to send emails with or without
	 * attachments. A user can send a simple mail or an html mail. To send an
	 * email the userName, password, host and port fields of the class must be
	 * set.
	 * 
	 * @author Nidhi
	 *
	 */
	public SendEmailImpl() {
		// Default constructor
	}

	/**
	 * @return all the email addresses set to receive blind carbon copy of the
	 *         mail.
	 */
	@Override
	public String[] getBcc() {
		return bcc;
	}

	/**
	 * to set the email addresses to receive blind carbon copy of the mail.
	 * 
	 * @param bcc
	 *            array of strings of email addresses
	 */
	@Override
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return all the email addresses set to receive carbon copy of the mail.
	 */
	@Override
	public String[] getCc() {
		return cc;
	}

	/**
	 * to set the email addresses to receive carbon copy of the mail.
	 * 
	 * @param cc
	 *            array of strings of email addresses
	 */
	@Override
	public void setCc(String[] cc) {
		this.cc = cc;
	}

	/**
	 * @return the set port or 465 as default.
	 */
	@Override
	public String getPort() {
		if (port == null)
			port = "465";
		return port;
	}

	/**
	 * set the port of the mailing server.
	 * 
	 * @param port
	 *            the port of the mailing server.
	 * 
	 */
	@Override
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the userName of the sender
	 */
	@Override
	public String getUserName() {
		return userName;
	}

	/**
	 * set the userName of the sender.
	 * 
	 * @param userName
	 *            username of the sender.
	 * 
	 */
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password of the sender
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/**
	 * set the password of the sender
	 * 
	 * @param password
	 *            the password to set
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * returns properties field if not null else set the default properties.
	 * 
	 * @return the properties set by the user or default properties for
	 *         protocol, host, auth, port etc.
	 */
	@Override
	public Properties getProperties() {
		if (properties == null) {
			properties = new Properties();

			properties.setProperty("mail.transport.protocol", "smtp");
			properties.setProperty("mail.host", getHost());
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", getPort());
			properties.put("mail.debug", "true");
			properties.put("mail.smtp.socketFactory.port", getPort());
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			properties.put("mail.smtp.STARTTLS.enable", "true");
			properties.put("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
			System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
		}
		return properties;
	}

	/**
	 * User may set different properties required by the mailing server in order
	 * to send an email. Properties may include <br>
	 * For eg. certain properties that can be required by the host are : <br>
	 * properties.setProperty("mail.transport.protocol", "smtp");<br>
	 * properties.setProperty("mail.host", getHost());<br>
	 * properties.put("mail.smtp.auth", "true");<br>
	 * properties.put("mail.smtp.port", getPort());<br>
	 * properties.put("mail.debug", "true");<br>
	 * properties.put("mail.smtp.socketFactory.port", getPort());<br>
	 * properties.put("mail.smtp.socketFactory.class",
	 * "javax.net.ssl.SSLSocketFactory");<br>
	 * properties.put("mail.smtp.socketFactory.fallback", "false");<br>
	 * properties.put("mail.smtp.STARTTLS.enable", "true");<br>
	 * properties.put("mail.mime.multipart.ignoreexistingboundaryparameter",
	 * "true");<br>
	 * 
	 * @param properties
	 *            the properties to set
	 */
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * 
	 * @return the session with current userName and password set in the
	 *         session.
	 * @throws InvalidAddressException
	 *             : if userName and password are not set.
	 */
	@Override
	public Session getSession() throws InvalidAddressException {

		if (session == null) {
			if (userName != null && password != null) {
				this.session = Session.getDefaultInstance(getProperties(), new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(getUserName(), getPassword());
					}
				});
			} else {
				throw new InvalidAddressException("Invalid UserName and password");
			}
		}
		return session;
	}

	/**
	 * sets the session field of class {@link Session} to session given by the
	 * user
	 * 
	 * @param session
	 *            the current session to set
	 */
	@Override
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * @return the url of the host if host field is set else returns
	 *         "smtp.gmail.com" as default.
	 */
	@Override
	public String getHost() {

		if (host == null)
			host = "smtp.gmail.com";
		return host;
	}

	/**
	 * @param host
	 *            the host url to set
	 */
	@Override
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * sends a simple mail to the addresses given in toMailAddress and addresses
	 * in the fields cc and bcc of this class.
	 * 
	 * @param toMailAddress
	 *            Addresses to send the mail to.
	 * @param subject
	 *            Subject of the mail.
	 * @param body
	 *            the body of the mail.
	 * 
	 * @throws MessagingException
	 * @throws InvalidAddressException
	 * 
	 */
	@Override
	public void sendSimpleMail(String[] toMailAddress, String subject, String body)
			throws MessagingException, InvalidAddressException {
		
		sendMail(toMailAddress, subject, body, true, false, null);

	}

	/**
	 * sends an http mail to the addresses given in toMailAddress and addresses
	 * in the fields cc and bcc of this class.
	 * 
	 * @param toMailAddress
	 *            Addresses to send the mail to.
	 * @param subject
	 *            Subject of the mail.
	 * @param body
	 *            the body of the mail.
	 * 
	 * @throws MessagingException
	 * @throws InvalidAddressException
	 * 
	 */
	@Override
	public void sendHTMLMail(String[] toMailAddress, String subject, String body)
			throws InvalidAddressException, AddressException, MessagingException {
		
		sendMail(toMailAddress, subject, body, false, false, null);

	}

	/**
	 * sends a simple mail with attachments to the addresses given in
	 * toMailAddress and addresses in the fields cc and bcc of this class.
	 * 
	 * @param toMailAddress
	 *            Addresses to send the mail to.
	 * @param subject
	 *            Subject of the mail.
	 * @param body
	 *            the body of the mail.
	 * @param files
	 *            The file that have to be attached to the mail.
	 * 
	 * @throws MessagingException
	 * @throws InvalidAddressException
	 * 
	 */
	@Override
	public void sendWithAttachment(String[] toMailAddress, String subject, String body, List<DataSource> files)
			throws MessagingException, InvalidAddressException {
		
		sendMail(toMailAddress, subject, body, true, true, files);

	}

	private void sendMail(String[] toMailAddress, String subject, String body, Boolean simple, Boolean attach, List<DataSource> files)
			throws InvalidAddressException, AddressException, MessagingException {
		MimeMessage message = new MimeMessage(getSession());

		message.setFrom(new InternetAddress(getUserName()));

		if (toMailAddress != null && toMailAddress.length > 0) {
			Address address[] = (Address[]) getAddress(toMailAddress);

			if (address == null) {
				throw new InvalidAddressException("Invalid send To Addresses");
			} else
				message.addRecipients(RecipientType.TO, address);
		} else {
			throw new InvalidAddressException("Send To Addresses cannot be null");
		}

		if (getBcc() != null && getBcc().length > 0) {
			Address address[] = (Address[]) getAddress(getBcc());

			if (address != null) {
				message.addRecipients(RecipientType.BCC, address);
			}
		}

		if (getCc() != null && getCc().length > 0) {
			Address address[] = (Address[]) getAddress(getCc());

			if (address != null) {
				message.addRecipients(RecipientType.CC, address);
			}
		}
		message.setSubject(subject);
		if(simple){
			message.setText(body);
		}else{
		message.setContent(body, "text/html");
		}
		
		if(attach){
			MimeMultipart prepareBody = new MimeMultipart();

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html");

			// adding message body
			prepareBody.addBodyPart(messageBodyPart);

			// adding files
			MimeBodyPart attachfile = null;
			if (files != null)
				for (DataSource addFile : files) {
					attachfile = new MimeBodyPart();

					DataSource bds = addFile;
					// bds
					attachfile.setDataHandler(new DataHandler(bds));
					attachfile.setFileName(bds.getName());

					prepareBody.addBodyPart(attachfile);
				}

			message.setContent(prepareBody);
		}
		
		Transport.send(message);
	}

	private InternetAddress[] getAddress(String[] toMailAddress) {
		InternetAddress address[] = null;
		try {
			if (toMailAddress.length == 1) {
				address = new InternetAddress[toMailAddress.length];
				address[0] = new InternetAddress(toMailAddress[0]);

			} else if (toMailAddress.length > 1) {
				int i = 0;
				address = new InternetAddress[toMailAddress.length];
				for (String mailAddress : toMailAddress) {
					address[i] = new InternetAddress(mailAddress);
					i++;
				}

			}
			return address;
		} catch (AddressException e) {
			logger.info(e.toString(), e);
			return null;
		}

	}

}
