package com.internship.manaskulkarni.walltest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ViewHolder> {

    private static final String TAG = "ThumbnailAdapter";
    private ArrayList<Wallpaper> mWallpaperThumbnail;
    private ItemClickListener mClickListener;
    private Context mContext;


    // data is passed into the constructor
    ThumbnailAdapter(Context mContext, ArrayList<Wallpaper> mWallpaperThumbnail) {
        this.mContext = mContext;
        this.mWallpaperThumbnail = mWallpaperThumbnail;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mWallpaperThumbnail.get(position).getThumbnail())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "IMAGE CLICKED", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, SetWallpaper.class);
                intent.putExtra("posi", position);
                Log.d(TAG, "onClick: Position: " + position);
/*                Bundle bundle = new Bundle();
                bundle.putParcelable("WALLPAPER", (Parcelable) mWallpaperThumbnail);*/
                intent.putParcelableArrayListExtra("WALLPAPER",mWallpaperThumbnail);
                mContext.startActivity(intent);
            }
        });


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mWallpaperThumbnail.size();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
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

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.idWallpaperThumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            /*int mPosi = getAdapterPosition();
            Intent intent = new Intent(mContext, SetWallpaper.class);
            intent.putExtra("POSITION: ", mPosi);
            mContext.startActivity(intent);*/
        }
    }

}