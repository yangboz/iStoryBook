/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.services;


import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Title;
import org.apache.commons.io.FileUtils;
import org.im4java.core.IM4JavaException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import tech.smartkit.istorybook.models.WxAccount;
import tech.smartkit.istorybook.models.dto.IMConvertInfo;

/**
 * Hide the access to the microservice inside this local service.
 *
 * @author Paul Chapman
 */
@Service
public class ProductService {

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

    public ProductService(){
    }
    public ProductService(WxAccountsService wxAccountsService,
                          ImageMagickService imageMagickService,
                          IpfsService ipfsService, WordPressService wordPressService){
        this.wxAccountsService = wxAccountsService;
        this.imageMagickService = imageMagickService;
        this.ipfsService = ipfsService;
        this.wordPressService = wordPressService;
        logger.info(wxAccountsService.toString());
        logger.info(imageMagickService.toString());
        logger.info(ipfsService.toString());
        logger.info(wordPressService.toString());

    }

    //@see:https://developer.github.com/v3/repos/forks/
    public String fork(Long pid, Long uid) throws InterruptedException, IOException, IM4JavaException, PostNotFoundException, PostCreateException {
        logger.info("ImageMagick fork() invoked: pid: " + pid + ",uid:"+uid);
        //0.ImageMagick test
//        String input = "/Users/yangboz/git/iStoryBook/MicroServices/src/main/resources/assets/input/input.jpg";//default.
        String output = "/Users/yangboz/git/iStoryBook/MicroServices/src/main/resources/assets/output/output.jpg";//default.
//        return "OK";
        //1.Image magick convert a new card.
        IMConvertInfo imConvertInfo = new IMConvertInfo();
        //1.1 get product related background.
        String temmplateInput = "/Users/yangboz/git/iStoryBook/MicroServices/src/main/resources/assets/template/"+pid+".jpg";
        imageMagickService.watermarkWithText(temmplateInput,"© smartkit.info 2018",output);
        ///1.2.replace face photo，@see：a brand new way to verify physical identity
        //for blockchain transactions，https://www.kairos.com/protocol
        Iterable wxAccountList = wxAccountsService.listAll();
        logger.info("wxAccountList:"+wxAccountList.toString());
        WxAccount wxUserInfo = wxAccountsService.findOne(uid);
        logger.info("WxAccount:"+wxUserInfo.toString());
        File avatarImageFile = null;
        FileUtils.copyURLToFile(new URL(wxUserInfo.getAvatarUrl()), avatarImageFile);
        //image magick composite all.

        ///1.3.dynamic gene deployed by ETH smart contract.
        ///1.4.https://github.com/yangboz/iStoryBook/wiki/Vladimir_Propp
        //2.put on IPFS get pin hash ID,@see:https://github.com/ipfs/java-ipfs-api
        File imageMagickFile = new File(output);
        String ipfsHashID = ipfsService.put(imageMagickFile);
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
