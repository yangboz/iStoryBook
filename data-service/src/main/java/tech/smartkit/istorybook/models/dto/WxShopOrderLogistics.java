package tech.smartkit.istorybook.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class WxShopOrderLogistics {
    private String logistic_company;//	快递公司
    private String logistic_no;//	快递单号
    private String consign_time;//	发货时间

    public WxShopOrderLogistics() {
    }

    public String getLogistic_company() {
        return logistic_company;
    }

    public void setLogistic_company(String logistic_company) {
        this.logistic_company = logistic_company;
    }

    public String getLogistic_no() {
        return logistic_no;
    }

    public void setLogistic_no(String logistic_no) {
        this.logistic_no = logistic_no;
    }

    public String getConsign_time() {
        return consign_time;
    }

    public void setConsign_time(String consign_time) {
        this.consign_time = consign_time;
    }

    @Override
    public String toString() {
        return "WxShopOrderLogistics{" +
                "logistic_company='" + logistic_company + '\'' +
                ", logistic_no='" + logistic_no + '\'' +
                ", consign_time='" + consign_time + '\'' +
                '}';
    }
}
