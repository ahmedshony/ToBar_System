package Model.Main_Data;

import com.jfoenix.controls.JFXCheckBox;


public class Company {

    int code;

    String name, address, phone;

    Double current_balance, balance_paid , balance_remaining;

    JFXCheckBox isSelected_checkBox;


    public Company() {

        this.code = 0;

        this.name = "";
        this.address = "";
        this.phone = "";

        this.current_balance = 0.0;
        this.balance_paid = 0.0;
        this.balance_remaining = 0.0;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Double getBalance_paid() {
        return balance_paid;
    }

    public void setBalance_paid(Double balance_paid) {
        this.balance_paid = balance_paid;
    }

    public Double getBalance_remaining() {
        return balance_remaining;
    }

    public void setBalance_remaining(Double balance_remaining) {
        this.balance_remaining = balance_remaining;
    }

    public JFXCheckBox getIsSelected_checkBox() {
        return isSelected_checkBox;
    }

    public void setIsSelected_checkBox(JFXCheckBox isSelected_checkBox) {
        this.isSelected_checkBox = isSelected_checkBox;
    }
}