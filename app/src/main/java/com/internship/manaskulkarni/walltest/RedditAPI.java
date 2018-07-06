package com.internship.manaskulkarni.walltest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedditAPI {

    private static final String BASE_URL = "https://www.reddit.com/";
    public static FeedAPI feedAPI = null;

    public static FeedAPI getRedditService() {

        if (feedAPI == null) {
/*
                    Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            feedAPI = retrofit.create(FeedAPI.class);
        }
        return feedAPI;
    }

}
