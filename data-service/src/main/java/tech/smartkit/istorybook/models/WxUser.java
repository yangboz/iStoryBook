/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

//@see:https://github.com/apelegri/wechat-mini-program-wiki#get-user-information
@Entity
@Table(name = "T_WXUSER")
public class WxUser extends ModelBase implements Serializable {
    public Long getId() {
        return id;
    }

    //Swagger 2 Annotations for Model
//@see: https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID")
    private Long id;

    @Version
    @ApiModelProperty(notes = "The auto-generated version of the product")
    private Integer version;

    @ApiModelProperty(notes = "The avatarUrl for WxUser",required = true,readOnly = true)
    private String avatarUrl;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    private String openid;
    private String city;
    private String country;
    private int gender;//sex => 0: unknown ; 1: male ; 2：female
    private String language;
    private String nickName;
    private String province;

    public WxUser(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public WxUser() {
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
        return "WxUser{" +
                "id=" + id +
                ", version=" + version +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", openid='" + openid + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", gender=" + gender +
                ", language='" + language + '\'' +
                ", nickName='" + nickName + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}
