package com.example.e_shop.model;

public class FashionCatModel {
    private String fashionImageUrls,fashionName,fashionPrice,fashionDescription;

    public FashionCatModel() {
    }

    public FashionCatModel(String fashionImageUrls, String fashionName, String fashionPrice, String fashionDescription) {
        this.fashionImageUrls = fashionImageUrls;
        this.fashionName = fashionName;
        this.fashionPrice = fashionPrice;
        this.fashionDescription = fashionDescription;
    }

    public String getFashionImageUrls() {
        return fashionImageUrls;
    }

    public String getFashionName() {
        return fashionName;
    }

    public String getFashionPrice() {
        return fashionPrice;
    }

    public String getFashionDescription() {
        return fashionDescription;
    }
}
