package com.internship.manaskulkarni.walltest;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.internship.manaskulkarni.walltest.model.Child;
import com.internship.manaskulkarni.walltest.model.PostList;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DemoSyncJob extends Job {

    public static final String TAG = "DemoSyncJob";

    private int position = 4;
/*    private SetWallpaper setWallpaper;
    private Bitmap bitmap;
    private int position ;
    private ArrayList<Wallpaper> wallpaper;
    private Context context;*/
/*
    public DemoSyncJob(int position,ArrayList<Wallpaper> wallpaper, Context context) {
        this.position = position;
        this.wallpaper = wallpaper;
        this.context = context;

        Log.d(TAG,"\nPostion :"+this.position +
                        "\nWallpaper :"+this.wallpaper+
                        "\nContext :"+this.context);
    }*/

    // private WallpaperManager wallpaperManager = WallpaperManager.getInstance(SetWallpaper.getContext());

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
                .setPeriodic(TimeUnit.MINUTES.toMillis(16), TimeUnit.MINUTES.toMillis(6))
                .setUpdateCurrent(true)
                .setRequiredNetworkType(JobRequest.NetworkType.UNMETERED)
                .setRequirementsEnforced(true)
                .build()
                .schedule();
    }

    private void runJobImmediately() {
        new JobRequest.Builder(DemoSyncJob.TAG)
                .startNow()
                .build()
                .schedule();
    }

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        Log.d(TAG,"onRunJobCalled");
        //Toast.makeText(SetWallpaper.getContext(),"ONRUN JOB EXECUTING",Toast.LENGTH_SHORT).show();
        new Thread(){
            @Override
            public void run() {

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
                }*/

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

                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
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
                        Toast.makeText(getContext(), "An error occured!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();
        return Result.SUCCESS;
    }
}
