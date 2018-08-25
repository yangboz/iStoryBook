package tech.smartkit.istorybook.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WxShopProducts {
    //@see:http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
    private String start_time;//	否	开始时间戳，默认为一天前
    private String end_time;//	否	结束时间戳，默认为：现在
    private List<WxShopProduct> products;
    private int errorcode;

    private String post_type_title;
    private int total;
    private int total_pages;
    private int current_page;
    private float next_cursor;


    public WxShopProducts() {
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

    public List<WxShopProduct> getProducts() {
        return products;
    }

    public void setProducts(List<WxShopProduct> products) {
        this.products = products;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getPost_type_title() {
        return post_type_title;
    }

    public void setPost_type_title(String post_type_title) {
        this.post_type_title = post_type_title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public float getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(float next_cursor) {
        this.next_cursor = next_cursor;
    }

    @Override
    public String toString() {
        return "WxShopProducts{" +
                "start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", products=" + products +
                ", errorcode=" + errorcode +
                ", post_type_title='" + post_type_title + '\'' +
                ", total=" + total +
                ", total_pages=" + total_pages +
                ", current_page=" + current_page +
                ", next_cursor=" + next_cursor +
                '}';
    }
}
