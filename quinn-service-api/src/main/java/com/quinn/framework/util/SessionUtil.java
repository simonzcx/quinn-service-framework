package com.quinn.framework.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quinn.util.base.api.Strategy;
import com.quinn.util.constant.ConfigConstant;
import com.quinn.util.constant.NumberConstant;
import com.quinn.util.constant.StringConstant;
import com.quinn.util.constant.enums.LanguageEnum;
import com.quinn.util.constant.enums.RoleTypeEnum;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 会话信息获取类
 *
 * @author Qunhua.Liao
 * @since 2020-02-10
 */
public class SessionUtil {

    private static ThreadLocal<Map<String, Object>> content = new ThreadLocal<>();

    public static final String SUPER_ADMIN_ROLE_NAME;

    public static final String ORG_ADMIN_ROLE_NAME;

    static {
        SUPER_ADMIN_ROLE_NAME = System.getProperty(ConfigConstant.PROP_KEY_OF_SUPER_ADMIN_ROLE_NAME,
                ConfigConstant.DEFAULT_SUPER_ADMIN_ROLE_NAME);

        ORG_ADMIN_ROLE_NAME = System.getProperty(ConfigConstant.PROP_KEY_OF_ORG_ADMIN_ROLE_NAME,
                ConfigConstant.DEFAULT_ORG_ADMIN_ROLE_NAME);
    }

    /**
     * 会话信息键：角色
     */
    public static final String SESSION_KEY_ROLE = "SESSION_KEY_ROLE";

    /**
     * 会话信息键：权限
     */
    public static final String SESSION_KEY_PERMISSION = "SESSION_KEY_PERMISSION";

    /**
     * 会话信息键：用户信息-Map
     */
    public static final String SESSION_KEY_AUTH_INFO = "SESSION_KEY_AUTH_INFO";

    /**
     * 会话信息键：用户信息-对象
     */
    public static final String SESSION_KEY_AUTH_INFO_OBJ = "SESSION_KEY_AUTH_INFO_OBJ";

    /**
     * 会话信息键：用户会话
     */
    public static final String SESSION_KEY_SESSION_INFO = "SESSION_KEY_SESSION_INFO";

    /**
     * 会话信息键：用户ID
     */
    public static final String SESSION_KEY_USER_ID = "SESSION_KEY_USER_ID";

    /**
     * 会话信息键：用户编码
     */
    public static final String SESSION_KEY_USER_KEY = "SESSION_KEY_USER_KEY";

    /**
     * 会话信息键：组织ID
     */
    public static final String SESSION_KEY_ORG_ID = "SESSION_KEY_ORG_ID";

    /**
     * 会话信息键：组织编码
     */
    public static final String SESSION_KEY_ORG_KEY = "SESSION_KEY_ORG_KEY";

    /**
     * 会话信息键：本地对象
     */
    public static final String SESSION_KEY_LOCALE = "SESSION_KEY_LOCALE";

    /**
     * 会话信息键：请求
     */
    public static final String SESSION_KEY_REQUEST = "SESSION_KEY_REQUEST";

    /**
     * 会话信息键：请求
     */
    public static final String SESSION_KEY_RESPONSE = "SESSION_KEY_RESPONSE";

    /**
     * 会话信息键：请求
     */
    public static final String SESSION_KEY_CURR_TENANT = "SESSION_KEY_CURR_TENANT";

    /**
     * 会话信息键：管理者标识
     */
    public static final String SESSION_KEY_ADMIN_FLAG = "SESSION_KEY_ADMIN_FLAG";

    /**
     * 会话信息默认值：用户ID
     */
    public static final Long DEFAULT_SYSTEM_USER_ID = NumberConstant.NONE_OF_DATA_ID;

    /**
     * 会话信息默认值：组织ID
     */
    public static final Long DEFAULT_SYSTEM_ORG_ID = NumberConstant.NONE_OF_DATA_ID;

    /**
     * 会话信息默认值：用户编码
     */
    public static final String DEFAULT_SYSTEM_USER_KEY = StringConstant.NONE_OF_DATA;

