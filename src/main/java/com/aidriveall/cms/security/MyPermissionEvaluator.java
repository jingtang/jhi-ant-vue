package com.aidriveall.cms.security;

import com.aidriveall.cms.service.ApiPermissionService;
import com.aidriveall.cms.service.dto.ApiPermissionDTO;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.List;

public class MyPermissionEvaluator implements PermissionEvaluator {

    private final ApiPermissionService apiPermissionService;

    public MyPermissionEvaluator(ApiPermissionService apiPermissionService) {
        this.apiPermissionService = apiPermissionService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        List<ApiPermissionDTO> allApiPermissions = apiPermissionService.findAllApiPermissionsByCurrentUser();
        return allApiPermissions.stream().anyMatch(apiPermissionDTO -> apiPermissionDTO.getCode().equals(targetDomainObject));
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
