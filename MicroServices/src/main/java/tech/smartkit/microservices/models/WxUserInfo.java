/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.microservices.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

//@see:https://github.com/apelegri/wechat-mini-program-wiki#get-user-information
@Entity
@Table(name = "T_WXACCOUNT_INFO")
public class WxUserInfo extends ModelBase implements Serializable {

    private String avatarUrl;
    private String city;
    private String country;
    private int gender;//sex => 0: unknown ; 1: male ; 2：female
    private String language;
    private String nickName;
    private String province;

    public WxUserInfo(String avatarUrl, String city, String country, int gender, String language, String nickName, String province) {
        this.avatarUrl = avatarUrl;
        this.city = city;
        this.country = country;
        this.gender = gender;
        this.language = language;
        this.nickName = nickName;
        this.province = province;
    }
    public WxUserInfo(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "WxUserInfo{" +
                "avatarUrl='" + avatarUrl + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", gender=" + gender +
                ", language='" + language + '\'' +
                ", nickName='" + nickName + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}
