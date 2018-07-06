package com.internship.manaskulkarni.walltest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    /*@SerializedName("after")
    @Expose
    private Object after;
    @SerializedName("before")
    @Expose
    private Object before; */
    private final static long serialVersionUID = -6538922778585403958L;
    /*
        @SerializedName("modhash")
        @Expose
        private String modhash;
        @SerializedName("dist")
        @Expose
        private Integer dist;*/
    @SerializedName("children")
    @Expose
    private List<Child> children = null;

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Data{" +
                "children= \n" + children +
                '}';
    }
}
