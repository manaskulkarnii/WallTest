package com.internship.manaskulkarni.walltest;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.internship.manaskulkarni.walltest.model.Child;
import com.internship.manaskulkarni.walltest.model.PostList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.manaskulkarni.walltest.SetWallpaper.getContext;


public class WallpaperUtils {

    private static final String TAG = "WallpaperUtils";
    private static WallpaperManager wallpaperManager;
    // private static Bitmap bitmap;
    private static Random random = new Random();
    private static int position = 3;
    //private int position = 2;

    static void autoChangeWallpaper() {

        //(int position, String wallpaper, Context context)
        /*wallpaperManager = WallpaperManager.getInstance(context);
        try {
            URL url = new URL(wallpaper);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            wallpaperManager.setBitmap(bitmap);
        } catch(IOException e) {
            System.out.println(e);
        }*/


        final Call<PostList> jsonData = RedditAPI.getRedditService().getPostList();

        jsonData.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                //Log.d(TAG, "onResponse: feed: SUCCESS: " + response.body().toString());
                Log.d(TAG, "onRespone: Server Response: " + response.toString());

                assert response.body() != null;
                List<Child> children = response.body().getData().getChildren();
                final ArrayList<Wallpaper> wallpapers = new ArrayList<Wallpaper>();
                int titleIndex = children.get(0).getData().getTitle().indexOf("/");
                String title = children.get(0).getData().getTitle().substring(titleIndex + 1);
                String category = children.get(0).getData().getTitle().substring(0, titleIndex);

                for (int i = 0; i < children.size(); i++) {
                    wallpapers.add(new Wallpaper(
                            children.get(i).getData().getId(),
                            children.get(i).getData().getTitle(),
                            children.get(i).getData().getTitle(),
                            children.get(i).getData().getThumbnail(),
                            children.get(i).getData().getUrl()
                    ));
                }
/*
                for (int j = 0; j < children.size(); j++) {
                    Log.d(TAG, "onResponse: \n" +
                            "Wallpaper ID: " + children.get(j).getData().getId() + "\n" +
                            "Category:" + category + "\n" +
                            "Title:" + title + "\n" +
                            "Wallpaper Title: " + children.get(j).getData().getTitle() + "\n" +
                            "Wallpaper Thumbnail: " + children.get(j).getData().getThumbnail() + "\n" +
                            "Wallpaper URL: " + children.get(j).getData().getUrl() + "\n");
                }*/

                //WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
                //Bitmap bitmap;
                position = random.nextInt(4);
                Log.d(TAG, "Random Postion: " + position);
                String temp = wallpapers.get(position).getUrl();

                wallpaperManager = WallpaperManager.getInstance(getContext());

                if (position != (wallpapers.size() - 1)) {
                    // String urlString = wallpapers.get(position + 1).getUrl();
/*                    try {
                        //URL url = new URL(temp);
                        //bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        bitmap = getBitmapFromURL(temp);
                        wallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                        System.out.println(e);
                    }*/
/*                    FutureTarget<Bitmap> futureTarget =
                            Glide.with(getContext())
                                    .asBitmap()
                                    .load(temp)
                                    .submit();

                    try {
                        bitmap = futureTarget.get();
                        wallpaperManager.setBitmap(bitmap);
                    } catch (InterruptedException | ExecutionException | IOException e) {
                        e.printStackTrace();
                    }*/
                    Glide.with(getContext())
                            .asBitmap()
                            .load(temp)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    try {
                                        wallpaperManager.setBitmap(resource);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                }/* else {
                   // String urlString = wallpapers.get(0).getUrl();
                    try {
*//*                        URL url = new URL(temp);
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());*//*
                        bitmap = getBitmapFromURL(temp);
                        wallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }*/
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {

                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(getContext(), "An error occured!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Bitmap getBitmapFromURL(String src) {
/*        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/
/*        FutureTarget<Bitmap> futureTarget =
                Glide.with(getContext())
                        .asBitmap()
                        .load(src)
                        .submit();

        try {
            return futureTarget.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }*/
        return null;
    }
}
