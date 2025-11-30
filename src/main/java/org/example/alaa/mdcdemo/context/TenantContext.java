package org.example.alaa.mdcdemo.context;

import org.example.alaa.mdcdemo.model.UserInfo;

/**
 * Simulated TenantContext Thread local
 * in real apps will be populated form SecurityContext or JWTAuthentication Object
 */
public class TenantContext {
    private static final ThreadLocal<UserInfo> TenantContext = new ThreadLocal<>();


    public static void setTenantContext(UserInfo userInfo) {
        TenantContext.set(userInfo);
    }
    public static UserInfo getTenantContext() {
        return TenantContext.get();
    }
    public static void clear() {
        TenantContext.remove();
    }
}
