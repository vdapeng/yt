package com.vdaoyun.systemapi.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WsOnlineHandler extends TextWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(WsOnlineHandler.class);
	
	private static Integer onLineCount = 0;
	
	private static CopyOnWriteArraySet<WebSocketSession> webSocketSet = new CopyOnWriteArraySet<WebSocketSession>();  

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		webSocketSet.remove(session);
		--onLineCount;
		log.info("会话丢失，当前在线人数" + onLineCount);
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		webSocketSet.add(session);
		++onLineCount;
		log.info("建立新的会话，当前在线人数" + onLineCount);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		log.info("接收到新的消息" + message.getPayload());
	}
	
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		super.handleMessage(session, message);
		log.info("接收到新的消息" + message.getPayload());
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		super.handleTransportError(session, exception);
		log.info("会话异常" + exception.getMessage());
	}
	
	/**
	 * 
	 * @Title: sendAll
	 *  
	 * @Description: 广播，给所有在线的用户推送消息
	 *  
	 * @param message void
	 */
	public void sendAll(String message) {
		log.info("消息广播，当前在线人数" + onLineCount);
		TextMessage textMessage = new TextMessage(message.getBytes());
		for (WebSocketSession webSocketSession : webSocketSet) {
			try {
				webSocketSession.sendMessage(textMessage);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
