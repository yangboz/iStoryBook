package io.pivotal.microservices.orders;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class OrderController {
    protected Logger logger = Logger.getLogger(io.pivotal.microservices.orders.OrderController.class
            .getName());

    /**
     /order
     GET / - List of orders
     POST / - Add order
     GET /{id} - View order
     POST /{id} - Update order
     *
     */
    @RequestMapping("/order/")
    public void listOrders() {
        logger.info("List of orders:");
    }

    /**
     * View order by id.
     */
    @RequestMapping("/order/{id}")
    public void connect(@PathVariable("id") String id) {
        logger.info(" View product, for "
                + id);
    }
}