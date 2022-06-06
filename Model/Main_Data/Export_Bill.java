package Model.Main_Data;


import com.jfoenix.controls.JFXCheckBox;

import java.sql.Date;

public class Export_Bill {


    int bill_item_code , code , client_code , envoy_code , index , item_code  ;

    String bill_kind ,pay_kind  , client_name , envoy_name , item_name , item_kind , bill_receipt_number;

    Double item_count , item_buy_price , item_price , item_total_price , bill_total_price ,
    bill_total_discount , bill_total_received , bill_final_total;

    Date date , expire_date ;

    JFXCheckBox isSelected_checkBox;

    public Export_Bill() {

        this.bill_item_code = 0;
        this.code = 0;
        this.client_code = 0;
        this.envoy_code = 0;
        this.index = 0;
        this.item_code = 0;

        this.pay_kind = "";
        this.client_name = "";
        this.envoy_name = "";
        this.item_name = "";
        this.item_kind = "";
        this.bill_receipt_number = "";

        this.item_count = 0.0;
        this.item_buy_price = 0.0;
        this.item_price = 0.0;
        this.item_total_price = 0.0 ;
        this.bill_total_price = 0.0 ;
        this.bill_total_discount = 0.0 ;
        this.bill_total_received = 0.0  ;
        this.bill_final_total = 0.0 ;

        this.date = null ;
        this.expire_date = null ;

        this.isSelected_checkBox = new JFXCheckBox();

    }

    public int getBill_item_code() {
        return bill_item_code;
    }

    public void setBill_item_code(int bill_item_code) {
        this.bill_item_code = bill_item_code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getClient_code() {
        return client_code;
    }

    public void setClient_code(int client_code) {
        this.client_code = client_code;
    }

    public int getEnvoy_code() {
        return envoy_code;
    }

    public void setEnvoy_code(int envoy_code) {
        this.envoy_code = envoy_code;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }

    public String getPay_kind() {
        return pay_kind;
    }

    public String getBill_kind() {
        return bill_kind;
    }

    public void setBill_kind(String bill_kind) {
        this.bill_kind = bill_kind;
    }

    public void setPay_kind(String pay_kind) {
        this.pay_kind = pay_kind;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getEnvoy_name() {
        return envoy_name;
    }

    public void setEnvoy_name(String envoy_name) {
        this.envoy_name = envoy_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_kind() {
        return item_kind;
    }

    public void setItem_kind(String item_kind) {
        this.item_kind = item_kind;
    }

    public String getBill_receipt_number() {
        return bill_receipt_number;
    }

    public void setBill_receipt_number(String bill_receipt_number) {
        this.bill_receipt_number = bill_receipt_number;
    }

    public Double getItem_count() {
        return item_count;
    }

    public void setItem_count(Double item_count) {
        this.item_count = item_count;
    }

    public Double getItem_buy_price() {
        return item_buy_price;
    }

    public void setItem_buy_price(Double item_buy_price) {
        this.item_buy_price = item_buy_price;
    }

    public Double getItem_price() {
        return item_price;
    }

    public void setItem_price(Double item_price) {
        this.item_price = item_price;
    }

    public Double getItem_total_price() {
        return item_total_price;
    }

    public void setItem_total_price(Double item_total_price) {
        this.item_total_price = item_total_price;
    }

    public Double getBill_total_price() {
        return bill_total_price;
    }

    public void setBill_total_price(Double bill_total_price) {
        this.bill_total_price = bill_total_price;
    }

    public Double getBill_total_discount() {
        return bill_total_discount;
    }

    public void setBill_total_discount(Double bill_total_discount) {
        this.bill_total_discount = bill_total_discount;
    }

    public Double getBill_total_received() {
        return bill_total_received;
    }

    public void setBill_total_received(Double bill_total_received) {
        this.bill_total_received = bill_total_received;
    }

    public Double getBill_final_total() {
        return bill_final_total;
    }

    public void setBill_final_total(Double bill_final_total) {
        this.bill_final_total = bill_final_total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Date expire_date) {
        this.expire_date = expire_date;
    }

    public JFXCheckBox getIsSelected_checkBox() {
        return isSelected_checkBox;
    }

    public void setIsSelected_checkBox(JFXCheckBox isSelected_checkBox) {
        this.isSelected_checkBox = isSelected_checkBox;
    }
}
