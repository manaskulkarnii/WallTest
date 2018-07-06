package com.internship.manaskulkarni.walltest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import java.util.ArrayList;

public class DemoJobCreator implements JobCreator {

    int posi;
    Context context;
    ArrayList<Wallpaper> wallpaper;

    public DemoJobCreator(int posi,ArrayList<Wallpaper> wallpaper ,Context context) {
        this.posi = posi;
        this.wallpaper = wallpaper;
        this.context = context;
    }

    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        switch (tag) {
            case DemoSyncJob.TAG:
                return new DemoSyncJob(posi,wallpaper,context);
            default:
                return null;
        }
    }
}
