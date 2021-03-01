package com.ifixhubke.kibu_olx.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;

public class Sell1 implements Parcelable {

    private String category;
    private String location;
    private String productName;
    private String price;
    private String condition;
    private String phoneNumber;
    private  String datePosted;
    private ArrayList<Uri> imagesList;

    public Sell1(String category, String location, String productName, String price, String condition, String phoneNumber, String datePosted, ArrayList<Uri> imagesList) {
        this.category = category;
        this.location = location;
        this.productName = productName;
        this.price = price;
        this.condition = condition;
        this.phoneNumber = phoneNumber;
        this.datePosted = datePosted;
        this.imagesList = imagesList;
    }


    public Sell1(String category, String location, ArrayList<Uri> imagesList) {
        this.category = category;
        this.location = location;
        this.imagesList = imagesList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public ArrayList<Uri> getImagesList() {
        return imagesList;
    }

    public void setImagesList(ArrayList<Uri> imagesList) {
        this.imagesList = imagesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.location);
        dest.writeString(this.productName);
        dest.writeString(this.price);
        dest.writeString(this.condition);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.datePosted);
        dest.writeTypedList(this.imagesList);
    }

    public void readFromParcel(Parcel source) {
        this.category = source.readString();
        this.location = source.readString();
        this.productName = source.readString();
        this.price = source.readString();
        this.condition = source.readString();
        this.phoneNumber = source.readString();
        this.datePosted = source.readString();
        this.imagesList = source.createTypedArrayList(Uri.CREATOR);
    }

    protected Sell1(Parcel in) {
        this.category = in.readString();
        this.location = in.readString();
        this.productName = in.readString();
        this.price = in.readString();
        this.condition = in.readString();
        this.phoneNumber = in.readString();
        this.datePosted = in.readString();
        this.imagesList = in.createTypedArrayList(Uri.CREATOR);
    }

    public static final Parcelable.Creator<Sell1> CREATOR = new Parcelable.Creator<Sell1>() {
        @Override
        public Sell1 createFromParcel(Parcel source) {
            return new Sell1(source);
        }

        @Override
        public Sell1[] newArray(int size) {
            return new Sell1[size];
        }
    };
}
