package com.vdaoyun.systemapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Value("${spring.cloud.config.profile}")
	private String profile;
	
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("restApi")
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage("com.vdaoyun.systemapi.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("微道云RESTful APIs测试")
                .description("在这里可以欢快的测试你写的优秀(lese)代码了")
                .termsOfServiceUrl("")
                .contact("大鹏")
                .version("1.0")
                .build();
    }
    
    private static final String DEV = "dev";
    
    private Predicate<RequestHandler> basePackage(String str) {
    	// 	扫描所有有注解的api
    	//  RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class);
    	
    	Predicate<RequestHandler> basePackage = RequestHandlerSelectors.none();
    	// 当前为开发模式时，生成swagger接口测试页面
    	if (profile.equalsIgnoreCase(DEV)) {
    		basePackage = RequestHandlerSelectors.basePackage(str);
		}
    	return basePackage;
    }
    
    @Bean
    public Docket miniApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.groupName("miniApi")
                .apiInfo(miniApiInfo())
                .select()
                .apis(basePackage("com.vdaoyun.systemapi.wx.miniapp.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo miniApiInfo() {
    	return new ApiInfoBuilder()
                .title("微道云RESTful APIs测试")
                .description("在这里可以欢快的测试你写的优秀(lese)代码了")
                .termsOfServiceUrl("")
                .contact("大鹏")
                .version("1.0")
                .build();
    }
    
    @Bean
    public Docket sysApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.groupName("sysApi")
                .apiInfo(sysApiInfo())
                .select()
                .apis(basePackage("com.vdaoyun.systemapi.web.base.param.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo sysApiInfo() {
    	return new ApiInfoBuilder()
                .title("微道云RESTful APIs测试")
                .description("在这里可以欢快的测试你写的优秀(lese)代码了")
                .termsOfServiceUrl("")
                .contact("大鹏")
                .version("1.0")
                .build();
    }
    


}
