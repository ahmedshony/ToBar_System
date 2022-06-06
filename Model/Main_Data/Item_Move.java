package Model.Main_Data;

import com.jfoenix.controls.JFXCheckBox;

import java.sql.Date;



public class Item_Move {
    int code, item_code , bill_code;

    String  item_name , kind , item_kind , envoy_name , client_name;

    Double quantity , price , buy_price;

    Date date  , expire_date;

    JFXCheckBox isSelected_checkBox;


    public Item_Move() {

        this.code = 0;
        this.item_code = 0;
        this.bill_code = 0;


        this.item_name = "";
        this.kind = "";
        this.item_kind = "";
        this.envoy_name = "";
        this.client_name = "";

        this.quantity = 0.0;
        this.price = 0.0;
        this.buy_price = 0.0;

        this.date = null;
        this.expire_date = null;


        this.isSelected_checkBox = new JFXCheckBox();


    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }


    public int getBill_code() {
        return bill_code;
    }

    public void setBill_code(int bill_code) {
        this.bill_code = bill_code;
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEnvoy_name() {
        return envoy_name;
    }

    public void setEnvoy_name(String envoy_name) {
        this.envoy_name = envoy_name;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(Double buy_price) {
        this.buy_price = buy_price;
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
