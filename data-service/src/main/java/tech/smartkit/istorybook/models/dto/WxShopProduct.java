package tech.smartkit.istorybook.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//@see: http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=product
@JsonIgnoreProperties(ignoreUnknown=true)
public class WxShopProduct {
//
    private String id;//	商品ID
    private String timestamp;//	时间戳
    private String status;//	状态，publish:已上架 unpublished:已下架 sold_out:售罄 draft:草稿 pending:待审
    private String title;//	商品标题
    private String content;//	商品详情
    private String excerpt;//	商品摘要
//    private String product_category;//	商品分类信息
//    private String images;//	商品图片
    private String video;//	视频地址
    private String groupon_enable;//	是否开启拼团，1 为开启，0 为关闭
    private String groupon_price;//	拼团价
    private String groupon_member_limit;//	拼团人数，即几人成团
    private String groupon_time_limit;//	有效时间
    private String miaosha_enable;//	是否开启限时购，1 为开启，0 为关闭
    private String miaosha_price;//	限时购价格
    private String miaosha_start_time;//	限时购开始时间
    private String miaosha_end_time;//	限时购结束时间
//    private String skus;//	sku详细信息
    //skus结构说明

    //参数	说明
    private String product_no;//	全局商品NO
    private String original_price;//	原价
    private String price;//	价格
    private String discount;//	折扣，根据 价格/原价 算出
    private String postage;//	邮费
    private String stock;//	库存
    private String sales;//	销量
    private String manual_sales;//	增加销量
    private String properties;//	sku规格数组，最多三层结构。
    private String records;//	sku记录数组。

    public WxShopProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

//    public String getProduct_category() {
//        return product_category;
//    }
//
//    public void setProduct_category(String product_category) {
//        this.product_category = product_category;
//    }

//    public String getImages() {
//        return images;
//    }
//
//    public void setImages(String images) {
//        this.images = images;
//    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getGroupon_enable() {
        return groupon_enable;
    }

    public void setGroupon_enable(String groupon_enable) {
        this.groupon_enable = groupon_enable;
    }

    public String getGroupon_price() {
        return groupon_price;
    }

    public void setGroupon_price(String groupon_price) {
        this.groupon_price = groupon_price;
    }

    public String getGroupon_member_limit() {
        return groupon_member_limit;
    }

    public void setGroupon_member_limit(String groupon_member_limit) {
        this.groupon_member_limit = groupon_member_limit;
    }

    public String getGroupon_time_limit() {
        return groupon_time_limit;
    }

    public void setGroupon_time_limit(String groupon_time_limit) {
        this.groupon_time_limit = groupon_time_limit;
    }

    public String getMiaosha_enable() {
        return miaosha_enable;
    }

    public void setMiaosha_enable(String miaosha_enable) {
        this.miaosha_enable = miaosha_enable;
    }

    public String getMiaosha_price() {
        return miaosha_price;
    }

    public void setMiaosha_price(String miaosha_price) {
        this.miaosha_price = miaosha_price;
    }

    public String getMiaosha_start_time() {
        return miaosha_start_time;
    }

    public void setMiaosha_start_time(String miaosha_start_time) {
        this.miaosha_start_time = miaosha_start_time;
    }

    public String getMiaosha_end_time() {
        return miaosha_end_time;
    }

    public void setMiaosha_end_time(String miaosha_end_time) {
        this.miaosha_end_time = miaosha_end_time;
    }

//    public String getSkus() {
//        return skus;
//    }
//
//    public void setSkus(String skus) {
//        this.skus = skus;
//    }

    public String getProduct_no() {
        return product_no;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getManual_sales() {
        return manual_sales;
    }

    public void setManual_sales(String manual_sales) {
        this.manual_sales = manual_sales;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "WxShopProduct{" +
                "id='" + id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", excerpt='" + excerpt + '\'' +
//                ", product_category='" + product_category + '\'' +
//                ", images='" + images + '\'' +
                ", video='" + video + '\'' +
                ", groupon_enable='" + groupon_enable + '\'' +
                ", groupon_price='" + groupon_price + '\'' +
                ", groupon_member_limit='" + groupon_member_limit + '\'' +
                ", groupon_time_limit='" + groupon_time_limit + '\'' +
                ", miaosha_enable='" + miaosha_enable + '\'' +
                ", miaosha_price='" + miaosha_price + '\'' +
                ", miaosha_start_time='" + miaosha_start_time + '\'' +
                ", miaosha_end_time='" + miaosha_end_time + '\'' +
//                ", skus='" + skus + '\'' +
                ", product_no='" + product_no + '\'' +
                ", original_price='" + original_price + '\'' +
                ", price='" + price + '\'' +
                ", discount='" + discount + '\'' +
                ", postage='" + postage + '\'' +
                ", stock='" + stock + '\'' +
                ", sales='" + sales + '\'' +
                ", manual_sales='" + manual_sales + '\'' +
                ", properties='" + properties + '\'' +
                ", records='" + records + '\'' +
                '}';
    }
}
