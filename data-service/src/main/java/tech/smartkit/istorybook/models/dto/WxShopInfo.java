/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models.dto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class WxShopInfo {

    private String shopId;

    private String appId;

    private String appSerect;

    long DAY_IN_MS = 1000 * 60 * 60 * 24;

    private long TIME_START  = 1535165358;//开始时间戳，默认为一天前,Wednesday, July 25, 2018 12:00:00 AM UTC

    private long TIME_END  = 1535251758;//结束时间戳，默认为：现在

    //status	订单状态，1 - 待付款 2 - 待发货 3 - 已发货 4 - 交易完成 5 - 申请退款 6 -退款成功 7 - 主动关闭 8 - 自动关闭 10 - 待成团
    private static final int STATUS_CLOSED = 4;

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

    private long getDaysAgo(){
        return new Date().getTime();
    }

    private long getNow(){
        return new Date().getTime();
    }


    public URI getShopOrderUrl(String accessToken) throws URISyntaxException {
        return new URI("http://"+shopId+".97866.com/api/mag.admin.order.list.json?access_token="+accessToken
//                +"&start_time="+TIME_START
// +"&end_time="+TIME_END
                +"&status="+STATUS_CLOSED
//        +"&blog_id=4160&orderbv=time"
        );
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
