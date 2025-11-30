package org.example.alaa.mdcdemo.context;

import org.example.alaa.mdcdemo.model.TenantInfo;

/**
 * Simulated TenantContext Thread local
 * in real apps will be populated form SecurityContext or JWTAuthentication Object
 */
public class TenantContext {
    private static final ThreadLocal<TenantInfo> tenantContext = new ThreadLocal<>();


    public static void setTenantInfo(TenantInfo tenantInfo) {
        tenantContext.set(tenantInfo);
    }
    public static TenantInfo getTenantInfo() {
        return tenantContext.get();
    }
    public static void clear() {
        tenantContext.remove();
    }
}
