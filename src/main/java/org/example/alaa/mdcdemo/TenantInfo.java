package org.example.alaa.mdcdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantInfo {

    private Integer tenantId;
    private String userName;
    private Integer userId;
}
