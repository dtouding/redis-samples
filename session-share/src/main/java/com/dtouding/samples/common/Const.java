package com.dtouding.samples.common;

public class Const {

    public interface RedisKeyPrefix {
        String REDIS_SESION_ID_PREFIX = "USER_";
    }

    public interface RedisCacheExtime {
        int REDIS_SESSION_EXTIME = 60 * 30; //30分钟
    }

    public interface CookieConstant {
        String COOKIE_DOMAIN = ".dtouding.com";
        String COOKIE_NAME = "dtouding_login_token";
    }

}
