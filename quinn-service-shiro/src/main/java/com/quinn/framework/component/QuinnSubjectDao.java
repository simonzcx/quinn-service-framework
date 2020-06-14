package com.quinn.framework.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quinn.framework.api.AuthInfo;
import com.quinn.framework.model.AuthInfoFactory;
import com.quinn.framework.model.DefaultPermission;
import com.quinn.framework.util.MultiAuthInfoFetcher;
import com.quinn.framework.util.SessionUtil;
import com.quinn.util.base.convertor.BaseConverter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.subject.Subject;

/**
 * Subject创建对象
 *
 * @author Qunhua.Liao
 * @since 2020-06-01
 */
public class QuinnSubjectDao extends DefaultSubjectDAO {

    /**
     * 权限信息缓存
     */
    private Cache<String, DefaultPermission> cache;

    @Override
    public Subject save(Subject subject) {
        Object principal = subject.getPrincipal();
        if (principal != null) {
            AuthInfo authInfo = AuthInfoFactory.generate(principal);
            DefaultPermission permission = cache.get(authInfo.authCacheKey());
            if (permission == null) {
                permission = MultiAuthInfoFetcher.fetchPermissions(authInfo);
                cache.put(authInfo.authCacheKey(), permission);
            }

            SessionUtil.setValue(SessionUtil.SESSION_KEY_USER_ID, authInfo.attr(AuthInfo.ATTR_NAME_ID));
            SessionUtil.setValue(SessionUtil.SESSION_KEY_USER_KEY, BaseConverter.staticToString(authInfo.getPrincipal()));
            SessionUtil.setValue(SessionUtil.SESSION_KEY_ORG_KEY, authInfo.getCurrentTenantCode());

            SessionUtil.setAuthInfo((JSONObject) JSON.toJSON(principal));
            SessionUtil.setPermissions(permission.getPermissionsMap());
            SessionUtil.setRoles(permission.getRolesMap());
        }
        return super.save(subject);
    }

    /**
     * 权限信息缓存
     *
     * @param cache 权限信息缓存
     */
    public void setAuthorizationCache(Cache<String, DefaultPermission> cache) {
        this.cache = cache;
    }
}
