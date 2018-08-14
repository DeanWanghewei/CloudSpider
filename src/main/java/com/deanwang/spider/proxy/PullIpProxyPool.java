package com.deanwang.spider.proxy;

import com.deanwang.spider.http.HTTP;
import com.deanwang.spider.proxy.bean.PorxyIp;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PullIpProxyPool {
    private static final Logger LOG = LoggerFactory.getLogger(PullIpProxyPool.class);
    private static final String PROXY_SERVICE = "http://www.api.xzdean.cn/";

    public static PorxyIp getOne(){
        String response = "";
        try {
            response = HTTP.get(PROXY_SERVICE+"get/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!Strings.isNullOrEmpty(response)){
            String[] split = response.split(":");
            if (split.length==2){
                return new PorxyIp(split[0], Integer.valueOf(split[1]));
            }
        }
        return null;
    }

}
