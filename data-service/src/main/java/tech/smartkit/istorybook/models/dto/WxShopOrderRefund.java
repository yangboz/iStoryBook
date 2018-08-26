package tech.smartkit.istorybook.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WxShopOrderRefund {
    private String refund_state;//	退款给状态，1 - 没有退款 2 - 部分退款处理中 3 - 部分退款成功 4 - 部分退款失败 5 - 全额退款处理中 6 - 全额退款成功 7 - 全额退款失败
    private String refund_type;//	申请退款类型，0 - 没有退款 1 - 仅退款 2 - 退款退货
    private int refund_fee;//	退款费用
    private String refund_reason;//	退款理由
    private String refund_images;//	退款图片
    private String refund_time;//	退款时间

    public WxShopOrderRefund() {
    }


    public String getRefund_state() {
        return refund_state;
    }

    public void setRefund_state(String refund_state) {
        this.refund_state = refund_state;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(String refund_type) {
        this.refund_type = refund_type;
    }

    public int getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(int refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public String getRefund_images() {
        return refund_images;
    }

    public void setRefund_images(String refund_images) {
        this.refund_images = refund_images;
    }

    public String getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(String refund_time) {
        this.refund_time = refund_time;
    }

    @Override
    public String toString() {
        return "WxShopOrderRefund{" +
                "refund_state='" + refund_state + '\'' +
                ", refund_type='" + refund_type + '\'' +
                ", refund_fee=" + refund_fee +
                ", refund_reason='" + refund_reason + '\'' +
                ", refund_images='" + refund_images + '\'' +
                ", refund_time='" + refund_time + '\'' +
                '}';
    }
}
