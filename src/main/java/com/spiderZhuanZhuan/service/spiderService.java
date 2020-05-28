package com.spiderZhuanZhuan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.spiderZhuanZhuan.pojo.ZhuanZhuanPojo;
import com.spiderZhuanZhuan.util.RedisUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@EnableScheduling
public class spiderService {
    private static final Logger logger = LoggerFactory.getLogger(spiderService.class);
    public static final String[] key = {"2650","E5-2670V2","E5-2689","E5-2660","i7-3770","i7-2600","i7-870","i5-3470","X5670","i7-2720QM","i7-950","X5660","i5-750","X5650","i5-760","X2-250", "i3-530", "E5620", "L5640", "i5-650", "X5550", "i3-2120", "E5645", "i3-3240", "i7-920XM","E5-2620","X5570","i7-860"};
    @Autowired
    RedisUtil redisUtil;

    @Scheduled(fixedRate = 300000)
    public void crawl58ContentProduction() {
        List<ZhuanZhuanPojo> salePriceList = new ArrayList<>();
        List<ZhuanZhuanPojo> originPriceList = new ArrayList<>();
        for (int page = 1; page < 10000; page++) {
            try {
                List<ZhuanZhuanPojo> zhuanPojoList = null;
                CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建HttpClient实例
                HttpGet httpGet = new HttpGet("https://youpinapi.zhuanzhuan.com/goods/mall/digital/list/data?page=" + page + "&filtrate=5461:103%3B5462:2103005%3B5464:2%3B5477:201912171734001&filterId=5464:2&rstmark=1590632599337&from=dnrc19&listType=4");//创建HttpGet实例
                httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
                CloseableHttpResponse response;
                response = httpClient.execute(httpGet);//执行HttpGet请求
                HttpEntity entity = response.getEntity();//获取返回实体
                String content = EntityUtils.toString(entity, "UTF-8");//获取网页内容 并且指定了编码
                JSONArray jsonArray = JSON.parseObject(content).getJSONObject("respData").getJSONArray("datas");
                if (JSON.parseObject(content).getJSONObject("respData").getIntValue("count") == 0) {
                    logger.info("response data is null,where page is : {}", JSON.parseObject(content).getJSONObject("respData").getIntValue("index"));
                    Collections.sort(salePriceList, (ZhuanZhuanPojo o1, ZhuanZhuanPojo o2) -> {
                        return o1.getSalePrice() - o2.getSalePrice();
                    });
                    Collections.sort(originPriceList, (ZhuanZhuanPojo o1, ZhuanZhuanPojo o2) -> {
                        return o1.getSalePrice() - o2.getSalePrice();
                    });
                    Calendar calendar = Calendar.getInstance();

//                    System.out.println(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND)+" size = "+list.size());
                    logger.info("秒杀产品 size : {} ", salePriceList.size());
                    salePriceList.stream().forEach((ZhuanZhuanPojo o1) -> {
                        System.out.println(o1.toString());
                    });
                    logger.info("无优惠产品 size : {} ", originPriceList.size());
                    originPriceList.stream().forEach((ZhuanZhuanPojo o1) -> {
                        System.out.println(o1.toString());
                    });
                    logger.info("- - - - - - - - - - - - - - - - - - - - -");
                    redisUtil.set("salePriceList", salePriceList);
                    redisUtil.set("originPriceList", originPriceList);
                    break;
                }
                zhuanPojoList = jsonArray.toJavaList(ZhuanZhuanPojo.class);

                for (int i = 0; i < zhuanPojoList.size(); i++) {
                    if (!Arrays.stream(key).anyMatch(zhuanPojoList.get(i).getTitle()::contains)) {
                        salePriceList.add(zhuanPojoList.get(i));

                    }
                }
                Thread.sleep(100);
//            logger.info("开始整理数据");
            } catch (Exception e) {
                logger.error("error:{}", e);
                return;
            }

        }
    }
}
