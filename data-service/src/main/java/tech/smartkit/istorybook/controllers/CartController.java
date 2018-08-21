/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.controllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(value ="/wxshop/cart")
@Api(value="CartController", description="Operations pertaining to wxshop cart in iStoryBook")
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
     */
    /**
     * View wxshop orders by id.
     */
    @RequestMapping("/byNickName/{nickName}")
    public void listProducts(@PathVariable("nickName") String nickName) {
        logger.info("List of cart by nickName:");
    }

    /**
     * View wxshop orders by id.
     */
    @RequestMapping("/byId/{id}")
    public void connect(@PathVariable("id") int id) {
        logger.info(" View cart, for "
                + id);
    }
}

