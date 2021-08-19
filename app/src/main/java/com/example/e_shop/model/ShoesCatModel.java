package com.example.e_shop.model;

public class ShoesCatModel {
    private String shoesImageUrls,shoesName,shoesPrice,shoesDescription;

    public ShoesCatModel() {
    }

    public ShoesCatModel(String shoesImageUrls, String shoesName, String shoesPrice, String shoesDescription) {
        this.shoesImageUrls = shoesImageUrls;
        this.shoesName = shoesName;
        this.shoesPrice = shoesPrice;
        this.shoesDescription = shoesDescription;
    }

    public String getShoesImageUrls() {
        return shoesImageUrls;
    }

    public String getShoesName() {
        return shoesName;
    }

    public String getShoesPrice() {
        return shoesPrice;
    }

    public String getShoesDescription() {
        return shoesDescription;
    }
}
