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
    public static final String[] key = {"荣耀20","荣耀30","V20","V30","nova5Pro","nova68","Mate20Pro","Mate20X","P30","nova7"};

    @Autowired
    RedisUtil redisUtil;

    @Scheduled(fixedRate = 100000)
    public void crawl58ContentProduction() {
        List<ZhuanZhuanPojo> salePriceList = new ArrayList<>();
        List<ZhuanZhuanPojo> originPriceList = new ArrayList<>();
        for (int page = 1; page < 10000; page++) {
            try {
                List<ZhuanZhuanPojo> zhuanPojoList = null;
                CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建HttpClient实例
                HttpGet httpGet = new HttpGet("https://youpinapi.zhuanzhuan.com/goods/mall/complex/list/data?page=" + page + "&filtrate=5461:101%3B5462:2101002%3B5464:2%3B%3B&listType=29&from=zdqqa&init_from=&rstmark=1590551880444");//创建HttpGet实例
//            httpGet.setHeader(":authority", "youpinapi.zhuanzhuan.com");
//            httpGet.setHeader(":method", "GET");
//            httpGet.setHeader(":path", "/goods/mall/complex/list/data?page=" + page + "&filtrate=5461:101%3B5462:2101002%3B5464:2%3B%3B5477:852089291349600377&listType=29&from=zdqqa&init_from=&rstmark=1590551880444");
//            httpGet.setHeader(":scheme", "https");
//            httpGet.setHeader("accept", "application/json, text/plain, */*");
//            httpGet.setHeader("accept-encoding", "gzip, deflate, br");
//            httpGet.setHeader("accept-language", "zh-CN,zh;q=0.9");
//            httpGet.setHeader("cookie", "idzz=c5/nR17N2/td02mWGOx8Ag==; tk=c5/nR17N2/td02mWGOx8Ag==; id58=c5/nR17N3G1d02mWGTGaAg==; t=37; zz_t=37");
//            httpGet.setHeader("origin", "https://m.zhuanzhuan.com");
//            httpGet.setHeader("referer", "https://m.zhuanzhuan.com/u/activity/new-universal-activity?webview=zzn&key=zdqq&from=zdqqa&zzpage=ZZSHARESUCESE&zzfrom=LinkCopy&wxTime=1590551880260");
//            httpGet.setHeader("sec-fetch-dest", "empty");
//            httpGet.setHeader("sec-fetch-mode", "cors");
//            httpGet.setHeader("sec-fetch-site", "same-site");
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
//                    if (zhuanPojoList.get(i).getTitle().contains("荣耀20 ") ||zhuanPojoList.get(i).getTitle().contains("荣耀 30 ") ||zhuanPojoList.get(i).getTitle().contains("V20 ") ||zhuanPojoList.get(i).getTitle().contains("V30 ")|| zhuanPojoList.get(i).getTitle().contains("nova 5 Pro") || zhuanPojoList.get(i).getTitle().contains("nova 6 8") || zhuanPojoList.get(i).getTitle().contains("Mate 20 Pro") || zhuanPojoList.get(i).getTitle().contains("Mate 20 X") || zhuanPojoList.get(i).getTitle().contains("P30") || zhuanPojoList.get(i).getTitle().contains("nova 7")) {
                        if (Arrays.stream(key).anyMatch(zhuanPojoList.get(i).getTitle().replaceAll(" ","")::contains)){
                        if (zhuanPojoList.get(i).getSalePrice()<2000&&!zhuanPojoList.get(i).getTitle().contains("8新")) {
                            if (zhuanPojoList.get(i).getOriginPrice() != 0) {
                                salePriceList.add(zhuanPojoList.get(i));
                            }
                            else {
                                originPriceList.add(zhuanPojoList.get(i));
                            }

                        }
                    }
                }
                Thread.sleep(200);
//            logger.info("开始整理数据");
            } catch (Exception e) {
                logger.error("error:{}", e);
                return;
            }

        }
    }
}
