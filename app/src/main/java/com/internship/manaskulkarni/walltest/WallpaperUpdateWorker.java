package com.internship.manaskulkarni.walltest;

import android.app.WallpaperManager;
import android.content.Context;
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

import androidx.work.Worker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.internship.manaskulkarni.walltest.SetWallpaper.getContext;

public class WallpaperUpdateWorker extends Worker {
    public static final String TAG = "WallpaperUpdateWorker";
    public Context context = getApplicationContext();
    private WallpaperManager wallpaperManager;
    // private static Bitmap bitmap;
    private Random random = new Random();
    private int position = 0;


    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Started to work");


        //WallpaperUtils wu = new WallpaperUtils();
        //wu.autoChangeWallpaper(context);

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

                position = random.nextInt(4);
                Log.d(TAG, "Random Postion: " + position);
                String temp = wallpapers.get(position).getUrl();

                wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                if (position != (wallpapers.size() - 1)) {

                    Glide.with(getApplicationContext())
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


                }
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {

                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(getContext(), "An error occured!", Toast.LENGTH_SHORT).show();
            }
        });


        //WallpaperUtils.changeWallpaper(position+1,wallpaper.get(position).getUrl(),SetWallpaper.getContext());
        //WallpaperUtils.changeWallpaper(position+1,"https://picsum.photos/300/400/?random",getApplicationContext());
        /*if(position!= (wallpaper.size()-1)){
            String urlString =  wallpaper.get(position+1).getUrl();
            try {
                URL url = new URL(urlString);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                wallpaperManager.setBitmap(bitmap);
            } catch(IOException e) {
                System.out.println(e);
            }

        }
        else {
            String urlString =  wallpaper.get(0).getUrl();
            try {
                URL url = new URL(urlString);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                wallpaperManager.setBitmap(bitmap);
            } catch(IOException e) {
                System.out.println(e);
            }
        }
*/
        return Result.SUCCESS;
    }
}
