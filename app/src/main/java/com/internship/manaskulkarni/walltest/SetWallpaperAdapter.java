package com.internship.manaskulkarni.walltest;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class SetWallpaperAdapter extends RecyclerView.Adapter<SetWallpaperAdapter.ViewHolder>  {
    private ArrayList<Wallpaper> mSetWallpaper;
    private ThumbnailAdapter.ItemClickListener mClickListener;
    private Context mContext;

    // data is passed into the constructor
    SetWallpaperAdapter(Context mContext, ArrayList<Wallpaper> mSetWallpaper) {
        this.mContext = mContext;
        this.mSetWallpaper = mSetWallpaper;
    }

    // inflates the cell layout from xml when needed
    @Override
    public SetWallpaperAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_set_wallpaper, parent, false);
        return new SetWallpaperAdapter.ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final SetWallpaperAdapter.ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mSetWallpaper.get(position).getThumbnail())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imageView.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "IMAGE CLICKED", Toast.LENGTH_SHORT).show();

                /*Intent i = new Intent(CategoryAdapter.this, demo.class);*/
            }
        });


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mSetWallpaper.size();
    }

    // allows clicks events to be caught
    void setClickListener(ThumbnailAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    // convenience method for getting data at click position
    /*String getItem(int id) {
        return mWallpaperThumbnail[id];
    }*/

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.idWallpaperThumbnail);
            progressBar=itemView.findViewById(R.id.idProgressBar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