    /**
     * 会话信息默认值：组织编码
     */
    public static final String DEFAULT_SYSTEM_ORG_KEY = StringConstant.NONE_OF_DATA;

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    @Strategy("SessionUtil.getUserId")
    public static Long getUserId() {
        return getValue(SESSION_KEY_USER_ID, DEFAULT_SYSTEM_USER_ID);
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    @Strategy("SessionUtil.getOrgId")
    public static Long getOrgId() {
        return getValue(SESSION_KEY_ORG_ID, DEFAULT_SYSTEM_ORG_ID);
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    @Strategy("SessionUtil.getUserKey")
    public static String getUserKey() {
        return getValue(SESSION_KEY_USER_KEY, DEFAULT_SYSTEM_USER_KEY);
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    @Strategy("SessionUtil.getOrgKey")
    public static String getOrgKey() {
        return getValue(SESSION_KEY_ORG_KEY, DEFAULT_SYSTEM_ORG_KEY);
    }

    /**
     * 获取用户本地化对象
     *
     * @return 本地化对象
     */
    @Strategy("SessionUtil.getLocale")
    public static Locale getLocale() {
        return LanguageEnum.closestLocale(LocaleContextHolder.getLocale());
    }

    /**
     * 获取请求对象
     *
     * @return 请求对象
     */
    public static HttpServletRequest getRequest() {
        return getValue(SESSION_KEY_REQUEST, null);
    }

    /**
     * 获取请求对象
     *
     * @return 请求对象
     */
    public static HttpServletResponse getResponse() {
        return getValue(SESSION_KEY_RESPONSE, null);
    }

    /**
     * 获取会话
     *
     * @return 会话
     */
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getSession();
    }

    /**
     * 获取上下文路径
     *
     * @return 上下文路径
     */
    @Strategy("SessionUtil.getSessionValue")
    public static <T> T getSessionValue(String key) {
        HttpSession getSession = getSession();
        if (getSession == null) {
            return null;
        }
        return (T) getSession.getAttribute(key);
    }

    /**
     * 获取上下文路径
     *
     * @return 上下文路径
     */
    public static String getContentPath() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getServletContext().getRealPath("/");
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public static void putUserId(Integer userId) {
        setValue(SESSION_KEY_USER_ID, userId);
    }

    /**
     * 设置用户ID
     *
     * @param orgId 组织ID
     */
    public static void putOrgId(Integer orgId) {
        setValue(SESSION_KEY_ORG_ID, orgId);
    }

    /**
     * 设置用户ID
     *
     * @param userKey 用户编码
     */
    public static void putUserKey(String userKey) {
        setValue(SESSION_KEY_USER_KEY, userKey);
    }

    /**
     * 设置用户ID
     *
     * @param orgKey 组织编码
     */
    public static void putOrgKey(String orgKey) {
        setValue(SESSION_KEY_ORG_KEY, orgKey);
    }

    /**
     * 设置用户本地化对象
     *
     * @param locale 本地化对象
     */
    public static void putLocale(Locale locale) {
        setValue(SESSION_KEY_LOCALE, locale);
    }

    /**
     * 设置请求对象
     *
     * @param request 请求对象
     */
    public static void putRequest(HttpServletRequest request) {
        setValue(SESSION_KEY_REQUEST, request);
    }

    /**
     * 设置请求对象
     *
     * @param response 请求对象
     */
    public static void putResponse(HttpServletResponse response) {
        setValue(SESSION_KEY_RESPONSE, response);
    }

    /**
     * 会话存值
     *
     * @param key   键
     * @param value 值
     */
    public static boolean putSession(String key, Object value) {
        HttpSession getSession = getSession();
        if (getSession == null) {
            return false;
        }
        getSession.setAttribute(key, value);
        return true;
    }

    /**
     * 获取会话消息
     *
     * @return 会话消息
     */
    public static Map<String, Object> get() {
        return content.get();
    }

    /**
     * 设置会话消息
     *
     * @param info 会话消息
     */
    public static void set(Map<String, Object> info) {
        content.set(info);
    }

    /**
     * 通用取值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param <T>          结果泛型
     * @return 会话线程保存值
     */
    public static <T> T getValue(String key, T defaultValue) {
        Map<String, Object> map = content.get();
        if (map == null) {
            return defaultValue;
        }

        T result = (T) map.get(key);
        if (result == null) {
            return defaultValue;
        }
        return result;
    }

    /**
     * 通用存值
     *
     * @param key   键
     * @param value 值
     */
    public static void setValue(String key, Object value) {
        Map<String, Object> map = content.get();
        if (map == null) {
            map = new HashMap<>(4);
            content.set(map);
        }
        map.put(key, value);
    }

    /**
     * 清除内容
     */
    public static void clear() {
        content.remove();
    }

    /**
     * 是否有角色
     *
     * @param roleType 角色类型
     * @param roleKey  角色编码
     * @return 有角色：true
     */
    public static boolean hasRole(String roleType, String roleKey) {
        Map<String, Set<String>> rolesMap = getValue(SESSION_KEY_ROLE, Collections.emptyMap());
        Set<String> roles = rolesMap.get(roleType);
        if (roles != null && roles.contains(roleKey)) {
            return true;
        }
        return isSuperAdmin();
    }

    /**
     * 是否有超级管理员
     *
     * @return 是超级管理员：true
     */
    public static boolean isSuperAdmin() {
        Map<String, Set<String>> rolesMap = getValue(SESSION_KEY_ROLE, Collections.emptyMap());
        Set<String> roles = rolesMap.get(RoleTypeEnum.FUNCTION.name());
        return roles != null && roles.contains(SUPER_ADMIN_ROLE_NAME);
    }

    /**
     * 是否有租户管理员
     *
     * @return 是租户管理员：true
     */
    public static boolean isOrgAdmin() {
        Map<String, Set<String>> rolesMap = getValue(SESSION_KEY_ROLE, Collections.emptyMap());
        Set<String> roles = rolesMap.get(RoleTypeEnum.FUNCTION.name());
        return roles != null && roles.contains(ORG_ADMIN_ROLE_NAME);
    }

    /**
     * 是否为管理员
     *
     * @return 是否为管理员
     */
    public static boolean isAdmin() {
        Boolean value = getValue(SESSION_KEY_ADMIN_FLAG, false);
        if (value) {
            return true;
        }

        Map<String, Set<String>> rolesMap = getValue(SESSION_KEY_ROLE, Collections.emptyMap());
        Set<String> roles = rolesMap.get(RoleTypeEnum.FUNCTION.name());
        return roles != null && (roles.contains(SUPER_ADMIN_ROLE_NAME) || roles.contains(ORG_ADMIN_ROLE_NAME));
    }

    /**
     * 获取授权纤细对象
     *
     * @return 授权信息对象
     */
    public static Object getAuthInfo() {
        return getValue(SESSION_KEY_AUTH_INFO, null);
    }

    /**
     * 设置授权纤细对象
     *
     * @param authInfo 授权信息对象
     */
    public static void setAuthInfo(Map<String, Object> authInfo) {
        setValue(SESSION_KEY_AUTH_INFO, authInfo);
    }

    /**
     * 设置授权纤细对象
     *
     * @param authInfo 授权信息对象
     */
    public static void setAuthInfoObj(Object authInfo) {
        setValue(SESSION_KEY_AUTH_INFO_OBJ, authInfo);
        setAuthInfo((JSONObject) JSON.toJSON(authInfo));
    }

    /**
     * 设置授权纤细对象
     *
     * @return authInfo 授权信息对象
     */
    public static <T> T getAuthInfoObj() {
        return getValue(SESSION_KEY_AUTH_INFO_OBJ, null);
    }

    /**
     * 设置授权信息
     *
     * @param permission 授权信息
     */
    public static void setPermissions(Map<String, Set<String>> permission) {
        setValue(SESSION_KEY_PERMISSION, permission);
    }

    /**
     * 设置角色信息
     *
     * @param rolesMap 角色信息
     */
    public static void setRoles(Map<String, Set<String>> rolesMap) {
        setValue(SESSION_KEY_ROLE, rolesMap);
    }

    /**
     * 设置管理员标识：表示管理员接管当前线程
     */
    public static void setAdminFlag() {
        setValue(SESSION_KEY_ADMIN_FLAG, true);
    }

    /**
     * 设置管理员标识：表示管理员接管当前线程
     */
    public static void clearAdminFlag() {
        setValue(SESSION_KEY_ADMIN_FLAG, false);
    }

}
