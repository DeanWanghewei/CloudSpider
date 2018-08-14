package com.deanwang.spider.http;

import com.deanwang.spider.proxy.PullIpProxyPool;
import com.deanwang.spider.proxy.bean.PorxyIp;
import com.google.common.base.Strings;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpClientGet {
    private static final Logger LOG = LoggerFactory.getLogger(HttpClientGet.class);
    private String url="";
    private String userAgent="";

    private HttpHost httpHost=null;

    public String get() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        try {
            if (Strings.isNullOrEmpty(this.url))return null;

            // 创建get连接
            HttpGet httpget = new HttpGet(url);
            // 设置超时时间
            RequestConfig requestConfig=null;
            if (httpHost==null){
                requestConfig = RequestConfig.custom()
                    .setSocketTimeout(2000).setConnectTimeout(1000 * 10)
                    .build();// 设置请求和传输超时时间

            }else {
                requestConfig = RequestConfig.custom()
                        .setSocketTimeout(2000).setConnectTimeout(1000 * 10)
                        .setProxy(httpHost)
                        .build();// 设置请求和传输超时时间
            }
            httpget.setConfig(requestConfig);

            if (userAgent==null){
                this.userAgent = UserAgent.getRandemUA();
            }

            httpget.setHeader("Accept-Charset", userAgent );
            // 发送请求
//            CloseableHttpResponse response = httpclient.execute(targetHost,
//                    httpget);
            CloseableHttpResponse response = httpclient.execute(httpget);
            LOG.debug("Request URI :{}",httpget.getURI());
            // 获取状态码
            LOG.debug("Response status :{}",response.getStatusLine().getStatusCode());
//
//            System.out.println("头部信息："
//                    + httpget.getFirstHeader("Accept-Charset").getValue());

            // BufferedReader reader= new BufferedReader(new
            // InputStreamReader(response.get));

            // 获取所有的请求头信息
//            Header headers[] = response.getAllHeaders();
//            int ii = 0;
//            while (ii < headers.length) {
//                System.out.println(headers[ii].getName() + ":"
//                        + headers[ii].getValue());
//                ++ii;
//            }
            // 抓取网页内容
            HttpEntity entity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    entity.getContent(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }

            br.close();

            // 终止请求
            httpget.abort();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 终止请求
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public String ProxyIpGet( ){
        PorxyIp proxyIp = PullIpProxyPool.getOne();
        //设置代理IP、端口、协议（请分别替换）
        HttpHost proxy = new HttpHost(proxyIp.getProxyip(), proxyIp.getProxyPort(), "http");
        this.httpHost = proxy;
        return get();
    }

    public HttpClientGet(String url) {
        this.url = url;
    }

    public HttpClientGet(String url, String userAgent) {
        this.url = url;
        this.userAgent = userAgent;
    }
}
