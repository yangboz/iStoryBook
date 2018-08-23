/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.controllers;

import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.smartkit.istorybook.services.ProductService;

import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping(value ="/product")
@Api(value="ProductController", description="Operations pertaining to product(cards of storybook) in iStoryBook")
public class ProductController {
        //
        protected Logger logger = Logger.getLogger(ProductController.class
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
        @ApiOperation(value = "View a list of available accounts", response = Iterable.class)
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "Successfully retrieved list"),
                @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
                @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
                @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
        }
        )
        @RequestMapping(value = "/", method= RequestMethod.GET, produces = "application/json")
        public Iterable list(){
                logger.info("List of products:");
//                Iterable productList = accountRepository.findAll();
                return null;
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
