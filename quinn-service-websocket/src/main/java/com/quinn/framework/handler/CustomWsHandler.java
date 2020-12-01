package com.quinn.framework.handler;

import com.alibaba.fastjson.JSON;
import com.quinn.framework.model.WsMessage;
import com.quinn.framework.util.WsParamName;
import com.quinn.framework.util.enums.WsCommandInner;
import com.quinn.framework.util.enums.WsCommandOuter;
import com.quinn.util.base.StringUtil;
import com.quinn.util.constant.enums.MessageLevelEnum;
import com.quinn.util.constant.enums.UrgentLevelEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Author: Simon.z
 * @since: 2020-11-28
 */
@Component
public class CustomWsHandler implements WebSocketHandler {

    /**
     * 用户编码（确定不同的连接是不同的当前对象）
     */
    private String userKey;

    /**
     * 客户端地址（确定不同的连接是不同的当前对象）
     */
    private String address;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.userKey = session.getAttributes().get(WsParamName.PARAM_TYPE_OF_USER_KEY).toString();
        this.address = session.getRemoteAddress().toString();
        WsMessageHandler.addSession(userKey, address, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = message.getPayload().toString();

        WsMessage param;
        if (StringUtil.isJson(payload)) {
            param = JSON.parseObject(message.getPayload().toString(), WsMessage.class);
        } else {
            param = new WsMessage(WsCommandInner.NOTIFY.name(), UrgentLevelEnum.NORMAL.code, payload);
        }
        WsMessageHandler.handleInn(param, session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable error) throws Exception {
        if (session.isOpen()) {
            WsMessageHandler.handleOut(new WsMessage(WsCommandOuter.MESSAGE.name(),
                    MessageLevelEnum.ERROR.status, error.getMessage()), session);
        } else {
            WsMessageHandler.removeSession(this.userKey, this.address);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        WsMessageHandler.removeSession(this.userKey, this.address);
    }

    @Override
    public boolean supportsPartialMessages() {
        // WebSocketHandler是否处理部分消息，没什么用 返回false
        return false;
    }

}
