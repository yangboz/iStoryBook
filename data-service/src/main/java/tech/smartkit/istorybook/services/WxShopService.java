package tech.smartkit.istorybook.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.smartkit.istorybook.models.dto.WxShopInfo;
import tech.smartkit.istorybook.models.dto.WxShopToken;
import tech.smartkit.istorybook.settings.WxShopSettings;

import java.util.concurrent.CompletableFuture;
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
    public CompletableFuture<WxShopToken> refreshToken() {
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
}
