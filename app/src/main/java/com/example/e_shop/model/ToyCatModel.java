package com.example.e_shop.model;

public class ToyCatModel {
    private String toysImageUrls,toysName,toysPrice,toysDescription;

    public ToyCatModel() {
    }

    public ToyCatModel(String toysImageUrls, String toysName, String toysPrice, String toysDescription) {
        this.toysImageUrls = toysImageUrls;
        this.toysName = toysName;
        this.toysPrice = toysPrice;
        this.toysDescription = toysDescription;
    }

    public String getToysImageUrls() {
        return toysImageUrls;
    }

    public String getToysName() {
        return toysName;
    }

    public String getToysPrice() {
        return toysPrice;
    }

    public String getToysDescription() {
        return toysDescription;
    }
}
