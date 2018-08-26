/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tech.smartkit.istorybook.models.dto.*;
import tech.smartkit.istorybook.services.WxShopService;
import tech.smartkit.istorybook.settings.MemcachedSettings;
import tech.smartkit.istorybook.settings.WxShopSettings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;


@RestController
@RequestMapping(value ="/wxshop/order")
@Api(value="OrderController", description="Operations pertaining to wxshop order in iStoryBook")
public class OrderController {

    protected Logger logger = Logger.getLogger(OrderController.class
            .getName());

//    private static final String URI_WxShopID = "wx764fa42b23cd341f";

//    private static final String URI_WxShopOrder = "http://"+URI_WxShopID+".97866.com/api/mag.admin.order.list.json?access_token=";
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    WxShopSettings wxShopSettings;
    @Autowired
    MemcachedSettings memcachedSettings;

    @Autowired
    WxShopService wxShopService;

    private WxShopInfo wxShopInfo = null;
    private WxShopToken wxShopToken = null;

    @RequestMapping("/connect/")
    public WxShopToken wxShopConnect(@RequestBody WxShopInfo wxShopInfo) throws IOException, ExecutionException, InterruptedException {
        return connectWxShop(wxShopInfo);
    }

    private WxShopToken connectWxShop(WxShopInfo wxShopInfo) throws IOException, ExecutionException, InterruptedException {
        this.wxShopInfo = wxShopInfo;
        logger.info("wxShopInfo:"+wxShopInfo.toString());
        return this.refreshToken();
    }

    //NOTE:access_token 是开放接口的全局唯一接口调用凭据，公众号调用各接口时都需使用 access_token。
    // 开发者需要进行妥善保存。access_token 的有效期目前为2个小时，需定时刷新，
    // 重复获取将导致上次获取的 access_token 失效。
    //@see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open
    private WxShopToken refreshToken() throws IOException, ExecutionException, InterruptedException {
        //
        if(this.wxShopInfo == null){
            this.wxShopInfo  = new WxShopInfo(wxShopSettings.getShopId(),wxShopSettings.getAppId(),wxShopSettings.getAppSecret());
        }
        //
        WxShopToken wxShopTokenMemcached = (WxShopToken) this.getMemcachedClient().get("wxshoptoken");
        logger.info("wxShopToken from memcached:" + wxShopTokenMemcached);
        //using memcached to avoid duplication
        if(wxShopTokenMemcached == null) {
            // Start the clock
            long start = System.currentTimeMillis();
            // Kick of multiple, asynchronous lookups
            CompletableFuture<WxShopToken> page_token = wxShopService.asyncRefreshToken();
            // Wait until they are all done
            CompletableFuture.allOf(page_token).join();
            // Print results, including elapsed time
            logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
            this.wxShopToken = page_token.get();
            logger.info("wxShopToken:" +  this.wxShopToken);
            this.getMemcachedClient().set("wxshoptoken", this.wxShopToken.getExpires_in(), this.wxShopToken);
        }else {
            this.wxShopToken = wxShopTokenMemcached;
        }
        return this.wxShopToken;
    }

    private MemcachedClient getMemcachedClient() throws IOException {
        MemcachedClient memcachedClient= new MemcachedClient(new InetSocketAddress(memcachedSettings.getServers().get(0), 11211));
//        c.set("someKey_1", 2592000, users);
//        Object myObject=c.get("someKey_1");
//        System.out.println("Object 1: " + myObject);
//        System.out.println("Statistics: " + c.getStats());
//        System.out.println("Statistics of individual Items: " + c.getStats("items"));
//        c.delete("someKey_2");
//        c.shutdown();
//        c=null ;
        return memcachedClient;
    }

    /**
     /order
     GET / - List of orders
     POST / - Add order
     GET /{id} - View order
     POST /{id} - Update order
     *
     */
    private List<WxShopOrder> allOrders = new ArrayList<>();
    @RequestMapping(value="/", method= RequestMethod.GET, produces = "application/json")
    public List<WxShopOrder> listAllOrders() throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        this.refreshToken();
        //
        allOrders = wxShopService.asyncGetAllOrders(this.wxShopToken).get();
        logger.info("List all of orders:"+allOrders.toString());
//        List<WxShopProduct> allProducts= wxShopService.asyncGetAllProducts().get();
//        logger.info("List all of products:"+allProducts.toString());
        return allOrders;
    }

    /**
     * View order by id.
     * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
     */
    @RequestMapping("/{id}")
    public WxShopOrder getOrderById(@PathVariable("id") String id) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
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
    public WxShopOrder getOrderByStatus(@PathVariable("status") int status) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
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
    public List<WxShopOrder> getOrderByNickname(@PathVariable("nickName") String nickName) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        logger.info(" View order by nickName:"
                + nickName );
        allOrders = this.listAllOrders();
        List<WxShopOrder> myOrders = new ArrayList<>();
        //findAll
        for(WxShopOrder wxShopOrder: allOrders) {
            if (wxShopOrder.getBuyer().getBuyer_nickname().equals(nickName)){
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
    public WxShopOrder getOrderByIdAndStatus(@PathVariable("id") String id,@PathVariable("status") int status) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        logger.info(" View order by id:"
                + id +", and status:"+status);
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