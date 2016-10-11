package com.toolkit.email;

import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;

import com.toolkit.exceptions.InvalidAddressException;

/**
 * 
 * @author Nidhi
 *
 */
public interface SendEmail {

	/**
	 * @return all the email addresees set to recieve blind carbon copy of the
	 *         mail.
	 */
	String[] getBcc();

	/**
	 * to set the email addresses to receive blind carbon copy of the mail.
	 * 
	 * @param bcc
	 *            array of strings of email addresses
	 */
	void setBcc(String[] bcc);

	/**
	 * @return all the email addresees set to recieve carbon copy of the mail.
	 */
	String[] getCc();

	/**
	 * to set the email addresses to receive carbon copy of the mail.
	 * 
	 * @param cc
	 *            array of strings of email addresses
	 */
	void setCc(String[] cc);

	/**
	 * @return the set port or 465 as default.
	 */
	String getPort();

	/**
	 * set the port of the mailing server.
	 * 
	 * @param port
	 *            the port of the mailing server.
	 * 
	 */
	void setPort(String port);

	/**
	 * @return the userName of the sender
	 */
	String getUserName();

	/**
	 * set the userName of the sender.
	 * 
	 * @param userName
	 *            username of the sender.
	 * 
	 */
	void setUserName(String userName);

	/**
	 * @return the password of the sender
	 */
	String getPassword();

	/**
	 * set the password of the sender
	 * 
	 * @param password
	 *            the password to set
	 */
	void setPassword(String password);

	/**
	 * returns properties field if not null else set the default properties.
	 * 
	 * @return the properties set by the user or default properties for
	 *         protocol, host, auth, port etc.
	 */
	Properties getProperties();

	/**
	 * User may set different properties required by the mailing server in order
	 * to send an email. Properties may include <br>
	 * For eg. certan properties that can be required by the host are : <br>
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
	void setProperties(Properties properties);

	/**
	 * 
	 * @return the session with current userName and password set in the
	 *         session.
	 * @throws InvalidAddressException
	 *             : if userName and password are not set.
	 */
	Session getSession() throws InvalidAddressException;

	/**
	 * sets the session field of class {@link Session} to session given by the
	 * user
	 * 
	 * @param session
	 *            the current session to set
	 */
	void setSession(Session session);

	/**
	 * @return the url of the host if host field is set else returns
	 *         "smtp.gmail.com" as default.
	 */
	String getHost();

	/**
	 * @param host
	 *            the host url to set
	 */
	void setHost(String host);

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
	void sendSimpleMail(String[] toMailAddress, String subject, String body)
			throws MessagingException, InvalidAddressException;

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
	void sendHTMLMail(String[] toMailAddress, String subject, String body)
			throws InvalidAddressException, AddressException, MessagingException;

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
	void sendWithAttachment(String[] toMailAddress, String subject, String body, List<DataSource> files)
			throws MessagingException, InvalidAddressException;

}
