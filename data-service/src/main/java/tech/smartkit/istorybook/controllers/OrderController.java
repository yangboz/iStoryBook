/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.controllers;

import io.swagger.annotations.Api;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tech.smartkit.istorybook.models.dto.WxShopInfo;
import tech.smartkit.istorybook.models.dto.WxShopOrder;
import tech.smartkit.istorybook.models.dto.WxShopToken;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping(value ="/wxshop/order")
@Api(value="OrderController", description="Operations pertaining to wxshop order in iStoryBook")
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

    @RequestMapping("/connect/")
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
    @RequestMapping("/")
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
    @RequestMapping("/{id}")
    public WxShopOrder getOrderById(@PathVariable("id") String id) throws URISyntaxException {
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
    @RequestMapping("/byStatus/{status}")
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
    @RequestMapping("/byNickname/{nickName}")
    public List<WxShopOrder> getOrderByNickname(@PathVariable("nickName") String nickName) throws URISyntaxException {
        logger.info(" View order by nickName:"
                + nickName );
        allOrders = this.listAllOrders();
        List<WxShopOrder> myOrders = new ArrayList<>();
        //findAll
        for(WxShopOrder wxShopOrder: allOrders) {
            if (wxShopOrder.getBuyer_nickname().equals(nickName)){
                logger.info("getOrderByNickname:" + wxShopOrder.toString());
                myOrders.add(wxShopOrder);
            }
        }
        return myOrders;
    }
    /**
     * View order by nickName and status(4).
     * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
     */
    @RequestMapping("/byIdAndStatus/{id}/{status}")
    public WxShopOrder getOrderByIdAndStatus(@PathVariable("id") String id,@PathVariable("status") int status) throws URISyntaxException {
        logger.info(" View order by id:"
                + id +", znd status:"+status);
        allOrders = this.listAllOrders();
        //findOne
        for(WxShopOrder wxShopOrder: allOrders){
            if(wxShopOrder.getId().equals(id) && wxShopOrder.getStatus()==status ) {
                logger.info("getOrderByIdAndStatus:" + wxShopOrder.toString());
                return wxShopOrder;
            }
        }
        return null;
    }
//
}