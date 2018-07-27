/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 * @ref: https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
 */

package tech.smartkit.microservices.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("tech.smartkit.microservices.controllers"))
//                .paths(regex("/*.*"))
//                .paths(PathSelectors.ant("**/*"))
                .paths(PathSelectors.any())
                .build();

    }
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("iStoryBook Spring Boot REST API")
                .description("\"Spring Boot REST API for Online StoryBook Service\"")
                .version("1.0.0")
                .license("The Unlicense Version 1.0")
                .licenseUrl("https://github.com/yangboz/iStoryBook/blob/master/LICENSE\"")
                .contact(new Contact("yangboz", "http://smartkit.tech", "contact@smartkit.info"))
                .build();
    }
}