package com.example.e_shop.model;

public class PerCatModel {
    private String perCatImageItemUrl,perCatItemName,perCatItemPrice,perCatItemDescription;

    public PerCatModel() {
    }

    public PerCatModel(String perCatImageItemUrl, String perCatItemName, String perCatItemPrice, String perCatItemDescription) {
        this.perCatImageItemUrl = perCatImageItemUrl;
        this.perCatItemName = perCatItemName;
        this.perCatItemPrice = perCatItemPrice;
        this.perCatItemDescription = perCatItemDescription;
    }

    public String getPerCatImageItemUrl() {
        return perCatImageItemUrl;
    }

    public String getPerCatItemName() {
        return perCatItemName;
    }

    public String getPerCatItemPrice() {
        return perCatItemPrice;
    }

    public String getPerCatItemDescription() {
        return perCatItemDescription;
    }
}
