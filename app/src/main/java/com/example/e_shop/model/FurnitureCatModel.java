package com.example.e_shop.model;

public class FurnitureCatModel {
    private String furnitureImageUrls,furnitureName,furniturePrice,furnitureDescription;

    public FurnitureCatModel() {
    }

    public FurnitureCatModel(String furnitureImageUrls, String furnitureName, String furniturePrice, String furnitureDescription) {
        this.furnitureImageUrls = furnitureImageUrls;
        this.furnitureName = furnitureName;
        this.furniturePrice = furniturePrice;
        this.furnitureDescription = furnitureDescription;
    }

    public String getFurnitureImageUrls() {
        return furnitureImageUrls;
    }

    public String getFurnitureName() {
        return furnitureName;
    }

    public String getFurniturePrice() {
        return furniturePrice;
    }

    public String getFurnitureDescription() {
        return furnitureDescription;
    }
}
