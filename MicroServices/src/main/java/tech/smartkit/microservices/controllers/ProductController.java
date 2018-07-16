/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.controllers;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ProductController {
        protected Logger logger = Logger.getLogger(tech.smartkit.microservices.controllers.ProductController.class
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
        public void getProduct(@PathVariable("id") String id) {
                logger.info(" View product, for "
                        + id);
        }
        /**
         * Fork product by id,name,.
         * @see:https://developer.github.com/v3/repos/forks/
         */
        @RequestMapping("/product/{pid}/fork/{uid}")
        public String fork(@PathVariable("pid") String pid,@PathVariable("uid") String uid) {
                logger.info("ImageMagick fork() invoked: pid: " + pid + ",uid:"+uid);
                //1.same template,same card,just replace the name,
                //TODO:post to wordpress,@see: https://1dir1.net/doc/wordpress-java/
                //https://github.com/Afrozaar/wp-api-v2-client-java
                String baseUrl = "https://knighter.cn/";
                String username = "user";
                String password = "bitnami";
                boolean debug = false;

                final Wordpress client = ClientFactory.fromConfig(ClientConfig.of(baseUrl, username, password, false,debug));
                logger.info(client.getTags().toString());
                //2.replace face photo.
                //3.dynamic gene.
                //4.https://github.com/yangboz/iStoryBook/wiki/Vladimir_Propp
                return "fork status";
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
