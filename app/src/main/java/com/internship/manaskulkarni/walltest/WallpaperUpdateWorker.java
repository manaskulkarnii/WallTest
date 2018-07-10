package com.internship.manaskulkarni.walltest;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

public class WallpaperUpdateWorker extends Worker {
    public static final String TAG = "WallpaperUpdateWorker";
    //    private WallpaperManager wallpaperManager = WallpaperManager.getInstance(SetWallpaper.getContext());
    //  private SetWallpaper setWallpaper;
    //private int position = getInputData().getInt("sendPosition",0);
    //private ArrayList<Wallpaper> wallpaper = setWallpaper.getSendWallpaper();
    // private int position = 3;
    // private Bitmap bitmap;
/*
    public WallpaperUpdateWorker(int position, ArrayList<Wallpaper> wallpaper) {
        this.position = position;
        this.wallpaper = wallpaper;
    }*/

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Started to work");

        //WallpaperUtils.changeWallpaper(position+1,wallpaper.get(position).getUrl(),SetWallpaper.getContext());
        //WallpaperUtils.changeWallpaper(position+1,"https://picsum.photos/300/400/?random",getApplicationContext());
        WallpaperUtils.autoChangeWallpaper();
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
