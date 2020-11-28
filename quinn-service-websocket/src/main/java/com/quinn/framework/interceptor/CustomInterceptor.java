package com.quinn.framework.interceptor;

import com.quinn.framework.util.WsParamName;
import com.quinn.util.base.StringUtil;
import com.quinn.util.base.api.LoggerExtend;
import com.quinn.util.base.factory.LoggerExtendFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自定义拦截器拦截WebSocket请求
 *
 * @Author: Simon.z
 * @since: 2020-11-28
 */
public class CustomInterceptor implements HandshakeInterceptor {
    private static final LoggerExtend LOGGER = LoggerExtendFactory.getLogger(CustomInterceptor.class);

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes
    ) throws Exception {
        LOGGER.error("握手拦截器前置拦截~~");
        if(!(request instanceof ServletServerHttpRequest)){
            return false;
        }
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String userName = servletRequest.getSession().getAttribute(WsParamName.PARAM_TYPE_OF_USER_NAME).toString();
        if(StringUtil.isEmpty(userName)){
            LOGGER.error("用户登录已失效");
            return false;
        } else {
            attributes.put(WsParamName.PARAM_TYPE_OF_USER_KEY, userName);
            LOGGER.error("用户：" + userName + "成功！");
        }
        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception e
    ) {
        LOGGER.error("握手拦截器后置拦截~~");
    }
}
