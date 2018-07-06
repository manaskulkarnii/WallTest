package com.internship.manaskulkarni.walltest;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class WallpaperUtils {

    private static WallpaperManager wallpaperManager;
    private static Bitmap bitmap;
    static void changeWallpaper(int position, String wallpaper, Context context){

        wallpaperManager = WallpaperManager.getInstance(context);
        try {
            URL url = new URL(wallpaper);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            wallpaperManager.setBitmap(bitmap);
        } catch(IOException e) {
            System.out.println(e);
        }
    }
}
