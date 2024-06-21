package org.example.java_final_project.Model;

import java.math.BigDecimal;

public class Private_User {
    private String sdt;
    private BigDecimal balance ;
    private String Pin ;

    public Private_User(String sdt, BigDecimal balance, String pin) {
        this.sdt = sdt;
        this.balance = balance;
        Pin = pin;
    }

    public Private_User(String sdt, String pin) {
        this.sdt = sdt;
        Pin = pin;
    }

    public Private_User(String sdt) {
        this.sdt = sdt;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPin() {
        return Pin;
    }

    public void setPin(String pin) {
        Pin = pin;
    }
}
