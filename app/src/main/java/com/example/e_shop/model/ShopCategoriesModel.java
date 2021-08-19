package com.example.e_shop.model;

public class ShopCategoriesModel {
    private int  cat_icon;
    private String cat_name;

    public ShopCategoriesModel() {
    }

    public ShopCategoriesModel(int cat_icon, String cat_name) {
        this.cat_icon = cat_icon;
        this.cat_name = cat_name;
    }

    public int getCat_icon() {
        return cat_icon;
    }

    public String getCat_name() {
        return cat_name;
    }
}
