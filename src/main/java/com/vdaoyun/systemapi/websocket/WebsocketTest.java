package com.vdaoyun.systemapi.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ws/test")
public class WebsocketTest {
	
	@Autowired
	private WsHandler server;
	
	@GetMapping
	public void send(@RequestParam String message) {
		server.sendAll(message);
	}

}
