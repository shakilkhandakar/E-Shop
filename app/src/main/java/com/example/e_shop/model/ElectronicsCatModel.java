package com.example.e_shop.model;

public class ElectronicsCatModel {
    private String electronicImageUrls,electronicName,electronicPrice,electronicDescription;

    public ElectronicsCatModel() {
    }

    public ElectronicsCatModel(String electronicImageUrls, String electronicName, String electronicPrice,
                               String electronicDescription) {
        this.electronicImageUrls = electronicImageUrls;
        this.electronicName = electronicName;
        this.electronicPrice = electronicPrice;
        this.electronicDescription = electronicDescription;
    }

    public String getElectronicImageUrls() {
        return electronicImageUrls;
    }

    public String getElectronicName() {
        return electronicName;
    }

    public String getElectronicPrice() {
        return electronicPrice;
    }

    public String getElectronicDescription() {
        return electronicDescription;
    }
}
