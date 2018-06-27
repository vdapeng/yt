package com.vdaoyun.systemapi.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.vdaoyun.systemapi.web", "com.vdaoyun.systemapi.wx"})//mapper扫描
public class MybatisConfiguration {

}
