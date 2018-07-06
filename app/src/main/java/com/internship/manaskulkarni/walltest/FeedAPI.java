package com.internship.manaskulkarni.walltest;

import com.internship.manaskulkarni.walltest.model.PostList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FeedAPI {
    @GET("user/manaskulkarnii/.json")
    Call<PostList> getPostList();
}

//https://www.reddit.com/user/manaskulkarnii/.json