package com.internship.manaskulkarni.walltest;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class SetWallpaper extends AppCompatActivity {


    private static final String TAG = "setWallpaperActivity";

    private static Context mContext = getContext();
    //private static MainActivity instance;

    public Context context = SetWallpaper.this;
    private Bitmap bitmap;
    //private Timer timer;
    //private TimerTask timerTask;
    private int sendPostion;
    private ArrayList<Wallpaper> sendWallpaper;

    //public PeriodicWorkRequest.Builder changeWallpaperBuilder;
    //public PeriodicWorkRequest changeWallpaper;

    /*public static MainActivity getInstance() {
        return instance;
    }*/

    public static Context getContext() {
        //return instance.getApplicationContext();
        return mContext;
    }

    public int getSendPostion() {
        return sendPostion;
    }

    public void setSendPostion(int sendPostion) {
        this.sendPostion = sendPostion;
    }

    public ArrayList<Wallpaper> getSendWallpaper() {
        return sendWallpaper;
    }

    public void setSendWallpaper(ArrayList<Wallpaper> sendWallpaper) {
        this.sendWallpaper = sendWallpaper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);

        //mContext = getApplicationContext();

        // timer = new Timer();


        final int posi = getIntent().getExtras().getInt("posi");
        Log.d(TAG, "bundle: Position: " + posi);
        //sendPostion = posi;
        setSendPostion(posi);

        final ArrayList<Wallpaper> wallpaper = getIntent().getParcelableArrayListExtra("WALLPAPER");
        Log.d(TAG, "bundle: Wallpaper: " + wallpaper.get(posi).getTitle());
        //sendWallpaper = wallpaper;
        setSendWallpaper(wallpaper);
        Log.d(TAG,"getSendWallpaper: "+getSendWallpaper().toString());


        final ProgressBar progressBar = findViewById(R.id.idProgressBar);
        final ImageView mSetWallpaperImageView = findViewById(R.id.idSetWallpaperImageView);
        Log.d(TAG, " IF: outside");
        if (context != null) {
            Log.d(TAG, " IF: inside");
            Glide.with(context)
                    .asBitmap()
                    .load(wallpaper.get(posi).getUrl())
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SetWallpaper.this, "Image failed to load", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onLoadFailed : Image failed to load");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            mSetWallpaperImageView.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(mSetWallpaperImageView);
            Button mSetWallpaperButton = findViewById(R.id.idSetWallpaperButton);
            mSetWallpaperButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bitmap = ((BitmapDrawable) mSetWallpaperImageView.getDrawable()).getBitmap();
                    final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                    try {
                        wallpaperManager.setBitmap(bitmap);
                        String urlString = wallpaper.get(posi).getUrl();
                        Log.d(TAG,"urlString: "+urlString);
                        Toast.makeText(context, "Wallpaper applied", Toast.LENGTH_SHORT).show();
/*
                        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType
                                .CONNECTED).build();

                        changeWallpaperBuilder = new PeriodicWorkRequest.Builder(WorkerTest.class, 16, TimeUnit.MINUTES).setConstraints(constraints);

                        changeWallpaper = changeWallpaperBuilder.build();
                        WorkManager.getInstance().cancelAllWork();
                        WorkManager.getInstance().enqueue(changeWallpaper);*/


                        /*Intent serviceIntent = new Intent(context,AutoChangeWallpaper.class);
                        //serviceIntent.setAction("com.internship.manaskulkarni.walltest.AutoChangeWallpaper");
                        context.startService(serviceIntent);*/


                        //JobManager.create(context).addJobCreator(new DemoJobCreator());
                        //JobConfig.setAllowSmallerIntervalsForMarshmallow(true);

                        // DemoSyncJob.schedulePeriodicJob();


                        Constraints constraints = new Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED).build();
                        /*Data data = new Data.Builder()
                                .putInt("sendPosition",posi)
                                .build();*/
                        PeriodicWorkRequest wallpaperUpdateWork = new PeriodicWorkRequest.Builder(
                                WallpaperUpdateWorker.class, 15, TimeUnit.MINUTES, 5, TimeUnit.MINUTES)
                                .setConstraints(constraints)
                                //.setInputData(data)
                                .addTag(WallpaperUpdateWorker.TAG)
                                .build();
                        if (WorkManager.getInstance() != null) {
                            WorkManager.getInstance().cancelAllWork();
                            WorkManager.getInstance().enqueue(wallpaperUpdateWork);
                            Toast.makeText(context, "Enqueue applied", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //transfer bundle
/*
                    Bundle bundle= new Bundle();
                    bundle.putInt("position",posi);
                    bundle.putParcelableArrayList("wallpaper",wallpaper);*/


                    //autochange in 5 seconds
/*                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if(posi!= (wallpaper.size()-1)){
                                String urlString =  wallpaper.get(posi+1).getUrl();
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
                    };

                    timer.scheduleAtFixedRate(timerTask, 5000, 2000);*/
                }
            });

        }
        //SetWallpaperAdapter setWallpaperAdapter = new SetWallpaperAdapter(SetWallpaper.this,wallpaper);

    }


}
