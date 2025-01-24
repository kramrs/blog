package com.kramrs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Collections;

/**
 * @Author: kramrs
 * @Description: Knife4j配置
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .protocols(Collections.singleton("https"))
                .host("https://www.kramrs.space")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kramrs.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("blog API文档")
                .description("基于SpringBoot + Vue3开发的博客项目")
                .termsOfServiceUrl("https://www.kramrs.space/api")
                .contact(new Contact("kramrs", "https://github.com/kramrs", "kramrs@163.com"))
                .version("1.0")
                .build();
    }

}
