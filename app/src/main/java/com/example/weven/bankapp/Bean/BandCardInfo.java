package com.example.weven.bankapp.Bean;

public class BandCardInfo {
    String id;
    int icon;
    String bank;
    String num;

    public BandCardInfo(String id, int icon, String bank, String num) {
        this.id = id;
        this.icon = icon;
        this.bank = bank;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
