package Model.Main_Data;

import com.jfoenix.controls.JFXCheckBox;


public class Envoy {

    int code;

    String name, phone;

    Double current_balance;

    JFXCheckBox isSelected_checkBox;


    public Envoy() {

        this.code = 0;

        this.name = "";
        this.phone = "";

        this.current_balance = 0.0;


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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(Double current_balance) {
        this.current_balance = current_balance;
    }

    public JFXCheckBox getIsSelected_checkBox() {
        return isSelected_checkBox;
    }

    public void setIsSelected_checkBox(JFXCheckBox isSelected_checkBox) {
        this.isSelected_checkBox = isSelected_checkBox;
    }
}