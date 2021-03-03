package com.ifixhubke.kibu_olx.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    Boolean itemStarred;
    private String sellName;
    private String sellerLastSeen;
    private String sellerPhoneNum;
    private String itemImage;
    private String imageImage2;
    private String itemImage3;
    private String itemName;
    private String itemPrice;
    private String datePosted;
    private String location;
    private String itemDescription;

    public Item() {
    }


    public Item(String itemImage, String itemName, String itemPrice, Boolean itemStarred) {
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStarred = itemStarred;
    }

    public Item(String sellName, String sellerLastSeen, String sellerPhoneNum, String itemImage, String imageImage2, String itemImage3, String itemName, String itemPrice, String datePosted, String location, String itemDescription) {
        this.sellName = sellName;
        this.sellerLastSeen = sellerLastSeen;
        this.sellerPhoneNum = sellerPhoneNum;
        this.itemImage = itemImage;
        this.imageImage2 = imageImage2;
        this.itemImage3 = itemImage3;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.datePosted = datePosted;
        this.location = location;
        this.itemDescription = itemDescription;
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
        dest.writeValue(this.itemStarred);
        dest.writeString(this.sellName);
        dest.writeString(this.sellerLastSeen);
        dest.writeString(this.sellerPhoneNum);
        dest.writeString(this.itemImage);
        dest.writeString(this.imageImage2);
        dest.writeString(this.itemImage3);
        dest.writeString(this.itemName);
        dest.writeString(this.itemPrice);
        dest.writeString(this.datePosted);
        dest.writeString(this.location);
        dest.writeString(this.itemDescription);
    }

    public void readFromParcel(Parcel source) {
        this.itemStarred = (Boolean) source.readValue(Boolean.class.getClassLoader());
        this.sellName = source.readString();
        this.sellerLastSeen = source.readString();
        this.sellerPhoneNum = source.readString();
        this.itemImage = source.readString();
        this.imageImage2 = source.readString();
        this.itemImage3 = source.readString();
        this.itemName = source.readString();
        this.itemPrice = source.readString();
        this.datePosted = source.readString();
        this.location = source.readString();
        this.itemDescription = source.readString();
    }

    protected Item(Parcel in) {
        this.itemStarred = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.sellName = in.readString();
        this.sellerLastSeen = in.readString();
        this.sellerPhoneNum = in.readString();
        this.itemImage = in.readString();
        this.imageImage2 = in.readString();
        this.itemImage3 = in.readString();
        this.itemName = in.readString();
        this.itemPrice = in.readString();
        this.datePosted = in.readString();
        this.location = in.readString();
        this.itemDescription = in.readString();
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
