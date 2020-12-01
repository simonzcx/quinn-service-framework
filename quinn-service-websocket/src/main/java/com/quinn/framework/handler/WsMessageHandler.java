package com.quinn.framework.handler;

import com.alibaba.fastjson.JSONObject;
import com.quinn.framework.model.WsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket消息处理类
 * @Author: Simon.z
 * @since: 2020-11-28
 */
public class WsMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(WsMessageHandler.class);

    /**
     * 会话
     */
    public static Map<String, Map<String, WebSocketSession>> sessionMapMap = new ConcurrentHashMap<>();

    /**
     * 添加会话
     *
     * @param userKey 用户编码
     * @param address 地址
     * @param session 会话
     */
    public static void addSession(String userKey, String address, WebSocketSession session) {
        Map<String, WebSocketSession> sessionMap = sessionMapMap.get(userKey);
        if (sessionMap == null) {
            sessionMap = new ConcurrentHashMap<>(1);
            sessionMapMap.put(userKey, sessionMap);
        }

        sessionMap.put(address, session);
    }

    /**
     * 移除会话
     *
     * @param userKey 用户编码
     * @param address 地址
     */
    public static void removeSession(String userKey, String address) {
        Map<String, WebSocketSession> sessionMap = sessionMapMap.get(userKey);
        if (sessionMap == null) {
            return;
        }

        sessionMap.remove(address);
        if (CollectionUtils.isEmpty(sessionMap)) {
            sessionMapMap.remove(userKey);
        }
    }

    /**
     * 向内处理
     *
     * @param param   参数
     * @param session 会话
     * @return 处理结果
     */
    public static boolean handleInn(WsMessage param, WebSocketSession session) {
        logger.info("【WebSocket】收到客户端发来的消息：{}", param.getData());
        return false;
    }

    /**
     * 向外处理
     *
     * @param WSMessage 参数
     * @param session   用户名
     * @return 处理结果
     */
    public static boolean handleOut(WsMessage WSMessage, WebSocketSession session) {
        try {
            //session.getBasicRemote().sendText(JSONObject.toJSONString(WSMessage));
            session.sendMessage(new TextMessage(JSONObject.toJSONString(WSMessage)));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 向外处理
     *
     * @param WSMessage 参数
     * @param userKey   用户名
     * @return 处理结果
     */
    public static boolean handleOut(WsMessage WSMessage, String userKey) {
        Map<String, WebSocketSession> sessionMap = sessionMapMap.get(userKey);
        if (sessionMap == null) {
            return false;
        }

        String text = JSONObject.toJSONString(WSMessage);
        for (Map.Entry<String, WebSocketSession> entry : sessionMap.entrySet()) {
            try {
                //entry.getValue().getBasicRemote().sendText(text);
                entry.getValue().sendMessage(new TextMessage(text));
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * 广播消息
     *
     * @param WSMessage 消息
     */
    public static void boardCast(WsMessage WSMessage) {
        String text = JSONObject.toJSONString(WSMessage);
        for (Map.Entry<String, Map<String, WebSocketSession>> mapEntry : sessionMapMap.entrySet()) {
            Map<String, WebSocketSession> sessionMap = mapEntry.getValue();
            if (sessionMap != null) {
                for (Map.Entry<String, WebSocketSession> entry : sessionMap.entrySet()) {
                    try {
                        //entry.getValue().getBasicRemote().sendText(text);
                        entry.getValue().sendMessage(new TextMessage(text));
                    } catch (IOException e) {
                        // DO NOTHING
                    }
                }
            }
        }
    }
}
