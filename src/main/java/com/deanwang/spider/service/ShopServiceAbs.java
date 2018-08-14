package com.deanwang.spider.service;

import com.alibaba.fastjson.JSON;

abstract class ShopServiceAbs {
 protected String url;
 protected String html;
    public void getHtml(){

    }
    public JSON getParser(){
        return null;
    }
}
