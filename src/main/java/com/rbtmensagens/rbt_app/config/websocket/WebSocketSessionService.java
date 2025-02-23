package com.rbtmensagens.rbt_app.config.websocket;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class WebSocketSessionService {
    private final ConcurrentHashMap<String, String> sessionUserMap = new ConcurrentHashMap<>();

    public void addSession(String sessionId, String userId) {
        sessionUserMap.put(sessionId, userId);
    }

    public void removeSession(String sessionId) {
        sessionUserMap.remove(sessionId);
    }

    public String getUserId(String sessionId) {
        return sessionUserMap.get(sessionId);
    }
}