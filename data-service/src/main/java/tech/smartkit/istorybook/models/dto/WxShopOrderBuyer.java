package tech.smartkit.istorybook.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WxShopOrderBuyer {
    private String buyer_nickname;//	买家微信昵称
    private String openid;//	买家微信OpenID
    private String buyer_message;//	买家留言

    public WxShopOrderBuyer() {
    }


    public String getBuyer_nickname() {
        return buyer_nickname;
    }

    public void setBuyer_nickname(String buyer_nickname) {
        this.buyer_nickname = buyer_nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    @Override
    public String toString() {
        return "WxShopOrderBuyer{" +
                "buyer_nickname='" + buyer_nickname + '\'' +
                ", openid='" + openid + '\'' +
                ", buyer_message='" + buyer_message + '\'' +
                '}';
    }
}
