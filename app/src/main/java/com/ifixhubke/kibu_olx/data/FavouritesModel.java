package com.ifixhubke.kibu_olx.data;

public class FavouritesModel {
    public String itemImage;
    public String itemName;
    public String itemPrice;

    public FavouritesModel() { }

    public FavouritesModel(String favImage, String favItemName, String favItemPrice) {
        this.itemImage = favImage;
        this.itemName = favItemName;
        this.itemPrice = favItemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
