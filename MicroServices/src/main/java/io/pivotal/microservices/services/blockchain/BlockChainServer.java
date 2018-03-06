package io.pivotal.microservices.services.blockchain;

import io.pivotal.microservices.blockchain.BlockChainController;
import io.pivotal.microservices.services.registration.RegistrationServer;
import io.pivotal.microservices.services.web.WebAccountsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaServer
public class BlockChainServer {
    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args
     *            Program arguments - ignored.
     */
    public static void main(String[] args) {
        // Tell server to look for registration.properties or registration.yml
        System.setProperty("spring.config.name", "blockchain-server");

        SpringApplication.run(RegistrationServer.class, args);
    }

    /**
     * Create the controller, passing it the {@link } to use.
     *
     * @return
     */
    @Bean
    public BlockChainController blockChainController() {
        return new BlockChainController();
    }
}
