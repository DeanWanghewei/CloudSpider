package com.deanwang.spider.http;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonolo on 2015/8/21.
 */
public class HTTP {
    static final Logger logger = LoggerFactory.getLogger(HTTP.class);

    public static final String get(String url) throws IOException {
        return get(url, "UTF-8");
    }

    public static final String get(String url, String charset) throws IOException {
        URL oracle = new URL(url);
        URLConnection connection = oracle.openConnection();
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);

        logger.debug("Http request Url={}, Charset={}", url, charset);
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset != null ? Charset.forName(charset) : Charset.forName("UTF-8")));
        StringBuffer result = new StringBuffer();
        String inputLine = null;
        while ((inputLine = in.readLine()) != null) result.append(inputLine);
        in.close();
        return result.toString();
    }

    public static final String post(String url, String body) throws IOException {
        return post(url, body, "UTF-8", 30000, 30000);
    }

    public static final String post(String url, String body, int cTime, int rTime) throws IOException {
        return post(url, body, "UTF-8", cTime, rTime);
    }

    public static final String post(String url, String body, int retry) {
        return post(url, body, 30000, 30000, retry);
    }

    public static final String post(String url, String body, int cTime, int rTime, int retry) {
        try {
            return post(url, body, "UTF-8", cTime, rTime);
        } catch (IOException e) {
            if (retry >= 3) {
                logger.error("requst retry= " + retry + " , body=" + body, e);
                return null;
            }
            logger.warn("requst retry=" + retry);
            return post(url, body, cTime, rTime, ++retry);
        }
    }

    public static final String post(String url, String body, String charset) throws IOException {
        return post(url, body, charset, 30000, 30000);
    }

    public static final String post(String url, String body, String charset, int cTime, int rTime) throws IOException {
        URL oracle = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) oracle.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Charset", charset);
//        connection.setConnectTimeout(90000);
//        connection.setReadTimeout(90000);

        connection.setConnectTimeout(cTime);
        connection.setReadTimeout(rTime);

        connection.connect();

        BufferedReader ready = null;
        java.io.DataOutputStream dop = null;
        StringBuffer result = new StringBuffer();
        try {
            dop = new java.io.DataOutputStream(connection.getOutputStream());
            dop.writeBytes(body);
            dop.flush();

            logger.debug("Http request Url={}, Charset={}", url, charset);

            ready = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset != null ? Charset.forName(charset) : Charset.forName("UTF-8")));
            String inputLine = null;
            while ((inputLine = ready.readLine()) != null) result.append(inputLine);
        } finally {
            if (dop != null)
                dop.close();

            if (ready != null)
                ready.close();

        }
        return result.toString();
    }

    private static final Map<String, String> paramMap(String url, String charset) {
        HashMap<String, String> params = new HashMap<>();

        int flag = url.indexOf("?");
        if (flag != -1) {
            String $ = url.substring(flag + 1);
            String[] ps = $.split("&");
            for (String p : ps) {
                String[] keyval = p.split("=");
                if (keyval.length == 2 && !Strings.isNullOrEmpty(keyval[1])) {
                    try {
                        params.put(keyval[0], URLDecoder.decode(keyval[1], charset));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params;
    }

    private static final String paramUrl(Map<String, String> params, String charset) {
        StringBuffer url = new StringBuffer();
        for (String key : params.keySet())
            try {
                url.append(key).append("=").append(URLEncoder.encode(params.get(key), charset)).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        return url.toString();
    }


    public static final String post(String url) {
        return post(url, 30000, 3000, false);
    }


    public static final String post(String url, int cTime, int rTime) {
        return post(url, cTime, rTime, false);
    }

    public static final String post(String url, boolean print) {
        return post(url, 30000, 3000, print);
    }

    public static final String post(String url, int cTime, int rTime, boolean print) {
        if (Strings.isNullOrEmpty(url)) return null;

        Map<String, String> params = paramMap(url, "UTF-8");
        if (print) {
            for (String key : params.keySet()) {
                logger.info(key + "=" + params.get(key));
            }
        }

        return post(host(url), paramUrl(params, "UTF-8"), cTime, rTime, 0);
    }

    public static final String host(String url) {
        int s = url.indexOf("?");
        if (s == -1) return url;
        return url.substring(0, s);
    }
}
