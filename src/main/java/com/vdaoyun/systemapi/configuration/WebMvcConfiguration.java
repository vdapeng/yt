package com.vdaoyun.systemapi.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Ken on 17/3/2.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");// 网站主框架
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(
                        new ErrorPage(HttpStatus.FORBIDDEN, "/403"),
                        new ErrorPage(HttpStatus.NOT_FOUND, "/404"),
                        new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500")
                );

            }
        };
    }

}
