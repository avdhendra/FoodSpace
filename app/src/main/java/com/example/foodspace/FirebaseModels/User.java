package com.example.foodspace.FirebaseModels;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public String id;

    public String firstname;
    public String email;
    public String image;
    public String BackgroundImage;
    public String mobile;
    public String Description;
    public Integer profileCompleted;

    public User() {
    }

    public User(String id, String Description, String BackgroundImage, String firstname, String email, String image, String mobile, Integer profileCompleted) {
        this.id = id;
        this.firstname = firstname;
        this.email = email;
        this.Description = Description;
        this.BackgroundImage = BackgroundImage;
        this.image = image;
        this.mobile = mobile;
        this.profileCompleted = profileCompleted;
    }


    protected User(Parcel in) {
        id = in.readString();
        firstname = in.readString();
        email = in.readString();
        image = in.readString();
        BackgroundImage = in.readString();
        mobile = in.readString();
        Description = in.readString();
        if (in.readByte() == 0) {
            profileCompleted = null;
        } else {
            profileCompleted = in.readInt();
        }
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(firstname);
        parcel.writeString(email);
        parcel.writeString(image);
        parcel.writeString(BackgroundImage);
        parcel.writeString(mobile);
        parcel.writeString(Description);
        if (profileCompleted == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(profileCompleted);
        }
    }
}
