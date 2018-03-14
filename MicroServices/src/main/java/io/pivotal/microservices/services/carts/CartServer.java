package io.pivotal.microservices.services.carts;

import io.pivotal.microservices.carts.CartController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaServer
public class CartServer {
    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args
     *            Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Tell server to look for registration.properties or registration.yml
        System.setProperty("spring.config.name", "cart-server");

        SpringApplication.run(io.pivotal.microservices.services.carts.CartServer.class, args);
    }

    /**
     * Create the controller, passing it the {@link } to use.
     *
     * @return
     */
    @Bean
    public CartController cartController() {
        return new CartController();
    }
}

