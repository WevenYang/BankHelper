package com.example.weven.bankapp.Bean;

public class LeftHorizontalBarItemInfo {
    private String leftText;
    private int leftImgResourceId;

    public LeftHorizontalBarItemInfo(String leftText, int leftImgResourceId) {
        this.leftText = leftText;
        this.leftImgResourceId = leftImgResourceId;
    }

    public String getLeftText() {
        return leftText;
    }

    public int getLeftImgResourceId() {
        return leftImgResourceId;
    }
}
