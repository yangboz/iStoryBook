/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.controllers;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Title;
import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.smartkit.microservices.services.ProductService;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping(value ="/product")
public class ProductController {
        //
        protected Logger logger = Logger.getLogger(tech.smartkit.microservices.controllers.ProductController.class
                .getName());
        ProductService productService;

        @Autowired
        public ProductController(ProductService productService) {
                this.productService  = productService;
                logger.info("ProductController autowired.");
        }
        /**
         GET / - List of products
         POST / - Add product - required : String name , String groupId, String userId
         GET /{id} - View product
         POST /{id} - Update product
         GET /{id}/images - View product images
         GET /image/{id}- View image
         POST /{id}/uploadimage - Upload product image
         GET /fork quick fork a product
         GET /montage
         https://imagemagick.org/script/montage.php
         http://im4java.sourceforge.net/docs/dev-guide.html
         *
         */
        @RequestMapping("/list/")
        public void list() {
                logger.info("List of products:");
        }

        /**
         * View product by id.
         */
        @RequestMapping("/{id}")
        public void getProduct(@PathVariable("id") String id) {
                logger.info(" View product, for "
                        + id);
        }
        /**
         * Fork product by id,name,.
         * @see:https://developer.github.com/v3/repos/forks/
         */
        @RequestMapping("/{pid}/fork/{uid}")
        public String fork(@PathVariable("pid") Long pid,@PathVariable("uid") Long uid) throws PostCreateException, PostNotFoundException, InterruptedException, IOException, IM4JavaException {
                return productService.fork(pid,uid);
        }

//        /**
//         * View product's montage.
//         * @see http://im4java.sourceforge.net/tools/index.html
//         * @see https://imagemagick.org/script/montage.php
//         */
//        @RequestMapping("/product/{id}/montage")
//        public void montage(@PathVariable("id") String id) throws InterruptedException, IOException, IM4JavaException {
//                logger.info(" View product's montage, for "
//                        + id);
//                imageMagickService.montage(new IMMontageInfo());
//        }

}
