/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 * @ref: https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
 */

package tech.smartkit.istorybook.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 .apis(RequestHandlerSelectors.basePackage("tech.smartkit.istorybook.controllers"))
//                .paths(regex("/*.*"))
//                .paths(PathSelectors.ant("**/*"))
                .paths(PathSelectors.any())
                .build()
                ;
                //@see：https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
//                .securitySchemes(Arrays.asList(securityScheme()))
//                .securityContexts(Arrays.asList(securityContext()));

    }
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("iStoryBook Spring Boot REST API")
                .description("\"Spring Boot REST API for iStoryBook Service\"")
                .version("0.0.1")
                .license("The Unlicense Version 1.0")
                .licenseUrl("https://github.com/yangboz/iStoryBook/blob/master/LICENSE\"")
                .contact(new Contact("yangboz", "http://smartkit.tech", "contact@smartkit.info"))
                .build();
//        return new ApiInfo(
//                "iStoryBook Spring Boot REST API",
//                "Spring Boot REST API for iStoryBook Service.",
//                "API TOS",
//                "Terms of service",
//                new Contact("yangboz", "http://smartkit.tech", "contact@smartkit.info"),
//                "License of API", "https://github.com/yangboz/iStoryBook/blob/master/LICENSE");
    }

//    private SecurityScheme securityScheme() {
//        GrantType grantType = new AuthorizationCodeGrantBuilder()
//                .tokenEndpoint(new TokenEndpoint(AUTH_SERVER + "/token", "oauthtoken"))
//                .tokenRequestEndpoint(
//                        new TokenRequestEndpoint(AUTH_SERVER + "/authorize", CLIENT_ID, CLIENT_ID))
//                .build();
//
//        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth")
//                .grantTypes(Arrays.asList(grantType))
//                .scopes(Arrays.asList(scopes()))
//                .build();
//        return oauth;
//    }

//    private AuthorizationScope[] scopes() {
//        AuthorizationScope[] scopes = {
//                new AuthorizationScope("read", "for read operations"),
//                new AuthorizationScope("write", "for write operations"),
//                new AuthorizationScope("foo", "Access foo API") };
//        return scopes;
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(
//                        Arrays.asList(new SecurityReference("spring_oauth", scopes())))
//                .forPaths(PathSelectors.regex("/foos.*"))
//                //                .paths(regex("/*.*"))
//                .forPaths(PathSelectors.any())
//                .build();
//    }
}