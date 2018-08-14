package com.deanwang.spider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author DeanWang
 * @version 1.0
 * @date 2018/8/14
 */
@RestController
public class SpiderJobController {
    private static final Logger LOG = LoggerFactory.getLogger(SpiderJobController.class);

    private static final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(20);

//    @RequestMapping(value = "/{keyWord}")
//    @ResponseBody
//    public String submitJob(@PathVariable String keyWord) {
//        scheduledThreadPool.scheduleAtFixedRate(new MercariService("https://www.mercari.com/jp/search/?keyword=" + keyWord), 0, 1, TimeUnit.MINUTES);
//        return "success";
//    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String helloWord() {
        return "helloWord";
    }
}
