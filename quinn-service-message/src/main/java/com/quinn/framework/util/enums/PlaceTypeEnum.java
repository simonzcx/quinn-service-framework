package com.quinn.framework.util.enums;

/**
 * 消息占位类型枚举类
 *
 * @author Qunhua.Liao
 * @since 2020-02-12
 */
public enum PlaceTypeEnum {

    // 内容
    CONTENT(1),

    // 主题
    SUBJECT(2),

    // 路径
    URL(4),

    // 附件
    ATTACHMENT(8),

    ;

    /**
     * 位存储标识
     */
    public final int code;

    PlaceTypeEnum(int code) {
        this.code = code;
    }

    /**
     * 编码转为名称
     *
     * @param code 编码
     * @return 名称
     */
    public static String codeToName(int code) {
        for (PlaceTypeEnum e : values()) {
            if (code == e.code) {
                return e.name();
            }
        }
        return null;
    }

    /**
     * 名称转换为编码
     *
     * @param name 名称
     * @return 编码
     */
    public static int nameToCode(String name) {
        for (PlaceTypeEnum e : values()) {
            if (e.name().equals(name)) {
                return e.code;
            }
        }
        return -1;
    }

}
