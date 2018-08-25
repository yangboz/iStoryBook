/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models.dto;

import java.net.URI;
import java.net.URISyntaxException;

public class WxShopInfo {

    private String shopId;

    private String appId;

    private String appSerect;

    public WxShopInfo(String shopId, String appId, String appSerect) {
        this.shopId = shopId;
        this.appId = appId;
        this.appSerect = appSerect;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSerect() {
        return appSerect;
    }

    public void setAppSerect(String appSerect) {
        this.appSerect = appSerect;
    }

    public WxShopInfo() {
    }


    public URI getShopOrderUrl(String accessToken) throws URISyntaxException {
        return new URI("http://"+shopId+".97866.com/api/mag.admin.order.list.json?access_token="+accessToken);
    }
    public String getShopProductUrl(String accessToken){
        return  "http://"+shopId+".97866.com/api/mag.admin.product.list.json?access_token="+accessToken;
    }

    public String getShopProductCategoryUrl(String accessToken){
        return  "http://"+shopId+".97866.com/api/mag.admin.product_category.list.json?access_token="+accessToken;
    }

    public String getShopTokenUrl(){
        return  "http://"+shopId+".97866.com/api/mag.token.json?appid="+this.getAppId()+"&secret="+this.getAppSerect();
    }

    @Override
    public String toString() {
        return "WxShopInfo{" +
                "shopId='" + shopId + '\'' +
                ", appId='" + appId + '\'' +
                ", appSerect='" + appSerect + '\'' +
                '}';
    }
}
