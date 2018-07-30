/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.controllers;

import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tech.smartkit.microservices.models.WxUserInfo;
import tech.smartkit.microservices.models.dto.WxShopInfo;
import tech.smartkit.microservices.models.dto.WxShopOrder;
import tech.smartkit.microservices.models.dto.WxShopToken;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static io.swagger.models.HttpMethod.GET;


@RefreshScope
@RestController
public class OrderController {

    protected Logger logger = Logger.getLogger(OrderController.class
            .getName());

//    private static final String URI_WxShopID = "wx764fa42b23cd341f";

//    private static final String URI_WxShopOrder = "http://"+URI_WxShopID+".97866.com/api/mag.admin.order.list.json?access_token=";
    private RestTemplate restTemplate = new RestTemplate();

    private WxShopInfo wxShopInfo;
    private WxShopToken wxShopToken;

//    @Value("${WxShopAppID}")
//    private String WxShopAppID = "jamqxbnk9lsyl8d7sg";
//
//    @Value("${WxShopAppSecret}")
//    private String WxShopAppSecret = "dmfztlxbe7wzydurexme3icijwqj6z11";

//    @Autowired
//    public void setWxShopConfig(WxShopConfig wxShopConfig) {
//        this.wxShopConfig = wxShopConfig;
//        logger.info("wxShopConfig:"+wxShopConfig.toString());
//    }

    @RequestMapping("/wxshop/connect/")
    public WxShopToken wxShopConnect(@RequestBody WxShopInfo wxShopInfo){
        this.wxShopInfo = wxShopInfo;
        logger.info("wxShopInfo:"+wxShopInfo.toString());
        return this.getWxShopToken();
    }

//    @RequestMapping("/wxshop/token")
    public WxShopToken getWxShopToken(){
        wxShopToken = restTemplate.getForObject(wxShopInfo.getShopTokenUrl(), WxShopToken.class);
        logger.info("wxShopToken:"+wxShopToken);
        return wxShopToken;
    }

    //TODO:implementation
    private boolean isTokenExpired(){
        return false;//curToken
    }

    //TODO:implementation
    private void refreshTokenString(){

    }

    /**
     /order
     GET / - List of orders
     POST / - Add order
     GET /{id} - View order
     POST /{id} - Update order
     *
     */
    private List<WxShopOrder> allOrders;
    @RequestMapping("/wxshop/order/")
    public List<WxShopOrder> listAllOrders() throws URISyntaxException {
        if(!isTokenExpired()) {
            //https://stackoverflow.com/questions/30936863/resttemplate-getforentity-map-to-list-of-objects
            ParameterizedTypeReference<List<WxShopOrder>> responseType = new ParameterizedTypeReference<List<WxShopOrder>>() {};
            HttpEntity<Object> requestEntity = null;
            ResponseEntity<List<WxShopOrder>> resp = restTemplate.exchange(wxShopToken.getAccess_token(), HttpMethod.GET, requestEntity, responseType);
            allOrders = resp.getBody();
//            allOrders = restTemplate.getForEntity(wxShopInfo.getShopOrderUrl(,List<WxShopOrder>().getClass());
            logger.info("List all of orders:"+allOrders.toString());
            return allOrders;
        }
        return null;
    }

    /**
     * View order by id.
     * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
     */
    @RequestMapping("/wxshop/order/{id}")
    public WxShopOrder getOrder(@PathVariable("id") String id) throws URISyntaxException {
        logger.info(" View orders, for " + id);
        allOrders = this.listAllOrders();
        //findOne
        for (WxShopOrder wxShopOrder : allOrders) {
            if (wxShopOrder.getId().equals(id))
                logger.info("findOneById:" + wxShopOrder.toString());
            return wxShopOrder;
        }
        return null;
    }

    /**
     * View order by status.
     * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
     */
    @RequestMapping("/wxshop/order/status/{status}")
    public WxShopOrder getOrderByStatus(@PathVariable("status") int status) throws URISyntaxException {
        logger.info(" View order by  status:"+status);
        allOrders = this.listAllOrders();
        //findOne
        for(WxShopOrder wxShopOrder: allOrders) {
            if (wxShopOrder.getStatus() == status) {
                logger.info("findOneByIdAndStatus:" + wxShopOrder.toString());
                return wxShopOrder;
            }
        }
        return null;
    }

    /**
     * View order by id and status.
     * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
     */
    @RequestMapping("/wxshop/order/{id}/{status}")
    public WxShopOrder getOrderByIdAndStatus(@PathVariable("id") String id,@PathVariable("status") int status) throws URISyntaxException {
        logger.info(" View order by id:"
                + id +", znd status:"+status);
        allOrders = this.listAllOrders();
        //findOne
        for(WxShopOrder wxShopOrder: allOrders){
            if(wxShopOrder.getId().equals(id) && wxShopOrder.getStatus()==status )
                logger.info("findOneByIdAndStatus:"+wxShopOrder.toString());
            return wxShopOrder;
        }
        return null;
    }
//
}