/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.services;

import com.afrozaar.wordpress.wpapi.v2.Wordpress;
import com.afrozaar.wordpress.wpapi.v2.api.Posts;
import com.afrozaar.wordpress.wpapi.v2.config.ClientConfig;
import com.afrozaar.wordpress.wpapi.v2.config.ClientFactory;
import com.afrozaar.wordpress.wpapi.v2.exception.PostCreateException;
import com.afrozaar.wordpress.wpapi.v2.exception.PostNotFoundException;
import com.afrozaar.wordpress.wpapi.v2.exception.WpApiParsedException;
import com.afrozaar.wordpress.wpapi.v2.model.Media;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.PostStatus;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import sun.java2d.Disposer;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

//@see: https://1dir1.net/doc/wordpress-java/
@Service
public class WordPressService {

    protected Logger logger = Logger.getLogger(WordPressService.class
            .getName());

    String baseUrl = "https://knighter.cn/";
    String username = "user";
    String password = "bitnami";

    boolean debug = false;

    @PostConstruct
    public void main() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("hard code wordpress info: "
                + baseUrl + username + password);
    }
////Post
    public Wordpress getWpClient(boolean debug){
        this.debug = debug;
        Wordpress client = ClientFactory.fromConfig(ClientConfig.of(baseUrl, username, password, false,debug));
        return client;
    }

    public Post createPost(Post post) throws PostCreateException {
        Post result = this.getWpClient(debug).createPost(post, PostStatus.publish);
        logger.info("createPost:"+result.toString());
        return result;
    }

    public Post getPost(Long id) throws PostNotFoundException{
        Post result = this.getWpClient(debug).getPost(id);
        logger.info("getPost:"+result.toString());
        return result;
    }

    public Post updatePost(Post post){
        Post result = this.getWpClient(debug).updatePost(post);
        logger.info("updatePost:"+result.toString());
        return result;
    }

    public Post deletePost(Post post){
        Post result = this.getWpClient(debug).deletePost(post);
        logger.info("deletePost:"+result.toString());
        return post;
    }
////Media
public Media createMedia(Media media) throws WpApiParsedException {
    Media result = this.getWpClient(debug).createMedia(media,null);
    logger.info("createMedia:"+result.toString());
    return result;
}

    public Media getMedia(Long id) throws PostNotFoundException{
        Media result = this.getWpClient(debug).getMedia(id);
        logger.info("getMedia:"+result.toString());
        return result;
    }

    public Media updateMedia(Media media){
        Media result = this.getWpClient(debug).updateMedia(media);
        logger.info("updateMedia:"+result.toString());
        return result;
    }

    public boolean deleteMedia(Media media){
        boolean result = this.getWpClient(debug).deleteMedia(media);
        logger.info("deleteMedia:"+result);
        return result;
    }

    //TODO:page,tag,category...
}