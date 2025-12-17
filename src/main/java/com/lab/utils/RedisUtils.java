package com.lab.utils;

import com.lab.neko.command.entity.NekoCommandEntity;
import com.lab.neko.query.dto.NekoQueryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * Cache basic objects such as Integer, String, entity classes, etc.
     *
     * @param key   the cache key
     * @param value the cached value
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Cache basic objects such as Integer, String, entity classes, etc.
     *
     * @param key      the cache key
     * @param value    the cached value
     * @param timeout  expiration time
     * @param timeUnit time unit
     */
    public <T> void setCacheObject(final String key, final T value, final Long timeout, final TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * Set expiration time
     *
     * @param key     Redis key
     * @param timeout expiration time
     * @return true if set successfully; false otherwise
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * Set expiration time
     *
     * @param key     Redis key
     * @param timeout expiration time
     * @param unit    time unit
     * @return true if set successfully; false otherwise
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * Get expiration time
     *
     * @param key Redis key
     * @return remaining expiration time
     */
    public long getExpire(final String key)
    {
        return redisTemplate.getExpire(key);
    }

    /**
     * Check whether a key exists
     *
     * @param key cache key
     * @return true if exists; false otherwise
     */
    public Boolean hasKey(String key)
    {
        return redisTemplate.hasKey(key);
    }

    /**
     * Get a cached basic object
     *
     * @param key cache key
     * @return the cached value associated with the key
     */
    public <T> T getCacheObject(final String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * Delete a single cached object
     *
     * @param key cache key
     */
    public boolean deleteObject(final String key)
    {
        return redisTemplate.delete(key);
    }

    /**
     * Delete multiple cached objects
     *
     * @param collection collection of keys
     * @return true if deletion succeeds; false otherwise
     */
    public boolean deleteObject(final Collection collection)
    {
        return redisTemplate.delete(collection) > 0;
    }

    /**
     * Cache a List
     *
     * @param key      cache key
     * @param dataList list data to be cached
     * @return number of cached elements
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * Get a cached List
     *
     * @param key cache key
     * @return list associated with the key
     */
    public <T> List<T> getCacheList(final String key)
    {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * Cache a Set
     *
     * @param key     cache key
     * @param dataSet data to be cached
     * @return BoundSetOperations instance
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet)
    {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext())
        {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * Get a cached Set
     *
     * @param key cache key
     * @return set associated with the key
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Cache a Map
     *
     * @param key     cache key
     * @param dataMap map data to be cached
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * Get a cached Map
     *
     * @param key cache key
     * @return map associated with the key
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * Put a value into a Hash
     *
     * @param key   Redis key
     * @param hKey  hash key
     * @param value value to store
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * Get a value from a Hash
     *
     * @param key  Redis key
     * @param hKey hash key
     * @return value stored in the hash
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * Get multiple values from a Hash
     *
     * @param key   Redis key
     * @param hKeys collection of hash keys
     * @return list of hash values
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * Delete a value from a Hash
     *
     * @param key  Redis key
     * @param hKey hash key
     * @return true if deleted successfully; false otherwise
     */
    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    /**
     * Get keys by pattern
     *
     * @param pattern key pattern (prefix)
     * @return collection of matching keys
     */
    public Collection<String> keys(final String pattern)
    {
        return redisTemplate.keys(pattern);
    }


    public <T> List<T> scanOptionsList(String pattern, int count) {
        List<T> lsT = new ArrayList<>();
        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(count)
                .build();

        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory()
                .getConnection()
                .scan(options);
        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            T t = (T) redisTemplate.opsForValue().get(key);
            lsT.add(t);
        }
        return lsT;
    }

    public Cursor<byte[]> scanOptionsCursor(String pattern, int count) {

        ScanOptions options = ScanOptions.scanOptions()
                .match(pattern)
                .count(count)
                .build();

        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory()
                .getConnection()
                .scan(options);

        return cursor;
    }

}
