package com.spiderZhuanZhuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class CqApplication extends SpringBootServletInitializer { // 打war包需继承类
public class SpiderZhuanZhuanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpiderZhuanZhuanApplication.class, args);
    }

    //打 war包需重新的方法
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(YkApplication.class);
    }*/

}
