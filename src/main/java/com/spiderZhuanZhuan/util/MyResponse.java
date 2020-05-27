package com.spiderZhuanZhuan.util;

import java.util.HashMap;
import java.util.Map;

public class MyResponse {
    private Map<String, Object> map = new HashMap<>();
    private int code;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void putMessage(String key,String msg){
        this.map.put(key,msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static MyResponse OK() {
        MyResponse response = new MyResponse();
        response.code=200;
        return response;
    }
    public static MyResponse OK(String result) {
        MyResponse response = new MyResponse();
        response.code=200;
        response.map = new HashMap<>();
        response.map.put("data", result);
        return response;
    }
    public void putMsg(String key,String value) {
       map.put(key,value);
    }
    public static MyResponse OK(Map result) {
        MyResponse response = new MyResponse();
        response.code=200;
        response.map = new HashMap<>();
        response.map.put("data", result);
        return response;
    }

    public static MyResponse Error(String errMsg) {
        MyResponse response = new MyResponse();
        response.code=400;
        response.map = new HashMap<>();
        response.map.put("data", errMsg);
        return response;
    }
    public static MyResponse Error() {
        MyResponse response = new MyResponse();
        response.code=400;
        return response;
    }
}
