package com.quinn.framework.util.enums;

/**
 * 策略枚举（取值、判断、选项：策略组的概念）
 *
 * @author Qunhu.Liao
 * @since 2020-04-25
 */
public enum StrategyTypeEnum {

    // Get外部请求
    HTTP_GET,

    // Post外部请求
    HTTP_POST,

    // Spring Bean 方法
    METHOD_BEAN,

    // 静态方法
    METHOD_STATIC,

    // 参数直接取值
    PARAM_VALUE,

    // 常量直接取值
    CONSTANT_VALUE,

    // 参数解析
    PARAM_RESOLVE,

}
