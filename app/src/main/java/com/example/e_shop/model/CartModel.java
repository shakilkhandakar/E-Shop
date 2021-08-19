package com.example.e_shop.model;

public class CartModel {
    private String cartImageUrl,cartProductName,cartProductPrice;
    int orderCount;


    public CartModel() {

    }

    public CartModel(String cartImageUrl, String cartProductName, String cartProductPrice, int orderCount) {
        this.cartImageUrl = cartImageUrl;
        this.cartProductName = cartProductName;
        this.cartProductPrice = cartProductPrice;
        this.orderCount = orderCount;
    }

    public String getCartImageUrl() {
        return cartImageUrl;
    }

    public String getCartProductName() {
        return cartProductName;
    }

    public String getCartProductPrice() {
        return cartProductPrice;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
