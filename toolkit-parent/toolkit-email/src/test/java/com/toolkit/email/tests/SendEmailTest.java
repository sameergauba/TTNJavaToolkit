package com.toolkit.email.tests;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.toolkit.email.impl.SendEmailImpl;
import com.toolkit.exceptions.InvalidAddressException;

public class SendEmailTest {
	private static File file;

	@BeforeClass
	public static void beforeTest() throws IOException {
		file = new File("wrapper");
		file.createNewFile();
	}

	@Test
	public void sendSimpleMail() {
		SendEmailImpl sender = new SendEmailImpl();
		sender.setUserName("link.sharing.java@gmail.com");
		sender.setPassword("linksharingjava");
		String[] recipients = new String[] { "link.sharing.java@gmail.com", "link.sharing.java@gmail.com" };
		sender.setBcc(new String[] { "link.sharing.java@gmail.com" });
		sender.setCc(new String[] { "link.sharing.java@gmail.com" });
		sender.setPort(sender.getPort());
		sender.setProperties(sender.getProperties());
		sender.setHost(sender.getHost());
		try {
			sender.setSession(sender.getSession());
			sender.sendSimpleMail(recipients, "Mailer", "Nothing");
		} catch (MessagingException | InvalidAddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void sendHtmlMail() {
		SendEmailImpl sender = new SendEmailImpl();
		sender.setUserName("link.sharing.java@gmail.com");
		sender.setPassword("linksharingjava");
		String[] recipients = new String[] { "link.sharing.java@gmail.com", "link.sharing.java@gmail.com" };
		sender.setBcc(new String[] { "link.sharing.java@gmail.com" });
		sender.setCc(new String[] { "link.sharing.java@gmail.com" });
		try {
			sender.sendHTMLMail(recipients, "Mailer", "Nothing");
		} catch (InvalidAddressException | MessagingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sendAttachmentMail() {
		SendEmailImpl sender = new SendEmailImpl();
		sender.setUserName("link.sharing.java@gmail.com");
		sender.setPassword("linksharingjava");
		String[] recipients = new String[] { "link.sharing.java@gmail.com", "link.sharing.java@gmail.com" };
		sender.setBcc(new String[] { "link.sharing.java@gmail.com" });
		sender.setCc(new String[] { "link.sharing.java@gmail.com" });
		DataSource fds = new FileDataSource(file);
		List<DataSource> dataSources = new LinkedList<>();
		dataSources.add(fds);
		try {
			sender.sendWithAttachment(recipients, "Mailer", "Nothing", dataSources);
		} catch (MessagingException | InvalidAddressException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = InvalidAddressException.class)
	public void nullMailAddressesTest() throws AddressException, MessagingException, InvalidAddressException {
		SendEmailImpl sender = new SendEmailImpl();
		sender.setUserName("link.sharing.java@gmail.com");
		sender.setPassword("linksharingjava");
		sender.sendSimpleMail(null, "Mailer", "Nothing");
	}

	@Test(expected = InvalidAddressException.class)
	public void nullMailAddressesTest2() throws AddressException, InvalidAddressException, MessagingException {
		SendEmailImpl sender = new SendEmailImpl();
		sender.setUserName("link.sharing.java@gmail.com");
		sender.setPassword("linksharingjava");
		sender.sendHTMLMail(null, "Mailer", "Nothing");
	}

	@Test(expected = InvalidAddressException.class)
	public void nullMailAddressesTest3() throws MessagingException, InvalidAddressException {
		SendEmailImpl sender = new SendEmailImpl();
		sender.setUserName("link.sharing.java@gmail.com");
		sender.setPassword("linksharingjava");
		sender.sendWithAttachment(null, "Mailer", "Nothing", null);
	}

	@Test
	public void exceptionCodeCoverageTest() {
		InvalidAddressException exception = new InvalidAddressException();
		exception = new InvalidAddressException("Invalid Address");
		exception = new InvalidAddressException(exception);
		exception = new InvalidAddressException("Invalid address", exception);
		exception = new InvalidAddressException("Invalid address", exception, true, true);
	}

	@AfterClass
	public static void afterTest() {
		file = new File("wrapper");
		file.delete();
	}

}
