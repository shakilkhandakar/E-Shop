package com.example.e_shop.model;

public class SportsCatModel {
    private String sportsImageUrls,sportsName,sportsPrice,sportsDescription;

    public SportsCatModel() {
    }

    public SportsCatModel(String sportsImageUrls, String sportsName, String sportsPrice, String sportsDescription) {
        this.sportsImageUrls = sportsImageUrls;
        this.sportsName = sportsName;
        this.sportsPrice = sportsPrice;
        this.sportsDescription = sportsDescription;
    }

    public String getSportsImageUrls() {
        return sportsImageUrls;
    }

    public String getSportsName() {
        return sportsName;
    }

    public String getSportsPrice() {
        return sportsPrice;
    }

    public String getSportsDescription() {
        return sportsDescription;
    }
}
