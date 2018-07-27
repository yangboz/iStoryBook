/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.models.dto;

public class WxShopToken {

//    errcode=0, access_token=Lou5tgO6DhXZjWzKv6on5hGwOTBoxnc3LOHGoJveE5vkbmLOfoScJVH9tlt84ukl, expires_in=7200
    private String access_token = "";
    private int errcode=0;
    private int expires_in=7200;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public WxShopToken() {
    }

    @Override
    public String toString() {
        return "WxShopToken{" +
                "access_token='" + access_token + '\'' +
                ", errcode=" + errcode +
                ", expires_in=" + expires_in +
                '}';
    }
}
