/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package io.pivotal.microservices.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

/**
 * Hide the access to the microservice inside this local service.
 *
 * @author Paul Chapman
 */
@Service
public class ProductService {

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(ProductService.class
            .getName());

    public ProductService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
                : "http://" + serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show
     * this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is "
                + restTemplate.getRequestFactory().getClass());
    }

    //http://www.imagemagick.org/Usage/montage/#montage
    public String montage(String id) {
        logger.info("ImageMagick montage() invoked: for " + id);
        return restTemplate.getForObject(serviceUrl + "/product/{id}",
                Product.class, id).url;
    }
    //http://www.imagemagick.org/Usage/compose/
    public String composite(String id) {
        logger.info("ImageMagick composite() invoked: for " + id);
        return restTemplate.getForObject(serviceUrl + "/product/{id}",
                Product.class, id).url;
    }

    //http://www.imagemagick.org/Usage/montage/#convert
    public String convert(String id) {
        logger.info("ImageMagick montage() invoked: for " + id);
        return restTemplate.getForObject(serviceUrl + "/product/{id}",Product.class, id).url;
    }


    //http://www.imagemagick.org/Usage/montage/#convert
    public String label(String id) {
        logger.info("ImageMagick montage() invoked: for " + id);
        return restTemplate.getForObject(serviceUrl + "/product/{id}",
                Product.class, id).url;
    }

    //http://www.imagemagick.org/Usage/montage/#convert
    public String watermark(String id) {
        logger.info("ImageMagick montage() invoked: for " + id);
        return restTemplate.getForObject(serviceUrl + "/product/{id}",
                Product.class, id).url;
    }
    //@see:https://developer.github.com/v3/repos/forks/
    public String fork(String pid,String uid,String name) {
        logger.info("ImageMagick fork() invoked: pid: " + pid + ",uid:"+uid);
        //1.same template,same card,just replace the name,
        //TODO:
        //2.replace face photo.
        //3.dynamic gene.
        //4.https://github.com/yangboz/iStoryBook/wiki/Vladimir_Propp
        return "fork status";
    }
}
