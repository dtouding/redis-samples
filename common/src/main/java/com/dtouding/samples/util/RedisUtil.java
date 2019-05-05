package com.dtouding.samples.util;

import com.dtouding.samples.config.RedisPool;
import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static String set(String key, String value) {
        Jedis jedis = RedisPool.getJedis();
        String result = jedis.set(key, value);
        RedisPool.returnToPool(jedis);
        return result;
    }

    public static String get(String key) {
        Jedis jedis = RedisPool.getJedis();
        String result = jedis.get(key);
        RedisPool.returnToPool(jedis);
        return result;
    }

    /**
     *
     * @param key
     * @param value
     * @param exTime
     * @return
     */
    public static String setex(String key, String value, int exTime) {
        Jedis jedis = RedisPool.getJedis();
        String result = jedis.setex(key, exTime, value);
        RedisPool.returnToPool(jedis);
        return result;
    }

    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key, int exTime) {
        Jedis jedis = RedisPool.getJedis();
        Long result = jedis.expire(key, exTime);
        RedisPool.returnToPool(jedis);
        return result;
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public static Long del(String key){
        Jedis jedis = RedisPool.getJedis();
        Long result = jedis.del(key);
        RedisPool.returnToPool(jedis);
        return result;
    }

    public static Boolean exists(String key) {
        Jedis jedis = RedisPool.getJedis();
        Boolean exists = jedis.exists(key);
        RedisPool.returnToPool(jedis);
        return exists;
    }
}
