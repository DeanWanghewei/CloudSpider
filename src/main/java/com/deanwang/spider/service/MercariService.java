package com.deanwang.spider.service;

import com.alibaba.fastjson.JSON;
import com.deanwang.spider.http.HttpClientGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class MercariService extends ShopServiceAbs implements Runnable {


    @Override
    public void getHtml() {
        HttpClientGet httpClientGet = new HttpClientGet(url);
        html = httpClientGet.get();
    }

    @Override
    public JSON getParser() {

        Document document = Jsoup.parse(html);
        Elements section = document.select("section");
        for (Iterator<Element> it = section.iterator(); it.hasNext(); ) {
            Element next = it.next();

            //商品链接
            Elements a = next.select("a");
            String href = a.attr("href");
            System.out.println("href: " + href);


            Elements img = next.select("img");
            //缩略图
            String attr = img.attr("data-src");
            System.out.println("imgUrl: " + attr);

            //商品所有文字信息
            System.out.println("message: " + next.text());
        }

        return null;

    }

    public MercariService(String url) {
        super.url = url;
    }

    public MercariService() {
    }

    @Override
    public void run() {
        this.getHtml();
        JSON parser = this.getParser();

    }


}
