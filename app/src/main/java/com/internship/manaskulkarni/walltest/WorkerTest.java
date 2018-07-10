package com.internship.manaskulkarni.walltest;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.internship.manaskulkarni.walltest.model.Child;
import com.internship.manaskulkarni.walltest.model.PostList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.work.Worker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerTest extends Worker {


    private static final String TAG = "WorkerTest";
    private SetWallpaper setWallpaper;
    private int position = 2;

    @NonNull
    @Override
    public Result doWork() {
/*        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {*/

        final Call<PostList> jsonData = RedditAPI.getRedditService().getPostList();

        jsonData.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                Log.d(TAG, "onResponse: feed: SUCCESS: " + response.body().toString());
                Log.d(TAG, "onRespone: Server Response: " + response.toString());

                assert response.body() != null;
                List<Child> children = response.body().getData().getChildren();
                ArrayList<Wallpaper> wallpapers = new ArrayList<Wallpaper>();
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

                for (int j = 0; j < children.size(); j++) {
                    Log.d(TAG, "onResponse: \n" +
                            "Wallpaper ID: " + children.get(j).getData().getId() + "\n" +
                            "Category:" + category + "\n" +
                            "Title:" + title + "\n" +
                            "Wallpaper Title: " + children.get(j).getData().getTitle() + "\n" +
                            "Wallpaper Thumbnail: " + children.get(j).getData().getThumbnail() + "\n" +
                            "Wallpaper URL: " + children.get(j).getData().getUrl() + "\n");
                }

                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                Bitmap bitmap;


                if (position != (wallpapers.size() - 1)) {
                    String urlString = wallpapers.get(position + 1).getUrl();
                    try {
                        URL url = new URL(urlString);
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        wallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                        System.out.println(e);
                    }

                } else {
                    String urlString = wallpapers.get(0).getUrl();
                    try {
                        URL url = new URL(urlString);
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        wallpaperManager.setBitmap(bitmap);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {

                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "An error occured!", Toast.LENGTH_SHORT).show();
            }
        });
/*            }
        };
        mainHandler.post(myRunnable);*/
        return Result.SUCCESS;
    }
}
