package org.example.alaa.mdcdemo.context;

import org.example.alaa.mdcdemo.model.TenantInfo;

public class TenantContext {
    private static ThreadLocal<TenantInfo> tenantContext = new ThreadLocal<>();


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
