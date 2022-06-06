package Model.Main_Data;

import com.jfoenix.controls.JFXCheckBox;


public class Client {

    int code , envoy_code;

    String name,kind , envoy_name , address, phone;

    Double current_balance, balance_received , balance_remaining;

    JFXCheckBox isSelected_checkBox;


    public Client() {

        this.code = 0;
        this.envoy_code = 0;

        this.name = "";
        this.kind = "";
        this.envoy_name = "";

        this.address = "";
        this.phone = "";

        this.current_balance = 0.0;
        this.balance_received = 0.0;
        this.balance_remaining = 0.0;

        this.isSelected_checkBox = new JFXCheckBox();


    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getEnvoy_code() {
        return envoy_code;
    }

    public void setEnvoy_code(int envoy_code) {
        this.envoy_code = envoy_code;
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

    public String getEnvoy_name() {
        return envoy_name;
    }

    public void setEnvoy_name(String envoy_name) {
        this.envoy_name = envoy_name;
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

    public Double getBalance_received() {
        return balance_received;
    }

    public void setBalance_received(Double balance_received) {
        this.balance_received = balance_received;
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