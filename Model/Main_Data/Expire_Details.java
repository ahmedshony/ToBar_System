package Model.Main_Data;

import com.jfoenix.controls.JFXCheckBox;

import java.sql.Date;

public class Expire_Details {
    int code, item_code ;

    String  item_name  , item_kind ;

    Double quantity ;

    Date  expire_date;

    public Expire_Details() {

        this.code = 0;
        this.item_code = 0;

        this.item_name = "";
        this.item_kind = "";

        this.quantity = 0.0;

        this.expire_date = null;


    }

    public int getCode() {
        return code ;
    }

    public void setCode(int code) {
        this.code = code ;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(Date expire_date) {
        this.expire_date = expire_date;
    }
}
