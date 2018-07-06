package com.internship.manaskulkarni.walltest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.internship.manaskulkarni.walltest.model.Child;
import com.internship.manaskulkarni.walltest.model.PostList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ThumbnailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Call<PostList> jsonData = RedditAPI.getRedditService().getPostList();

        jsonData.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                Log.d(TAG, "onResponse: feed: SUCCESS: " + response.body().toString());
                Log.d(TAG, "onRespone: Server Response: " + response.toString());

                assert response.body() != null;
                List<Child> children = response.body().getData().getChildren();
                ArrayList<Wallpaper> wallpapers = new ArrayList<Wallpaper>();

                for (int i = 0; i < children.size(); i++) {
                    wallpapers.add(new Wallpaper(
                            children.get(i).getData().getId(),
                            children.get(i).getData().getTitle(),
                            children.get(i).getData().getThumbnail(),
                            children.get(i).getData().getUrl()
                    ));
                }

                for (int j = 0; j < children.size(); j++) {
                    Log.d(TAG, "onResponse: \n" +
                            "Wallpaper ID: " + children.get(j).getData().getId() + "\n" +
                            "Wallpaper Title: " + children.get(j).getData().getTitle() + "\n" +
                            "Wallpaper Thumbnail: " + children.get(j).getData().getThumbnail() + "\n" +
                            "Wallpaper URL: " + children.get(j).getData().getUrl() + "\n");
                }


                GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);

                RecyclerView mRecyclerView = findViewById(R.id.idRecyclerView);
                mRecyclerView.setLayoutManager(layoutManager);
                ThumbnailAdapter thumbnailAdapter = new ThumbnailAdapter(MainActivity.this, wallpapers);

                mRecyclerView.setAdapter(thumbnailAdapter);


            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage());
                Toast.makeText(MainActivity.this, "An error occured!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}
