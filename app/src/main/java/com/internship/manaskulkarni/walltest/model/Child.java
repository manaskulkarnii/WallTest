package com.internship.manaskulkarni.walltest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Child implements Serializable {
    private final static long serialVersionUID = 5004141750623428469L;
    /*
        @SerializedName("kind")
        @Expose
        private String kind;*/
    @SerializedName("data")
    @Expose
    private Data_ data;

    public Data_ getData() {
        return data;
    }

    public void setData(Data_ data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Child{" +
                "data=" + data +
                '}';
    }
}
