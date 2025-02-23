package com.rbtmensagens.rbt_app.config.websocket;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class WebSocketInterceptor implements HandshakeInterceptor {
	
    private final WebSocketSessionService sessionService;	
	 
    public WebSocketInterceptor(WebSocketSessionService sessionService) {
	        this.sessionService = sessionService;
	    }	 

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
    							   WebSocketHandler wsHandler, Map<String, Object> attributes) {       	
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String query = servletRequest.getServletRequest().getQueryString();

            String token = null;
            if (query != null && query.contains("token=")) {
                token = query.split("token=")[1].split("&")[0];
            }

            if (token == null || token.isEmpty()) {
                System.out.println("🚫 Token não encontrado!");
                return false;
            }          
                        
            try {                
                DecodedJWT decodedJWT = JWT.decode(token);
                String userId = decodedJWT.getSubject();

                if (userId == null) {
                    System.out.println("🚫 Token inválido: Sem userId!");
                    return false;
                }

                attributes.put("userId", userId);
                System.out.println("✅ WebSocket autenticado para userId: " + userId);

            } catch (Exception e) {
                System.out.println("🚫 Erro ao validar token JWT: " + e.getMessage());
                return false;
            }
        }
        return true;
    }      

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    	System.out.println("🤝 Handshake finalizado.");    	
    }
}