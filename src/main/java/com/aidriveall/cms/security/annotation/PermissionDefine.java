package com.aidriveall.cms.security.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Inherited
@Documented
public @interface PermissionDefine {
    String groupName() default "系统设置";
    String groupCode() default "GROUP_SYSTEM";
    String entityCode();
    String entityName();
    String permissionName();
    String permissionCode();
}
