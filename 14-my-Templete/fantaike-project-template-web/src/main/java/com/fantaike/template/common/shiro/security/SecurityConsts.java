package com.fantaike.template.common.shiro.security;

public class SecurityConsts {

    public static final String LOGIN_SALT = "story-admin";

    /**
     * request请求头属性
     */
    public static final String REQUEST_AUTH_HEADER="Authorization";

    /**
     * JWT-account
     */
    public static final String ACCOUNT = "account";

    /**
     * 组织ID
     */
    public static final String ORG_ID_TOKEN = "orgIdToken";

    /**
     * Shiro redis 前缀
     */
    public static final String PREFIX_SHIRO_CACHE = "story-admin:cache:";

    /**
     * redis-key-前缀-shiro:refresh_token
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "story-admin:refresh_token:";

    /**
     * redis-key-前缀-shiro:refresh_check
     */
    public final static String PREFIX_SHIRO_REFRESH_CHECK = "story:refresh_check:";

    /**
     * JWT-currentTimeMillis
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * 过期时间
     */
    public static class ExpireTime {
        private ExpireTime() {
        }
        /** 10s */
        public static final long TEN_SEC =  10;
        /** 30s */
        public static final long THIRTY_SEC =  30;
        /** 一分钟 */
        public static final long ONE_MINUTE =  60;
        /** 一小时 */
        public static final long ONE_HOUR = 60 * 60;
        /** 十二小时，单位s */
        public static final long TWELVE_HOUR =  60 * 60 * 12;
        /** 二十四小时 */
        public static final long ONE_DAY = 60 * 60 * 24;
    }

}
