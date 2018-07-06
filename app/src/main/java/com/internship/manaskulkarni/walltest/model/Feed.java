package com.internship.manaskulkarni.walltest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.internship.manaskulkarni.walltest.model.entry.Entry;

import java.util.List;


public class Feed {

    @SerializedName("entry")
    @Expose
    private List<Entry> entries;

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
