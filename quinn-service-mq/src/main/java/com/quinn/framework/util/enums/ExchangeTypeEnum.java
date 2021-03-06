package com.quinn.framework.util.enums;

/**
 * 消息发送：交换器类型
 *
 * @author Qunhua.Liao
 * @since 2020-05-27
 */
public enum ExchangeTypeEnum {

    // 交换器类型：订阅发送 - 有路由
    TOPIC,

    // 直接发送 - 抢签
    DIRECT,

    // 1 - N 无路由
    FANOUT
}
