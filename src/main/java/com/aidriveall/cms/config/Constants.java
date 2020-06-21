package com.aidriveall.cms.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "zh-cn";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DATA_PATH = "data";
    public static final String BASE_PACKAGE = "com.hmtech.cms";
    public static final String DOMAIN_PACKAGE = BASE_PACKAGE + ".domain";
    public static final String REPOSITORY_PACKAGE = BASE_PACKAGE + ".repository";
    public static final String DTO_PACKAGE = BASE_PACKAGE + ".service.dto";
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".service.mapper";
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";
    public static final String WEB_REST_PACKAGE = BASE_PACKAGE + ".web.rest";
    private Constants() {
    }
}
