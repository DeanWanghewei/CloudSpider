package com.deanwang.spider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;

/**
 * <p>Author: Deanwang
 * <p>Date: 2018-08-14 10:05
 * <p>Version: 1.0
 */
@SpringBootApplication
public class ShopSpiderPlus implements EmbeddedServletContainerCustomizer {

    private static final Logger LOG = LoggerFactory.getLogger(ShopSpiderPlus.class);


    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ShopSpiderPlus.class);
        app.run(args);
        LOG.info("shopSpider JobPool Start ...");

    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {

    }
}
