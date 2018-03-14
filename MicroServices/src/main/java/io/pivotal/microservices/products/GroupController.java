package io.pivotal.microservices.products;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class GroupController {
    protected Logger logger = Logger.getLogger(GroupController.class
            .getName());

    /**
     /group
     GET / - List of groups
     POST / - Add group
     GET /{id} - View group
     POST /{id} - Update group
     *
     */
    @RequestMapping("/group/")
    public void listProducts() {
        logger.info("List of product groups:");
    }

    /**
     * View cart by id.
     */
    @RequestMapping("/group/{id}")
    public void connect(@PathVariable("id") String id) {
        logger.info(" View product groups, for "
                + id);
    }
}
