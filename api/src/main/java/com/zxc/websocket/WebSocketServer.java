package com.zxc.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wahaha
 */
@Component
@ServerEndpoint("/webSocket/{oid}")
public class WebSocketServer {

    private static ConcurrentHashMap<String , Session> sessionMap = new ConcurrentHashMap<>(8);

    /**
     * 前端发送请求,建立websocket连接,就会执行@onopen标注的方法
     */
    @OnOpen
    public void open(@PathParam("oid") String orderId, Session session){
        sessionMap.put(orderId, session);
    }

    /**
     * 前端关闭页面或者关闭websocket连接就会执行close
     */
    @OnClose
    public void close(@PathParam("oid") String orderId){
        sessionMap.remove(orderId);
    }

    public static void sendMsg(String orderId, String msg){
        Session session = sessionMap.get(orderId);
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
