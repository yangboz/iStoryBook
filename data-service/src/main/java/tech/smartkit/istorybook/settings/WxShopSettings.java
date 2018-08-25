package tech.smartkit.istorybook.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by yangboz on 23/08/2018.
 * @see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-grant
 */
@ConfigurationProperties(prefix = "wxshop")
@Component
public class WxShopSettings {

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    private String shopId;
    private String appId;
    private String appSecret;

    public WxShopSettings() {

    }

    public WxShopSettings(String shopId, String appId, String appSecret) {
        this.shopId = shopId;
        this.appId = appId;
        this.appSecret = appSecret;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public String toString() {
        return "WxShopSettings{" +
                "shopId='" + shopId + '\'' +
                ", appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                '}';
    }
}

