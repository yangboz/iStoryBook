package tech.smartkit.istorybook.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.smartkit.istorybook.models.dto.*;
import tech.smartkit.istorybook.settings.WxShopSettings;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//@see:https://spring.io/guides/gs/async-method/
@Service
public class WxShopService {

    private static final Logger logger = LoggerFactory.getLogger(WxShopService.class);

    private final RestTemplate restTemplate;

    @Autowired
    WxShopSettings wxShopSettings;

    private WxShopInfo wxShopInfo = null;
    private WxShopToken wxShopToken = null;

    public WxShopService() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    @Async
    public CompletableFuture<WxShopToken> asyncRefreshToken() {
        if(this.wxShopInfo == null){
            this.wxShopInfo = new WxShopInfo(wxShopSettings.getShopId(),wxShopSettings.getAppId(),wxShopSettings.getAppSecret());
        }
        String wxShopTokenUrlStr = wxShopInfo.getShopTokenUrl();
        wxShopToken = restTemplate.getForObject(wxShopTokenUrlStr, WxShopToken.class);
//        // Artificial delay of 1s for demonstration purposes
//        Thread.sleep(1000L);
        logger.info("wxShopToken:" + this.wxShopToken);
        return CompletableFuture.completedFuture(wxShopToken);
    }

    @Async
    public CompletableFuture<List<WxShopOrder>> asyncGetAllOrders() throws URISyntaxException, IOException {
        this.asyncRefreshToken();
        //https://stackoverflow.com/questions/30936863/resttemplate-getforentity-map-to-list-of-objects
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        HttpEntity<Object> requestEntity = null;
        URI wxShopOrdersUrl = this.wxShopInfo.getShopOrderUrl(this.wxShopToken.getAccess_token());
        logger.info("wxShopOrdersUrl:"+wxShopOrdersUrl.toString());
        ResponseEntity<String> jsonResp = restTemplate.exchange(wxShopOrdersUrl, HttpMethod.GET, requestEntity, responseType);
        logger.info(jsonResp.toString());
        WxShopOrders wxShopOrders = new ObjectMapper().readValue(jsonResp.getBody().toString(), new TypeReference<WxShopOrders>() { });
        logger.info("wxShopOrders:"+wxShopOrders.toString());
        List<WxShopOrder> allOrders = wxShopOrders.getOrders();
        logger.info("asyncGet all of orders:"+allOrders.toString());
        return CompletableFuture.completedFuture(allOrders);
    }

    @Async
    public CompletableFuture<List<WxShopProduct>> asyncGetAllProducts() throws URISyntaxException, IOException {
        this.asyncRefreshToken();
        //https://stackoverflow.com/questions/30936863/resttemplate-getforentity-map-to-list-of-objects
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        HttpEntity<Object> requestEntity = null;
        String wxShopProductsUrl = this.wxShopInfo.getShopProductUrl(this.wxShopToken.getAccess_token());
        logger.info("wxShopProductsUrl:"+wxShopProductsUrl.toString());
        ResponseEntity<String> jsonResp = restTemplate.exchange(wxShopProductsUrl, HttpMethod.GET, requestEntity, responseType);
        logger.info("jsonResp："+jsonResp.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        WxShopProducts wxShopProducts = objectMapper.readValue(jsonResp.getBody().toString(), new TypeReference<WxShopProducts>() { });
        logger.info("wxShopProducts:"+wxShopProducts.toString());
        List<WxShopProduct> allProducts = wxShopProducts.getProducts();
        logger.info("asyncGet all of products:"+allProducts.toString());
        return CompletableFuture.completedFuture(allProducts);
    }
}
