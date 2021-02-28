package com.ifixhubke.kibu_olx.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.widget.AppCompatSpinner;

public class Sell1 implements Parcelable {

    private String category;
    private String location;
    private Uri image1;
    private Uri image2;
    private Uri image3;
    private String productName;
    private String price;
    private String condition;
    private String phoneNumber;
    private  String datePosted;

    public Sell1(String category, String location, Uri image1, Uri image2, Uri image3) {
        this.category = category;
        this.location = location;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Sell1(String category, String location, Uri image1, Uri image2, Uri image3, String productName, String price, String condition, String phoneNumber, String datePosted) {
        this.category = category;
        this.location = location;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.productName = productName;
        this.price = price;
        this.condition = condition;
        this.phoneNumber = phoneNumber;
        this.datePosted = datePosted;
    }

    public Sell1(AppCompatSpinner categorySpinner, AppCompatSpinner locationSpinner, String downloadURL1, String downloadURL2, String downloadURL3, String date){}

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

    public Uri getImage1() {
        return image1;
    }

    public void setImage1(Uri image1) {
        this.image1 = image1;
    }

    public Uri getImage2() {
        return image2;
    }

    public void setImage2(Uri image2) {
        this.image2 = image2;
    }

    public Uri getImage3() {
        return image3;
    }

    public void setImage3(Uri image3) {
        this.image3 = image3;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeString(this.location);
        dest.writeParcelable(this.image1, flags);
        dest.writeParcelable(this.image2, flags);
        dest.writeParcelable(this.image3, flags);
        dest.writeString(this.productName);
        dest.writeString(this.price);
        dest.writeString(this.condition);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.datePosted);
    }

    public void readFromParcel(Parcel source) {
        this.category = source.readString();
        this.location = source.readString();
        this.image1 = source.readParcelable(Uri.class.getClassLoader());
        this.image2 = source.readParcelable(Uri.class.getClassLoader());
        this.image3 = source.readParcelable(Uri.class.getClassLoader());
        this.productName = source.readString();
        this.price = source.readString();
        this.condition = source.readString();
        this.phoneNumber = source.readString();
        this.datePosted = source.readString();
    }

    protected Sell1(Parcel in) {
        this.category = in.readString();
        this.location = in.readString();
        this.image1 = in.readParcelable(Uri.class.getClassLoader());
        this.image2 = in.readParcelable(Uri.class.getClassLoader());
        this.image3 = in.readParcelable(Uri.class.getClassLoader());
        this.productName = in.readString();
        this.price = in.readString();
        this.condition = in.readString();
        this.phoneNumber = in.readString();
        this.datePosted = in.readString();
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
