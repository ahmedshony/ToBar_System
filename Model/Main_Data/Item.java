package Model.Main_Data;

import com.jfoenix.controls.JFXCheckBox;


public class Item {

    int code;

    String name, kind, notes;

    Double current_quantity , quantity_limit , buy_price,farmer_cash_price, trade_cash_price ,
            farmer_Installment_price , trade_Installment_price;

    JFXCheckBox isSelected_checkBox;


    public Item() {

        this.code = 0;


        this.name = "";
        this.kind = "";
        this.notes = "";

        this.current_quantity = 0.0;
        this.quantity_limit = 0.0;
        this.farmer_cash_price = 0.0;
        this.trade_cash_price = 0.0;
        this.farmer_Installment_price = 0.0;
        this.trade_Installment_price = 0.0;
        this.buy_price = 0.00 ;

        this.isSelected_checkBox = new JFXCheckBox();


    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getCurrent_quantity() {
        return current_quantity;
    }

    public void setCurrent_quantity(Double current_quantity) {
        this.current_quantity = current_quantity;
    }

    public Double getQuantity_limit() {
        return quantity_limit;
    }

    public void setQuantity_limit(Double quantity_limit) {
        this.quantity_limit = quantity_limit;
    }

    public Double getFarmer_cash_price() {
        return farmer_cash_price;
    }

    public void setFarmer_cash_price(Double farmer_cash_price) {
        this.farmer_cash_price = farmer_cash_price;
    }


    public Double getTrade_cash_price() {
        return trade_cash_price;
    }

    public void setTrade_cash_price(Double trade_cash_price) {
        this.trade_cash_price = trade_cash_price;
    }

    public Double getFarmer_Installment_price() {
        return farmer_Installment_price;
    }

    public void setFarmer_Installment_price(Double farmer_Installment_price) {
        this.farmer_Installment_price = farmer_Installment_price;
    }


    public Double getTrade_Installment_price() {
        return trade_Installment_price;
    }

    public void setTrade_Installment_price(Double trade_Installment_price) {
        this.trade_Installment_price = trade_Installment_price;
    }


    public Double getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(Double buy_price) {
        this.buy_price = buy_price;
    }

    public JFXCheckBox getIsSelected_checkBox() {
        return isSelected_checkBox;
    }

    public void setIsSelected_checkBox(JFXCheckBox isSelected_checkBox) {
        this.isSelected_checkBox = isSelected_checkBox;
    }
}