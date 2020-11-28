package com.quinn.framework.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * WebSocket消息
 *
 * @Author: Simon.z
 * @since: 2020-11-28
 */
@Setter
@Getter
public class WsMessage {

    public WsMessage() {
    }

    public WsMessage(String command, Integer priority, Object data) {
        this.command = command;
        this.priority = priority;
        this.data = data;
    }

    /**
     * 命令
     */
    @ApiModelProperty("命令")
    private String command;

    /**
     * 优先级
     */
    @ApiModelProperty("优先级")
    private Integer priority;

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private Object data;
}
