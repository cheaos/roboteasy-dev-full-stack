package com.rbtmensagens.rbt_app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbtmensagens.rbt_app.config.websocket.CustomWebSocketHandler;

@CrossOrigin(origins = "*")
@RestController
public class WebSocketController {
	
	@GetMapping("/active-users")
	public List<String> getActiveUsersList() {
		return CustomWebSocketHandler.getActiveUsersList();
	}
	
	@GetMapping("/active-listusers")
	public int getActiveUsersCount() {
		return CustomWebSocketHandler.getActiveUsersCount();
	}	

}
