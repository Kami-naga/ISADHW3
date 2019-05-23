package com.eis.hw.Controller;

import com.eis.hw.Model.Entity.Orderbook;
import com.eis.hw.Model.RedisEntity.ROrderbook;
import com.eis.hw.Service.ROrderbookService;
import com.eis.hw.Util.RedisPool;
import com.eis.hw.Util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.*;

@Controller
public class JedisController {
    @Autowired
    ROrderbookService rOrderbookService;

    @GetMapping(value = "/ob")
    @ResponseBody
    public void ob(){
        ROrderbook rOrderbook = new ROrderbook();
        rOrderbook.test();
        rOrderbookService.save("ob2",rOrderbook);
    }

    @GetMapping(value="/Redis/String")
    @ResponseBody
    public void redisString(){
        RedisPool.getJedis().set("name","李琥");
        System.out.println(RedisPool.getJedis().get("name"));
    }

    @GetMapping(value="/Redis/List")
    @ResponseBody
    public void redisList(){
        Jedis jedis = RedisPool.getJedis();
        jedis.lpush("城市", "南京");
        jedis.lpush("城市", "上海");
        jedis.lpush("城市", "苏州");
        jedis.lpush("城市", "北京");
        jedis.lpush("城市", "南通");
        List<String> arr = jedis.lrange("城市",0,100);
        System.out.println("城市：");
        for(String s:arr){
            System.out.println(s);
        }
    }

    @GetMapping(value="/Redis/ListDel")
    @ResponseBody
    public void redisListDel(){
        Jedis jedis = RedisPool.getJedis();
        jedis.del("城市");
    }

    @GetMapping(value = "/Redis/Map")
    @ResponseBody
    public void redisMap(){
        Jedis jedis = RedisPool.getJedis();
        Map<String,String> map = new HashMap<>();
        map.put("name", "李琥");
        map.put("age", "21");
        map.put("sex", "男");
        map.put("height", "185");
        jedis.hmset("people",map);
        String[] arr = new String[4];
        arr[0]="name";
        arr[1]="age";
        arr[2]="sex";
        arr[3]="height";
        List<String> list = jedis.hmget("people",arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println("存入键值对为:"+arr[i]+"--"+list.get(i));
        }
    }

    @GetMapping(value = "/Redis/Set")
    @ResponseBody
    public void redisSet(){
        Jedis jedis = RedisPool.getJedis();
        List<String> list=new ArrayList<>();
        list.add("北京");
        list.add("南京");
        list.add("上海");
        list.add("北京");
        list.add("北京");
        list.add("上海");
        list.add("苏州");
        list.add("南京");
        System.out.println("源数据为"+list);

        //因为jedis的sadd的方法,存入的是一个数组对象或者多数据,所有将集合对象转换成数组对象
        String[] arr=new String[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=list.get(i);
        }
        jedis.sadd("city", arr);

        Set<String> smembers = jedis.smembers("city");

        System.out.println(smembers);
    }



}
