package com.nnk.upstream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/20
 * Time: 8:48
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Component
public class RedisCacheManager {
    public static final String WAITTINGMAP = "OrderWaitingMap";//用于存放程序异常订单，分两种：未发送，已发送
    public static final String INSTANCEMAP = "InstanceMaps";//用于存放程序启动实例
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //=======================================map start====================================//
    public void add2MapExpire(String mapName,String objectKey,Object slowIntProctol,int time,TimeUnit timeUnit){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        map.put(objectKey,slowIntProctol);
        map.expire(time,timeUnit);
    }
    public void add2Map(String mapName,String objectKey,Object slowIntProctol){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        map.put(objectKey,slowIntProctol);
        map.expire(2,TimeUnit.DAYS);
    }
    public List<Object> listValues(String mapName){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        List<Object> list = map.values();
        return list;
    }

    public Object mapGet(String mapName,String objectKey){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        return map.get(objectKey);
    }

    public Long mapSize(String mapName){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        return map.size();
    }
    public long getExpire(String mapName){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        return map.getExpire();
    }
    public Set listKeys(String mapName){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        return map.keys();

    }
    public Long removeKeys(String mapName,String... objectKeys){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        return map.delete(objectKeys);
    }

    public boolean existKey(String mapName,String objectKey){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        return map.hasKey(objectKey);
    }

    public List muiltGet(String mapName,Collection objectKeys){
        BoundHashOperations<String, String, Object> map = redisTemplate.boundHashOps(mapName);
        return map.multiGet(objectKeys);
    }
    //==============================map end==================================================//
    //============================List start=================================================//
    public void add2List(String listname,Object value){
        BoundListOperations<String,Object> listOperations = redisTemplate.boundListOps(listname);
        listOperations.leftPush(value);

        listOperations.expire(2, TimeUnit.DAYS);
    }
    public long getListsize(String listname){

        BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(listname);
        return listOperations.size();
    }
    public void updateList(String listname,int index,Object value){
        BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(listname);
        listOperations.set(index,value);
    }
    public Object getIndexValue(String listname, int index){
        BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(listname);
        return listOperations.index(index);
    }
    public long removeLvalue(String listname,int count,String value){
        BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(listname);
        return listOperations.remove(count,value);
    }
    public void removeOutterValue(String listname,int start , int end){
        BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(listname);
        listOperations.trim(start,end);
    }
    public Object popList(String listname) {
        BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(listname);
        return listOperations.leftPop();
    }
    public List<Object> getListValues(String listname, long start, long end) {
        BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(listname);
        return listOperations.range(start,end);
    }
    public List<Object> getListValuesbysize(String listname, long startpage, long size) {
        BoundListOperations<String, Object> listOperations = redisTemplate.boundListOps(listname);
        return listOperations.range((startpage-1L)*size,startpage*size-1);
    }
    public List<Object> getAllListValues(String listName) {
        return getListValues(listName, 0, -1);
    }
    //===========list end =============================================================//
    //===========set start =============================================================//
    public void add2Set(String setName,Object... value){
        BoundSetOperations<String, Object> setOperations = redisTemplate.boundSetOps(setName);
        setOperations.add(value);
    }
    public void setDelete(String setName,Object... value){
        BoundSetOperations<String, Object> setOperations = redisTemplate.boundSetOps(setName);
        setOperations.remove(value);
    }
    public Long setSize(String setName){
        BoundSetOperations<String, Object> setOperations = redisTemplate.boundSetOps(setName);
        return setOperations.size();
    }
    //===========set end =============================================================//


}
