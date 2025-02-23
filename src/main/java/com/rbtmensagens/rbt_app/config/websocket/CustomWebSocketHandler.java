package com.rbtmensagens.rbt_app.config.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
	
	private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();		
	
	private static final Map<WebSocketSession, String> userSessions = new ConcurrentHashMap<>();
	
	private final ConcurrentHashMap<String, String> sessionUserMap = new ConcurrentHashMap<>();
	
    private final WebSocketSessionService sessionService;

    public CustomWebSocketHandler(WebSocketSessionService sessionService) {
        this.sessionService = sessionService;
    }	

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {    	
	sessions.add(session);
        String username = (String) session.getAttributes().get("userId");

        if (username != null) {
        	if (!userSessions.containsValue(username)) {
        		userSessions.put(session, username);
        	}
            System.out.println("✅ Usuário conectado: " + username);
            broadcast("🔔 " + username + " entrou no chat!");            
        } else {
            System.out.println("⚠️ Nenhum usuário encontrado no WebSocketSession!");
        }
    }
         
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	String username = userSessions.get(session);
    	if (username != null) {
            System.out.println("📩 Mensagem de " + username + ": " + message.getPayload());            
            broadcast("💬 " + username + ": " + message.getPayload());            
        } else {
            System.out.println("⚠️ Usuário não encontrado para a sessão!");
        }                
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);        
        String username = userSessions.get(session);
        System.out.println("🔴 Usuário desconectado: " + username);        
        broadcast("❌ " + username + " saiu do chat.");
        userSessions.remove(session);        
    }   
    
    public String getUserId(String sessionId) {
        return sessionUserMap.get(sessionId);
    }    
    
    private void broadcast(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }  
    
    public static int getActiveUsersCount() {
        return userSessions.size();
    }    
    
    public static List<String> getActiveUsersList() {
        return new ArrayList<>(userSessions.values());
    }    
       
}
