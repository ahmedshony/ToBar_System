package Model.Main_Data;


import com.jfoenix.controls.JFXCheckBox;

import java.sql.Date;

public class Earn_And_Spend_Bill {


    int operation_code , code   ;

    String kind  , name , notes ;

    Double  value , total ;

    Date date ;

    JFXCheckBox isSelected_checkBox;


    public Earn_And_Spend_Bill() {

        this.operation_code = 0;
        this.code = 0;


        this.kind = "";
        this.name = "";
        this.notes = "";

        this.value = 0.0;
        this.total = 0.0 ;

        this.date = null ;

        this.isSelected_checkBox = new JFXCheckBox();

    }

    public int getOperation_code() {
        return operation_code;
    }

    public void setOperation_code(int operation_code) {
        this.operation_code = operation_code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public JFXCheckBox getIsSelected_checkBox() {
        return isSelected_checkBox;
    }

    public void setIsSelected_checkBox(JFXCheckBox isSelected_checkBox) {
        this.isSelected_checkBox = isSelected_checkBox;
    }
}
