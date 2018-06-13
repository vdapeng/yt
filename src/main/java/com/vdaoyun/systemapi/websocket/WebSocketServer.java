package com.vdaoyun.systemapi.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketServer {
	
	private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();  
	
	private Session session;
	
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this);
		sendMessage("连接成功");
	}
	
	@OnClose
	public void onClose() {
		webSocketSet.remove(this);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		for (WebSocketServer webSocketServer : webSocketSet) {
			webSocketServer.sendMessage(message);
		}
	}
	
	public void sendMessage(String message) {
		try {
			this.session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendAll(String message) {
		for (WebSocketServer webSocketServer : webSocketSet) {
			webSocketServer.sendMessage(message);
		}
	}
}
