package com.example.e_shop.model;

public class WishListModel {
    private String wishImageUrl,wishProductName,wishProductPrice,wishDescription;

    public WishListModel() {
    }

    public WishListModel(String wishImageUrl, String wishProductName, String wishProductPrice,String wishDescription) {
        this.wishImageUrl = wishImageUrl;
        this.wishProductName = wishProductName;
        this.wishProductPrice = wishProductPrice;
        this.wishDescription = wishDescription;
    }

    public String getWishImageUrl() {
        return wishImageUrl;
    }

    public String getWishProductName() {
        return wishProductName;
    }

    public String getWishProductPrice() {
        return wishProductPrice;
    }

    public String getWishDescription() {
        return wishDescription;
    }
}
