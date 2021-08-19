package com.example.e_shop.model;

public class BookCatModel {
    private String bookImageUrls,bookName,bookPrice,bookDescription;

    public BookCatModel() {
    }

    public BookCatModel(String bookImageUrls, String bookName, String bookPrice, String bookDescription) {
        this.bookImageUrls = bookImageUrls;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookDescription = bookDescription;
    }

    public String getBookImageUrls() {
        return bookImageUrls;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public String getBookDescription() {
        return bookDescription;
    }
}
