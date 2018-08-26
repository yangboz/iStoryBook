package tech.smartkit.istorybook.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WxShopOrderCoupon {
    private String coupon_title;//	使用的优惠券标题
    private int coupon_discount_fee;//	优惠券优惠的金额

    public WxShopOrderCoupon() {
    }

    public String getCoupon_title() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public int getCoupon_discount_fee() {
        return coupon_discount_fee;
    }

    public void setCoupon_discount_fee(int coupon_discount_fee) {
        this.coupon_discount_fee = coupon_discount_fee;
    }

    @Override
    public String toString() {
        return "WxShopOrderCoupon{" +
                "coupon_title='" + coupon_title + '\'' +
                ", coupon_discount_fee=" + coupon_discount_fee +
                '}';
    }
}
