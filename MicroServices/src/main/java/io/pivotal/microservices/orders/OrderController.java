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
    public void listAllOrders() {
        logger.info("List all of orders:");
    }

    /**
     * View order by id.
     * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
     */
    @RequestMapping("/order/{id}")
    public void listOrders(@PathVariable("id") String id) {
        logger.info(" View orders, for "
                + id);
        
    }
}