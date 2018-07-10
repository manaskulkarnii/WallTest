package com.internship.manaskulkarni.walltest;

import android.os.Parcel;
import android.os.Parcelable;

public class Wallpaper implements Parcelable {

    private String id;
    private String category;
    private String title;
    private String thumbnail;
    private String url;

    public Wallpaper(String id, String category, String title, String thumbnail, String url) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.thumbnail = thumbnail;
        this.url = url;
    }

    protected Wallpaper(Parcel in) {
        id = in.readString();
        category = in.readString();
        title = in.readString();
        thumbnail = in.readString();
        url = in.readString();
    }

    public static final Creator<Wallpaper> CREATOR = new Creator<Wallpaper>() {
        @Override
        public Wallpaper createFromParcel(Parcel in) {
            return new Wallpaper(in);
        }

        @Override
        public Wallpaper[] newArray(int size) {
            return new Wallpaper[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(category);
        parcel.writeString(title);
        parcel.writeString(thumbnail);
        parcel.writeString(url);
    }
}
