package com.ifixhubke.kibu_olx.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    private String itemImage;
    private String itemName;
    private String itemPrice;
    Boolean itemStarred;

    public Item() {
    }

    public Item(String itemImage, String itemName, String itemPrice, Boolean itemStarred) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStarred = itemStarred;
    }

    public Boolean getItemStarred(){
        return itemStarred;
    }

    public void setItemStarred(Boolean itemStarred){
        this.itemStarred = itemStarred;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemImage);
        dest.writeString(this.itemName);
        dest.writeString(this.itemPrice);
    }

    public void readFromParcel(Parcel source) {
        this.itemImage = source.readString();
        this.itemName = source.readString();
        this.itemPrice = source.readString();
    }

    protected Item(Parcel in) {
        this.itemImage = in.readString();
        this.itemName = in.readString();
        this.itemPrice = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
