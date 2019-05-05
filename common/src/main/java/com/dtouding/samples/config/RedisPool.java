package com.dtouding.samples.config;

import com.dtouding.samples.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

    /** jedis连接池. */
    private static JedisPool pool;
    /** 最大连接数. */
    private static Integer maxTotal = Integer.valueOf(PropertiesUtil.getProperty("redis.max.total", "20"));
    /** jedis连接池中最大的空闲的jedis实例数. */
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));
    /** 最小的空闲的jedis实例数. */
    private static Integer minIdle = Integer.valueOf(PropertiesUtil.getProperty("redis.min.idle", "2"));
    /** 在borrow一个jedis实例时，是否需要进行验证操作. */
    private static Boolean testOnBorrow = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    /** 在return一个jedis实例时，是否需要进行验证操作. */
    private static Boolean testOnReturn = Boolean.valueOf(PropertiesUtil.getProperty("redis.test.return", "true"));
    /** 当连接数耗尽的时候，连接是否阻塞，true代表阻塞, false会抛出异常. */
    private static Boolean blockWhenExhausted = true;
    private static String IP = PropertiesUtil.getProperty("redis.ip");
    private static Integer PORT = Integer.valueOf(PropertiesUtil.getProperty("redis.port"));
    private static Integer TIMEOUT = Integer.valueOf(PropertiesUtil.getProperty("redis.timeout"));
    private static String PASSWORD = PropertiesUtil.getProperty("redis.password");
    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(blockWhenExhausted);
        pool = new JedisPool(config, IP, PORT, TIMEOUT, PASSWORD);
    }

    static  {
        initPool();
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnToPool(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }
}
