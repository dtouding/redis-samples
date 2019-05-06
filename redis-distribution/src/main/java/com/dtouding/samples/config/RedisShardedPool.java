package com.dtouding.samples.config;

import com.dtouding.samples.util.PropertiesUtil;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RedisShardedPool {

    /** sharded jedis连接池. */
    private static ShardedJedisPool pool;
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
    private static String REDIS1_IP = PropertiesUtil.getProperty("redis1.ip");
    private static String REDIS2_IP = PropertiesUtil.getProperty("redis2.ip");
    private static Integer REDIS1_PORT = Integer.valueOf(PropertiesUtil.getProperty("redis1.port"));
    private static Integer REDIS2_PORT = Integer.valueOf(PropertiesUtil.getProperty("redis2.port"));
    private static Integer TIMEOUT = Integer.valueOf(PropertiesUtil.getProperty("redis.timeout"));
    private static String REDIS1_PASSWORD = PropertiesUtil.getProperty("redis1.password");
    private static String REDIS2_PASSWORD = PropertiesUtil.getProperty("redis2.password");

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(blockWhenExhausted);
        //pool = new JedisPool(config, IP, PORT, TIMEOUT, PASSWORD);

        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(REDIS1_IP, REDIS1_PORT, TIMEOUT);
        jedisShardInfo1.setPassword(REDIS1_PASSWORD);
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo(REDIS2_IP, REDIS2_PORT, TIMEOUT);
        jedisShardInfo2.setPassword(REDIS2_PASSWORD);
        List<JedisShardInfo> jedisShardInfoList = Arrays.asList(jedisShardInfo1, jedisShardInfo2);
        pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH,
                Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static  {
        initPool();
    }

    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void returnToPool(ShardedJedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

}
