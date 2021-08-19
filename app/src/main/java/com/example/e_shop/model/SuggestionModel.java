package com.example.e_shop.model;

public class SuggestionModel {
    private String suggestionImageItemUrl,suggestionItemName,suggestionItemPrice,suggestionItemDescription;

    public SuggestionModel() {
    }

    public SuggestionModel(String suggestionImageItemUrl, String suggestionItemName, String suggestionItemPrice, String suggestionItemDescription) {
        this.suggestionImageItemUrl = suggestionImageItemUrl;
        this.suggestionItemName = suggestionItemName;
        this.suggestionItemPrice = suggestionItemPrice;
        this.suggestionItemDescription = suggestionItemDescription;
    }

    public String getSuggestionImageItemUrl() {
        return suggestionImageItemUrl;
    }

    public String getSuggestionItemName() {
        return suggestionItemName;
    }

    public String getSuggestionItemPrice() {
        return suggestionItemPrice;
    }

    public String getSuggestionItemDescription() {
        return suggestionItemDescription;
    }
}
