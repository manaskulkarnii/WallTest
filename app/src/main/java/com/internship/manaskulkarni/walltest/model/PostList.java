package com.internship.manaskulkarni.walltest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostList implements Serializable {
    private final static long serialVersionUID = 9019451479399262195L;
    /*
        @SerializedName("kind")
        @Expose
        private String kind;*/
    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PostList\n" +
                "{data =" + data +
                '}';
    }
}
