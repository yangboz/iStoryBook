/*
 * Copyright (c) 2018. SMARTKIT.INFO.
 */

package tech.smartkit.istorybook.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WxShopOrder {
    //@see:http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
    private String start_time;//	否	开始时间戳，默认为一天前
    private String end_time;//	否	结束时间戳，默认为：现在
//    private int status;//	否	订单状态，默认不填则为全部
    private String id;//	订单id
    private String order_no;//	订单No.
    private int status;//	订单状态，1 - 待付款 2 - 待发货 3 - 已发货 4 - 交易完成 5 - 申请退款 6 - 退款成功 7 - 主动关闭 8 - 自动关闭 10 - 待成团
    private int type;//	订单类型，0 - 普通订单 1 - 拼团订单 2 - 礼品卡订单
    private int total_fee;//	总价
    private int discount_fee;//	优惠
    private int postage;//	邮费
    private int fixed_fee;//	改价
    private String fixed_reason;//	改价理由
    private int amount;//	合计
//    private String coupon_title;//	使用的优惠券标题
//    private int coupon_discount_fee;//	优惠券优惠的金额
    private WxShopOrderCoupon coupon;

//    private String buyer_nickname;//	买家微信昵称
//    private String openid;//	买家微信OpenID
//    private String buyer_message;//	买家留言
    private WxShopOrderBuyer buyer;
//    private String receiver_name;//	收件人姓名
//    private String receiver_phone;//	收件人电话
//    private String receiver_state;//	收件人省份
//    private String receiver_city;//	收件人城市
//    private String receiver_district;//	收件人区
//    private String receiver_address;//	收件人地址
    private WxShopOrderReceiver receiver;
    private String seller_message;//	卖家备注
//    private String logistic_company;//	快递公司
//    private String logistic_no;//	快递单号
//private String consign_time;//	发货时间
    private WxShopOrderLogistics logistics;
    private String time;//	下单时间
    private String paytime;//	支付时间
//    private String refund_state;//	退款给状态，1 - 没有退款 2 - 部分退款处理中 3 - 部分退款成功 4 - 部分退款失败 5 - 全额退款处理中 6 - 全额退款成功 7 - 全额退款失败
//    private String refund_type;//	申请退款类型，0 - 没有退款 1 - 仅退款 2 - 退款退货
//    private int refund_fee;//	退款费用
//    private String refund_reason;//	退款理由
//    private String refund_images;//	退款图片
//    private String refund_time;//	退款时间
    private WxShopOrderRefund refund;
    private String wx_transaction_id;//	微信流水号
    private String trade_no;//	交易号
    private String vendor;//	渠道
    private Object items;//	订单商品，下面是每个订单商品的信息
    private String title;//	商品标题
    private String post_id;//	商品ID
    private String image_url;//	商品图
    private String product_no;//	商品No
    private String sku_id;//	sku id
    private String sku_no;//	sku no
    private String sku_property_names;//	sku 属性
    private int quantity;//	数量
    private int original_price;//	原价
    private int price;//	价格
    private int cost;//	成本

    public WxShopOrder() {
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public int getDiscount_fee() {
        return discount_fee;
    }

    public void setDiscount_fee(int discount_fee) {
        this.discount_fee = discount_fee;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public int getFixed_fee() {
        return fixed_fee;
    }

    public void setFixed_fee(int fixed_fee) {
        this.fixed_fee = fixed_fee;
    }

    public String getFixed_reason() {
        return fixed_reason;
    }

    public void setFixed_reason(String fixed_reason) {
        this.fixed_reason = fixed_reason;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getWx_transaction_id() {
        return wx_transaction_id;
    }

    public void setWx_transaction_id(String wx_transaction_id) {
        this.wx_transaction_id = wx_transaction_id;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getSku_no() {
        return sku_no;
    }

    public void setSku_no(String sku_no) {
        this.sku_no = sku_no;
    }

    public String getSku_property_names() {
        return sku_property_names;
    }

    public void setSku_property_names(String sku_property_names) {
        this.sku_property_names = sku_property_names;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(int original_price) {
        this.original_price = original_price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public WxShopOrderCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(WxShopOrderCoupon coupon) {
        this.coupon = coupon;
    }

    public WxShopOrderBuyer getBuyer() {
        return buyer;
    }

    public void setBuyer(WxShopOrderBuyer buyer) {
        this.buyer = buyer;
    }

    public WxShopOrderReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(WxShopOrderReceiver receiver) {
        this.receiver = receiver;
    }

    public String getSeller_message() {
        return seller_message;
    }

    public void setSeller_message(String seller_message) {
        this.seller_message = seller_message;
    }

    public WxShopOrderLogistics getLogistics() {
        return logistics;
    }

    public void setLogistics(WxShopOrderLogistics logistics) {
        this.logistics = logistics;
    }

    public WxShopOrderRefund getRefund() {
        return refund;
    }

    public void setRefund(WxShopOrderRefund refund) {
        this.refund = refund;
    }

    @Override
    public String toString() {
        return "WxShopOrder{" +
                "start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", id='" + id + '\'' +
                ", order_no='" + order_no + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", total_fee=" + total_fee +
                ", discount_fee=" + discount_fee +
                ", postage=" + postage +
                ", fixed_fee=" + fixed_fee +
                ", fixed_reason='" + fixed_reason + '\'' +
                ", amount=" + amount +
                ", coupon=" + coupon +
                ", buyer=" + buyer +
                ", receiver=" + receiver +
                ", seller_message='" + seller_message + '\'' +
                ", logistics=" + logistics +
                ", time='" + time + '\'' +
                ", paytime='" + paytime + '\'' +
                ", refund=" + refund +
                ", wx_transaction_id='" + wx_transaction_id + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", vendor='" + vendor + '\'' +
                ", items=" + items +
                ", title='" + title + '\'' +
                ", post_id='" + post_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", product_no='" + product_no + '\'' +
                ", sku_id='" + sku_id + '\'' +
                ", sku_no='" + sku_no + '\'' +
                ", sku_property_names='" + sku_property_names + '\'' +
                ", quantity=" + quantity +
                ", original_price=" + original_price +
                ", price=" + price +
                ", cost=" + cost +
                '}';
    }
}
