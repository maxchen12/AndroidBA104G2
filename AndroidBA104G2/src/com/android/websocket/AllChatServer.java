package com.android.websocket;

import java.io.*;
import java.util.*;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.simple.JSONObject;

import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;

@ServerEndpoint("/AllChatServer/{userID}/{type}")
public class  AllChatServer {
	private static final Set<Session> connectedSessions = Collections.synchronizedSet(new HashSet<>());
	private static final Map<String, Set<Session>> combineSessions = Collections.synchronizedMap(new HashMap<>());
	
	
	@OnOpen
	synchronized public void onOpen(@PathParam("userID") String userID, Session userSession, @PathParam("type") String type) throws IOException {
		
		
		
		//-----------broadcast get loc info
		if("map".equals(type)){
			connectedSessions.add(userSession);
			JSONObject json = new JSONObject();	
			json.put("action", "askLoc");
			for (Session session : connectedSessions) {
				if (session.isOpen())
					session.getAsyncRemote().sendText(json.toString());
			}
		}	
		//-----------end broadcast disconnect info
		else if(type.contains("OM")){
			if(combineSessions.containsKey(type)){
				combineSessions.get(type).add(userSession);
			}else{
				Set<Session> ss = Collections.synchronizedSet(new HashSet<>());
				ss.add(userSession);
				combineSessions.put(type, ss);
			}
			JSONObject json = new JSONObject();	
			json.put("action", "acceptBuy");
			json.put("memName", userID);
			for(Session session : combineSessions.get(type)){
				if(session.isOpen())
					session.getAsyncRemote().sendText(json.toString());
			}
			
		}else{
			connectedSessions.add(userSession);
		}
				
		String text = String.format("Session ID = %s, connected; userName = %s", 
				userSession.getId(), userID);
		System.out.println(text);
	}

	@OnMessage
	synchronized public void onMessage(Session userSession, String message,@PathParam("type") String type) {
		
		if(type.contains("OM")){
			for(Session session : combineSessions.get(type)){
				if(session.isOpen())
					session.getAsyncRemote().sendText(message);
			}
			
			if(message.contains("buyFinish")){
				for (Session session : connectedSessions) {
					if (session.isOpen())
						session.getAsyncRemote().sendText(message);
				}
			}
		
			
		}else{
			for (Session session : connectedSessions) {
				if (session.isOpen())
					session.getAsyncRemote().sendText(message);
			}
			
		}
		
		System.out.println("Message received: " + message);
	}

	@OnClose
	synchronized public void onClose(Session userSession, CloseReason reason, @PathParam("userID") String userID, @PathParam("type") String type) {
		
		
		if(type.contains("OM")){
			combineSessions.get(type).remove(userSession);
		}else{
			connectedSessions.remove(userSession);
			
			JSONObject json = new JSONObject();	
			//-----------broadcast disconnect info
			if("map".equals(type)){
				
				json.put("action", "mapOff");
				json.put("memID", userID);
				for (Session session : connectedSessions) {
					if (session.isOpen())
						session.getAsyncRemote().sendText(json.toString());
				}
			}
		}
		
		
		
		//-----------end broadcast disconnect info
		
		String text = String.format("session ID = %s, disconnected; close code = %d" , userSession.getId(),
				reason.getCloseCode().getCode());
		System.out.println(text);
	}
	
	@OnError
	synchronized public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

}
