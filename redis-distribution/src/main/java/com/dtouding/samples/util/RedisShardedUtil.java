package com.dtouding.samples.util;

import com.dtouding.samples.config.RedisShardedPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Sharded;

public class RedisShardedUtil {

    public static String set(String key, String value) {
        ShardedJedis jedis = RedisShardedPool.getJedis();
        String result = jedis.set(key, value);
        RedisShardedPool.returnToPool(jedis);
        return result;
    }

    public static String get(String key) {
        ShardedJedis jedis = RedisShardedPool.getJedis();
        String result = jedis.get(key);
        RedisShardedPool.returnToPool(jedis);
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
        ShardedJedis jedis = RedisShardedPool.getJedis();
        String result = jedis.setex(key, exTime, value);
        RedisShardedPool.returnToPool(jedis);
        return result;
    }

    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key, int exTime) {
        ShardedJedis jedis = RedisShardedPool.getJedis();
        Long result = jedis.expire(key, exTime);
        RedisShardedPool.returnToPool(jedis);
        return result;
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public static Long del(String key){
        ShardedJedis jedis = RedisShardedPool.getJedis();
        Long result = jedis.del(key);
        RedisShardedPool.returnToPool(jedis);
        return result;
    }

    public static Boolean exists(String key) {
        ShardedJedis jedis = RedisShardedPool.getJedis();
        Boolean exists = jedis.exists(key);
        RedisShardedPool.returnToPool(jedis);
        return exists;
    }
}
