package com.internship.manaskulkarni.walltest;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DemoSyncJob extends Job {

    public static final String TAG = "DemoSyncJob";
    private SetWallpaper setWallpaper;
    private Bitmap bitmap;
    private int position ;
    private ArrayList<Wallpaper> wallpaper;
    private Context context;

    public DemoSyncJob(int position,ArrayList<Wallpaper> wallpaper, Context context) {
        this.position = position;
        this.wallpaper = wallpaper;
        this.context = context;
    }

    private WallpaperManager wallpaperManager = WallpaperManager.getInstance(SetWallpaper.getContext());

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        Log.d(TAG,"onRunJobCalled");
        Toast.makeText(SetWallpaper.getContext(),"ONRUN JOB EXECUTING",Toast.LENGTH_SHORT).show();
        new Thread(){
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
        }.start();
        return Result.SUCCESS;
    }

    private void runJobImmediately() {
        new JobRequest.Builder(DemoSyncJob.TAG)
                .startNow()
                .build()
                .schedule();
    }
/*    public static void scheduleJob() {
        new JobRequest.Builder(DemoSyncJob.TAG)
                .setExecutionWindow(30_000L, 40_000L)
                .build()
                .schedule();
    }*/
    public static void schedulePeriodicJob() {
        Set<JobRequest> jobRequests = JobManager.instance().getAllJobRequestsForTag(DemoSyncJob.TAG);
        if (!jobRequests.isEmpty()) {
            return;
        }
                 new JobRequest.Builder(DemoSyncJob.TAG)
                         .setPeriodic(TimeUnit.MINUTES.toMillis(15),TimeUnit.MINUTES.toMillis(5))
                         .setUpdateCurrent(true)
                         .setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                         .setRequirementsEnforced(true)
                         .build()
                         .schedule();
    }
}
