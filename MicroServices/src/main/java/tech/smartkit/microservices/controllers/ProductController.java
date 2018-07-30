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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.smartkit.microservices.models.WxUserInfo;
import tech.smartkit.microservices.models.dao.WxAccountRepository;
import tech.smartkit.microservices.models.dto.IMConvertInfo;
import tech.smartkit.microservices.models.dto.IMMontageInfo;
import tech.smartkit.microservices.services.ImageMagickService;
import tech.smartkit.microservices.services.WordPressService;

import javax.jws.Oneway;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ProductController {
        protected Logger logger = Logger.getLogger(tech.smartkit.microservices.controllers.ProductController.class
                .getName());

        @Autowired
        ImageMagickService imageMagickService;
        @Autowired
        WordPressService wordPressService;
        @Autowired
        WxAccountRepository wxAccountRepository;
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
        public String fork(@PathVariable("pid") Long pid,@PathVariable("uid") Long uid) throws PostCreateException, PostNotFoundException, InterruptedException, IOException, IM4JavaException {
                logger.info("ImageMagick fork() invoked: pid: " + pid + ",uid:"+uid);
                //1.Image magick convert a new card.
                IMConvertInfo imConvertInfo = new IMConvertInfo();
                imConvertInfo.setBackground("");//FIXME
                imageMagickService.convert(imConvertInfo);
                ///1.2.replace face photo.
                WxUserInfo wxUserInfo = wxAccountRepository.findOne(uid);
                logger.info("WxUserInfo:"+wxUserInfo.toString());

                ///1.3.dynamic gene.
                ///1.4.https://github.com/yangboz/iStoryBook/wiki/Vladimir_Propp
                //2.put on IPFS get pin hash ID,@see:https://github.com/ipfs/java-ipfs-api

                String ipfsHashID = "";
                //3.forking new post,same template,same card,just replace the name,
                Post forkedPost = wordPressService.getPost(pid);
                //5.publish to Wordpress, return status
                Post newPost = new Post();
                Title newTitle = new Title();
                newTitle.setRaw(ipfsHashID);
                newPost.setTitle(newTitle);
                newPost.setAuthor(null);//FIXME

                wordPressService.createPost(newPost);
                //6.notify wechat mini-program refresh.
                return "fork status";
        }

        /**
         * View product's montage.
         * @see http://im4java.sourceforge.net/tools/index.html
         * @see https://imagemagick.org/script/montage.php
         */
        @RequestMapping("/product/{id}/montage")
        public void montage(@PathVariable("id") String id) throws InterruptedException, IOException, IM4JavaException {
                logger.info(" View product's montage, for "
                        + id);
                imageMagickService.montage(new IMMontageInfo());
        }

}
