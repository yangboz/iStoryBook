/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.controllers;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tech.smartkit.microservices.models.WxShopToken;

import java.util.logging.Logger;


@RefreshScope
@RestController
public class OrderController {

    protected Logger logger = Logger.getLogger(OrderController.class
            .getName());

    private static final String URI_WxShopID = "wx764fa42b23cd341f";

    private static final String URI_WxShopOrder = "http://"+URI_WxShopID+".97866.com/api/mag.admin.order.list.json?access_token=";
    private RestTemplate restTemplate = new RestTemplate();


//    WxShopConfig wxShopConfig;

//    @Value("${WxShopAppID}")
    private String WxShopAppID = "jamqxbnk9lsyl8d7sg";
//
//    @Value("${WxShopAppSecret}")
    private String WxShopAppSecret = "dmfztlxbe7wzydurexme3icijwqj6z11";

//    @Autowired
//    public void setWxShopConfig(WxShopConfig wxShopConfig) {
//        this.wxShopConfig = wxShopConfig;
//        logger.info("wxShopConfig:"+wxShopConfig.toString());
//    }

    private static  final String URI_WxShopToken = "http://"+URI_WxShopID+".97866.com/api/mag.token.json?";
    @RequestMapping("/wxshop/token")
    public String getWxShopToken(){
        WxShopToken wxShopToken = restTemplate.getForObject(URI_WxShopToken+"appid="+WxShopAppID+"&secret="+WxShopAppSecret, WxShopToken.class);
        logger.info("wxShopToken:"+wxShopToken);
        curToken = wxShopToken.getAccess_token();
        return curToken;
    }

    private String curToken  = "";
    private String getTokenString(){
        if(isTokenExpired()){
            refreshTokenString();
        }
        return "?access_token="+curToken;
    }

    private boolean isTokenExpired(){
        return false;//curToken
    }

    private void refreshTokenString(){
        //TODO
    }

    /**
     /order
     GET / - List of orders
     POST / - Add order
     GET /{id} - View order
     POST /{id} - Update order
     *
     */
    @RequestMapping("/wxshop/order/")
    public void listAllOrders() {
        if(!isTokenExpired()) {
            Object[] objects = restTemplate.getForObject(URI_WxShopOrder+getTokenString(), Object[].class);
            logger.info("List all of orders:"+objects.toString());
        }
    }

    /**
     * View order by id.
     * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
     */
    @RequestMapping("/wxshop/order/{id}")
    public void getOrder(@PathVariable("id") String id) {
        logger.info(" View orders, for "
                + id);
        Object[] orders = restTemplate.getForObject(URI_WxShopOrder+getTokenString(), Object[].class);
        logger.info(orders.toString());
    }

    /**
     * View order by id.
     * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
     */
    @RequestMapping("/wxshop/order/{id}/{status}")
    public void getOrderByStatus(@PathVariable("id") String id,@PathVariable("status") String status) {
        logger.info(" View orders, for "
                + id);

    }
}