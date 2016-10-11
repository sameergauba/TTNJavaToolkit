package com.toolkit.websocket;

import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.toolkit.webSocket.WebSocket;

public class WebSocketTest {
	
	Session ses = Mockito.mock(Session.class);
	Basic bas = Mockito.mock(Basic.class);
	
	@Before
	public void beforeTest(){
		Mockito.when(ses.getBasicRemote()).thenReturn(bas);
	}
	
	@Test
	public void sendMessageTest(){
		WebSocket webSocket = new WebSocketImpl();
		webSocket.sessionStart(ses, "123");
		webSocket.sessionStart(ses, "123");
		WebSocket.sendMessage("123", "name:sameer");
		webSocket.endSession(ses, "123");
		webSocket.endSession(null, "123");
	}
	
	@Test
	public void errorOccurTest() throws Throwable{
		WebSocket webSocket = new WebSocketImpl();
		webSocket.errorOccur(new Exception());
	}
	
	@Test
	public void connectionsNullTest(){
		WebSocket.sendMessage("123", "sameData");
	}
	
	class WebSocketImpl extends WebSocket{
		
	}

}
