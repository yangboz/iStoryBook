/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.mservices.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import tech.smartkit.microservices.controllers.GroupController;
import tech.smartkit.microservices.controllers.ProductController;

@SpringBootApplication
@EnableEurekaServer
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

    /**
     * Create the controller, passing it the {@link } to use.
     *
     * @return
     */
    @Bean
    public ProductController productController() {
        return new ProductController();
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
}

