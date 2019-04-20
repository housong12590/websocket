package com.example.websocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket")
@Component
public class WebSocket {

    private Session session;
    private Logger logger = LoggerFactory.getLogger(WebSocket.class);

    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    public WebSocket() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Scanner scan = new Scanner(System.in);
                while (true) {
                    String data = scan.nextLine();
                    if (data.equals("json")) {
                        data = FileUtil.read("D:\\work\\java\\escpos4j\\src\\main\\resources\\data.json");
                    }
                    sendMessage(data);
                }
            }
        });
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        logger.info("【websocket消息】 有新的连接，总数：{}", webSocketSet.size());
//        String data = FileUtil.read("D:\\work\\java\\escpos4j\\src\\main\\resources\\data.json");
//        sendMessage(data);
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        logger.info("【websocket消息】 连接断开，总数：{}", webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("【websocket消息】 收到客户端发来的消息：{}", message);
//        sendMessage(message);
    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSocketSet) {
            logger.info("【websocket消息】 广播消息，message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
