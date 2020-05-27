package com.spiderZhuanZhuan.controller;

import com.spiderZhuanZhuan.pojo.ZhuanZhuanPojo;
import com.spiderZhuanZhuan.util.MyResponse;
import com.spiderZhuanZhuan.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class getInfoController {
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/")
    @ResponseBody
    public MyResponse getInfo(){
        Map<String, List<ZhuanZhuanPojo>> pojoList=new HashMap<>();
        pojoList.put("salePriceList",(List<ZhuanZhuanPojo>)redisUtil.get("salePriceList"));
        pojoList.put("originPriceList",(List<ZhuanZhuanPojo>)redisUtil.get("originPriceList"));
        return MyResponse.OK(pojoList);
    }
}
