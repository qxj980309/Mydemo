package com.example.demo.mock.common.util;

import org.springframework.data.redis.core.RedisTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private final RedisTemplate<String,Object> redisTemplate;

    private final Long defaultTime = 60L*10;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
    * 删除key
    *
    *  @param keys keys
    * */
    public void deleteKey(String... keys){
        if (keys != null && keys.length >0 ){
            if (keys.length==1){
                redisTemplate.delete(keys[0]);
            }else {
                redisTemplate.delete(Arrays.asList(keys));
            }
        }
    }

    /*
    * 缓存值，并设置默认失效时间
    *
    * @param key key
    * @param value value
    * */
    public void valueDefaultSet (String key, Object value) {
        valueSetExpire(key, value, defaultTime) ;
    }

    /*
     * 缓存值 设置过期时间
     *
     * @param key
     * @param value
     * @param timeout 过期时间
     * */
    public void valueSetExpire(String key,Object value,Long timeout){
        redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
    }

    /*
    * 获取值
    *
    *  @param key
    *  @return object
    * */
    public Object valueGet (String key){
        return redisTemplate.opsForValue().get (key) ;
    }

    /*
    * 指定redis key失效时间 单位:秒
    * *@param key
    * *@param timeout 过期时间
    * */
    public void expire(String key,Long timeout){
        if (timeout > 0){
            redisTemplate.expire(key,timeout,TimeUnit.SECONDS);
        }
    }

    /*
     * 缓存hash,并设置失效时间
     *
     * @param key
     * @param value
     * @param hashKey
     * @param timeout 过期时间
     * */
    public void hashSetExpire(String key, String hashKey, Object value, Long timeout){
        redisTemplate.opsForHash () .put (key, hashKey, value);
        expire(key,timeout);
    }

    /*
    *缓存hash,并设置默认失效时间
    *
    * @param key
    * @param hashKey hashKey
    * @param value value
    * */
    public void hashDefaultSet(String key,String hashKey,Object value){
        hashSetExpire(key,hashKey,value,defaultTime);
    }

    /*
    * 获取hash
    *
    * @param key
    * @param hashKey hashKey
    * @param value value
    * */
    public Object hashGet (String key, String hashKey) {
        return redisTemplate.opsForHash ().get(key, hashKey);
    }

    /*
    * 获取所有键值对后并删除
    * 线程安全
    *
    * @param key key
    * @return map
    * */
    public synchronized Map<Object, Object> hashAllGetAndDestroy(String key) {
        Map<Object, Object> ret = redisTemplate.opsForHash ().entries(key);
        this.deleteKey(key);
        return ret;
    }

    /*
    * 缓存list值，并设置默认失效时间
    *
    * @param key key
    * @param value value
    * */
    public void listDefaultSet (String key, Object value) {
        listSetExpire(key, value, defaultTime);
    }

    /*
    * 缓存list所有值
    *
    * @param key key*
    * @return List<object>
    * */
    public List<Object> listGetAll(String key) {
        return redisTemplate.opsForList ().range(key, 0, -1);
    }

    /*
    * 缓存list值,并设置失效时间
    *
    * @param key key
    * @param value value
    * @param timeout 过期时间
    * */
    private void listSetExpire(String key, Object value, Long timeout) {
        redisTemplate.opsForList().rightPush (key, value) ;
        expire (key, timeout);
    }

}
