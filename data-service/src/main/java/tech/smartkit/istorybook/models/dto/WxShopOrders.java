package tech.smartkit.istorybook.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WxShopOrders {
    //@see:http://wx764fa42b23cd341f.97866.com/wp-admin/admin.php?page=shop-open&tab=order
    private String start_time;//	否	开始时间戳，默认为一天前
    private String end_time;//	否	结束时间戳，默认为：现在
    private List<WxShopOrder> orders;
    private int errorcode;

    public WxShopOrders(String start_time, String end_time, List<WxShopOrder> orders, int errorcode) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.orders = orders;
        this.errorcode = errorcode;
    }

    public WxShopOrders() {
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

    public List<WxShopOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<WxShopOrder> orders) {
        this.orders = orders;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    @Override
    public String toString() {
        return "WxShopOrders{" +
                "start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", orders=" + orders +
                ", errorcode=" + errorcode +
                '}';
    }
}
