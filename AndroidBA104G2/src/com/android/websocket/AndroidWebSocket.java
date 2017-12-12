package com.android.websocket;


import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;



	

@ServerEndpoint("/AndroidWebSocket/{userName}")
	public class AndroidWebSocket  {
		private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());

		@OnOpen
		public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
			connectedSessions.add(userSession);
			String text = String.format("Session ID = %s, connected; userName = %s", 
					userSession.getId(), userName);
			System.out.println(text);
		}

		@OnMessage
		public void onMessage(Session userSession, String message) {
			for (Session session : connectedSessions) {
				if (session.isOpen())
					session.getAsyncRemote().sendText(message);
			}
			System.out.println("Message received: " + message);
		}

		@OnClose
		public void onClose(Session userSession, CloseReason reason) {
			connectedSessions.remove(userSession);
			String text = String.format("session ID = %s, disconnected; close code = %d", userSession.getId(),
					reason.getCloseCode().getCode());
			System.out.println(text);
		}
		
		@OnError
		public void onError(Session userSession, Throwable e) {
			System.out.println("Error: " + e.toString());
		}

}


