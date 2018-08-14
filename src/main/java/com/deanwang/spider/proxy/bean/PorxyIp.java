package com.deanwang.spider.proxy.bean;

public class PorxyIp {
    private String proxyip;
    private int proxyPort;

    public PorxyIp(String proxyip, int proxyPort) {
        this.proxyip = proxyip;
        this.proxyPort = proxyPort;
    }

    public String getProxyip() {
        return proxyip;
    }

    public void setProxyip(String proxyip) {
        this.proxyip = proxyip;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
}
