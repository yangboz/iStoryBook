/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.mservices.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tech.smartkit.microservices.controllers.GroupController;
import tech.smartkit.microservices.controllers.ProductController;
import tech.smartkit.microservices.models.WxUserInfo;
import tech.smartkit.microservices.models.dao.WxAccountRepository;
import tech.smartkit.microservices.services.*;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("tech.smartkit.microservices.models.dao")
@EntityScan("tech.smartkit.microservices.models")
//@EnableEurekaServer
//@EnableAutoConfiguration
@EnableDiscoveryClient
public class ProductServer {
    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args
     *            Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Tell server to look for registration.properties or registration.yml
        System.setProperty("spring.config.name", "product-server");

        SpringApplication.run(ProductServer.class, args);
    }

//    @Autowired
//    ImageMagickService imageMagickService;
//    @Autowired
//    WordPressService wordPressService;
//@Autowired
//    WxAccountRepository wxAccountRepository;
//    @Autowired
//    IpfsService ipfsService;

//    @Autowired
//    ProductService productService;
    /**
     * Create the controller, passing it the {@link } to use.
     *
     * @return
     */
    @Bean
    public ProductController productController() {
        return new ProductController(this.productService());
    }
    /**
     * Create the controller, passing it the {@link } to use.
     *
     * @return
     */
    @Bean
    public GroupController groupController() {
        return new GroupController();
    }
    //
    @Bean
    public ProductService productService(){ return new ProductService(this.wxAccountsService(),this.imageMagickService()
            ,this.ipfsService(),this.wordPressService());}
    @Bean
    public ImageMagickService imageMagickService(){ return new ImageMagickService();}
    @Bean
    public IpfsService ipfsService(){ return new IpfsService();}
    @Bean
    public WordPressService wordPressService(){ return new WordPressService();}
    @Bean
    public WxAccountsService wxAccountsService(){ return new WxAccountsService(); }
}

