/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class CartController {
    protected Logger logger = Logger.getLogger(CartController.class
            .getName());

    /**
     /cart
     POST / - Create cart
     GET /{id} - Get carts for card with ID = {id}
     POST /{id} - Add CartItem to cart with ID {id}
     DELETE /{id}/{product_id} - Remove product with ID {product_id} from cart with ID {id}
     POST /{id}/quantity - Updates cart item, i.e. set product quantity
     POTS /{id}/order - Create order from cart
     *
     */
    @RequestMapping("/cart/")
    public void listProducts() {
        logger.info("List of carts:");
    }

    /**
     * View cart by id.
     */
    @RequestMapping("/cart/{id}")
    public void connect(@PathVariable("id") String id) {
        logger.info(" View cart, for "
                + id);
    }
}

