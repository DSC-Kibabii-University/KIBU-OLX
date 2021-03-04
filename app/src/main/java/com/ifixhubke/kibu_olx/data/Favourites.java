package com.ifixhubke.kibu_olx.data;

public class Favourites {
    public String itemImage;
    public String itemName;
    public String itemPrice;

    public Favourites() {
    }

    public Favourites(String favImage, String favItemName, String favItemPrice) {
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
