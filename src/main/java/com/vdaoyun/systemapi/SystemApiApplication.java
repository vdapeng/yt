package com.vdaoyun.systemapi;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

import com.vdaoyun.systemapi.common.utils.EnvironmentUtil;

@EnableAsync
@SpringBootApplication
@ServletComponentScan//	servlet扫描
@EnableTransactionManagement
public class SystemApiApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SystemApiApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		Environment env = SpringApplication.run(SystemApiApplication.class, args).getEnvironment();
		EnvironmentUtil.addEnvironment(env);
		String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"),
            env.getActiveProfiles());
	}
	
	@Bean
	public ServletRegistrationBean dispatcherServlet() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(
	            new DispatcherServlet(), "/");
	    registration.setAsyncSupported(true);
	    return registration;
	}
	
//	private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        return corsConfiguration;
//    }
//	
//	@Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
}
