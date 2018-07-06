package com.internship.manaskulkarni.walltest;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AutoChangeWallpaper extends Service {

    private static final String TAG = "AutoChangeWallpaper";
    Timer mytimer;
    int interval=1000000;
    Drawable drawable;
    WallpaperManager wallpaperManager;
    Bitmap bitmap;
    private SetWallpaper setWallpaper;
    private int position;
    private ArrayList<Wallpaper> wallpaper;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mytimer = new Timer();
        wallpaperManager = WallpaperManager.getInstance(AutoChangeWallpaper.this);
        position = setWallpaper.getSendPostion();
        wallpaper = setWallpaper.getSendWallpaper();
        Log.d(TAG,": called");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mytimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(position!= (wallpaper.size()-1)){
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
            }
        },interval,interval);
        return super.onStartCommand(intent, flags, startId);
    }
}
