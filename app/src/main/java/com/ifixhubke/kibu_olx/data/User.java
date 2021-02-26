package com.ifixhubke.kibu_olx.data;

public class User {

    String e_Mail;
    String f_Name;
    String l_Name;
    String phone_No;

    public User() {
    }

    public User(String e_Mail, String f_Name, String l_Name, String phone_No) {
        this.e_Mail = e_Mail;
        this.f_Name = f_Name;
        this.l_Name = l_Name;
        this.phone_No = phone_No;
    }

    public String getE_Mail() {
        return e_Mail;
    }

    public void setE_Mail(String e_Mail) {
        this.e_Mail = e_Mail;
    }

    public String getF_Name() {
        return f_Name;
    }

    public void setF_Name(String f_Name) {
        this.f_Name = f_Name;
    }

    public String getL_Name() {
        return l_Name;
    }

    public void setL_Name(String l_Name) {
        this.l_Name = l_Name;
    }

    public String getPhone_No() {
        return phone_No;
    }

    public void setPhone_No(String phone_No) {
        this.phone_No = phone_No;
    }
}
