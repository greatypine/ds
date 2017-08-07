package com.guoanshequ.dc.das.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by daishuhua on 2017/7/12.
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> template;

    public Object getValue(final String key) {
        return template.opsForValue().get(key);
    }

    public void setValue(final String key, final String val, long expire) {
        template.opsForValue().set(key, val, expire, TimeUnit.SECONDS);
    }
    
    public boolean hasKey(final String key){
    	return template.hasKey(key);
    }
}
