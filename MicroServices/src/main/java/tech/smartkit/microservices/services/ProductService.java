/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.services;


import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Title;
import com.google.gson.Gson;
import org.hibernate.annotations.LazyToOne;
import org.im4java.core.IM4JavaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.smartkit.microservices.models.Product;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import tech.smartkit.microservices.models.WxUserInfo;
import tech.smartkit.microservices.models.dao.WxAccountRepository;
import tech.smartkit.microservices.models.dto.IMConvertInfo;

/**
 * Hide the access to the microservice inside this local service.
 *
 * @author Paul Chapman
 */
@Service
public class ProductService {

//    @Autowired
//    @LoadBalanced
//    protected RestTemplate restTemplate;
//
//    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(ProductService.class
            .getName());

//    public ProductService(String serviceUrl) {
//        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
//                : "http://" + serviceUrl;
//    }
//    @Autowired
    ImageMagickService imageMagickService;
//    @Autowired
    WordPressService wordPressService;
    @Autowired
    WxAccountRepository wxAccountRepository;
//    @Autowired
    IpfsService ipfsService;
    WxAccountsService wxAccountsService;
    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show
     * this.
     */
    @PostConstruct
    public void main() {
//        // Can't do this in the constructor because the RestTemplate injection
//        // happens afterwards.
//        logger.warning("The RestTemplate request factory is "
//                + restTemplate.getRequestFactory().getClass());
        logger.info("ProductService invoked.");
    }

    public ProductService(WxAccountsService wxAccountsService,
            ImageMagickService imageMagickService,
            IpfsService ipfsService, WordPressService wordPressService){
        this.wxAccountsService = wxAccountsService;
        this.imageMagickService = imageMagickService;
        this.ipfsService = ipfsService;
        this.wordPressService = wordPressService;

    }

    //@see:https://developer.github.com/v3/repos/forks/
    public String fork(Long pid, Long uid) throws InterruptedException, IOException, IM4JavaException, PostNotFoundException, PostCreateException {
        logger.info("ImageMagick fork() invoked: pid: " + pid + ",uid:"+uid);
        //1.Image magick convert a new card.
        IMConvertInfo imConvertInfo = new IMConvertInfo();
        //1.1 get product related background.
        ClassPathResource bgImageFile = new ClassPathResource("assets/templates/1.jpg");
//                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//                StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
        imConvertInfo.setBackground(bgImageFile.getPath());//FIXME
        File imageMagickFileResult = imageMagickService.convert(imConvertInfo);
        ///1.2.replace face photo，@see：a brand new way to verify physical identity
        //for blockchain transactions，https://www.kairos.com/protocol
        WxUserInfo wxUserInfo = wxAccountRepository.findOne(uid);
        logger.info("WxUserInfo:"+wxUserInfo.toString());
        ///1.3.dynamic gene deployed by ETH smart contract.
        ///1.4.https://github.com/yangboz/iStoryBook/wiki/Vladimir_Propp
        //2.put on IPFS get pin hash ID,@see:https://github.com/ipfs/java-ipfs-api
        String ipfsHashID = ipfsService.put(imageMagickFileResult);
        //3.forking new post,same template,same card,just replace the name,
        Post forkedPost = wordPressService.getPost(pid);
        //5.publish to Wordpress, return status
        Post newPost = new Post();
        Title postTitle = new Title();
        postTitle.setRaw(ipfsHashID);
        newPost.setTitle(postTitle);
//                newPost.setAuthor(new List<Number>(wxUserInfo.getId()) {
//                });//FIXME
        wordPressService.createPost(newPost);
        //6.notify wechat mini-program refresh.
        return wordPressService.createPost(newPost).getPingStatus();
    }
}
