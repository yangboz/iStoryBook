package io.pivotal.microservices.products;


import org.im4java.core.MontageCmd;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class ProductController {
        protected Logger logger = Logger.getLogger(io.pivotal.microservices.products.ProductController.class
                .getName());

        /**
         GET / - List of products
         POST / - Add product - required : String name , String groupId, String userId
         GET /{id} - View product
         POST /{id} - Update product
         GET /{id}/images - View product images
         GET /image/{id}- View image
         POST /{id}/uploadimage - Upload product image
         GET /montage
         https://imagemagick.org/script/montage.php
         http://im4java.sourceforge.net/docs/dev-guide.html
         *
         */
        @RequestMapping("/product/")
        public void listProducts() {
                logger.info("List of products:");
        }

        /**
         * View product by id.
         */
        @RequestMapping("/product/{id}")
        public void connect(@PathVariable("id") String id) {
                logger.info(" View product, for "
                        + id);
        }

        /**
         * View product's montage.
         * @see http://im4java.sourceforge.net/tools/index.html
         * @see https://imagemagick.org/script/montage.php
         */
        @RequestMapping("/product/{id}/montage")
        public void montage(@PathVariable("id") String id) {
                logger.info(" View product's montage, for "
                        + id);
//TODO:                org.im4java.core.MontageCmd
        }

}
