package tech.smartkit.istorybook.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
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


    private WxShopInfo wxShopInfo = null;
    private WxShopToken wxShopToken = null;

    @Autowired
    WxShopSettings wxShopSettings;

    public WxShopService() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public WxShopInfo getWxShopInfo() {
        if(this.wxShopInfo == null){
            this.wxShopInfo  = new WxShopInfo(wxShopSettings.getShopId(),wxShopSettings.getAppId(),wxShopSettings.getAppSecret());
        }
        return wxShopInfo;
    }

    @Async
    public CompletableFuture<WxShopToken> asyncRefreshToken() {
        //
        String wxShopTokenUrlStr = this.getWxShopInfo().getShopTokenUrl();
        wxShopToken = restTemplate.getForObject(wxShopTokenUrlStr, WxShopToken.class);
//        // Artificial delay of 1s for demonstration purposes
//        Thread.sleep(1000L);
        logger.info("wxShopToken:" + this.wxShopToken);
        return CompletableFuture.completedFuture(wxShopToken);
    }

    @Async
    public CompletableFuture<List<WxShopOrder>> asyncGetAllOrders(WxShopToken wxShopToken) throws URISyntaxException, IOException {
        //https://stackoverflow.com/questions/30936863/resttemplate-getforentity-map-to-list-of-objects
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        URI wxShopOrdersUrl = this.getWxShopInfo().getShopOrderUrl(wxShopToken.getAccess_token());
        logger.info("wxShopOrdersUrl:"+wxShopOrdersUrl.toString());
        //@see: https://stackoverflow.com/questions/8297215/spring-resttemplate-get-with-parameters
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(wxShopOrdersUrl.toString())
                .queryParam("start_time", 1535189178L)
                .queryParam("end_time", 1535275578L)
                .queryParam("status", 4);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        //
        ResponseEntity<String> jsonResp = restTemplate.exchange(wxShopOrdersUrl, HttpMethod.GET, requestEntity, responseType);
        logger.info(jsonResp.toString());
        WxShopOrders wxShopOrders = new ObjectMapper().readValue(jsonResp.getBody().toString(), new TypeReference<WxShopOrders>() { });
        logger.info("wxShopOrders:"+wxShopOrders.toString());
        List<WxShopOrder> allOrders = wxShopOrders.getOrders();
        logger.info("asyncGet all of orders:"+allOrders.toString());
        return CompletableFuture.completedFuture(allOrders);
    }

    @Async
    public CompletableFuture<List<WxShopProduct>> asyncGetAllProducts(WxShopToken wxShopToken) throws URISyntaxException, IOException {
        //https://stackoverflow.com/questions/30936863/resttemplate-getforentity-map-to-list-of-objects
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
        HttpEntity<Object> requestEntity = null;
        String wxShopProductsUrl = this.wxShopInfo.getShopProductUrl(wxShopToken.getAccess_token());
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
