package com.moreno.imgurviewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.moreno.imgurviewer.adapter.ImageAdapter;
import com.moreno.imgurviewer.models.GalleryItem;
import com.moreno.imgurviewer.network.FetchGallery;
import com.moreno.imgurviewer.network.TaskListener;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by adan on 1/11/16.
 * <br />
 * <br />
 * Displays a view containing a grid of thumbnails that is dynamically populated
 * and rendered as the user scrolls through
 */
public class LoadingActivity extends Activity implements TaskListener<ArrayList<GalleryItem>>{
    private static final String LOG_TAG = LoadingActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_grid);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                renderGallery(0);
            }
        });

        renderGallery(0);
    }

    private void renderGallery(int page){
        mSwipeRefreshLayout.setRefreshing(true);
        FetchGallery images = new FetchGallery(this, this);
        images.setPage(page);
        images.execute();
    }

    @Override
    public void onTaskSuccess (ArrayList<GalleryItem> items) throws IOException, JSONException {
        mSwipeRefreshLayout.setRefreshing(false);
        ImageAdapter adapter = new ImageAdapter(LoadingActivity.this);
        adapter.setData(items);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onTaskFailure (Exception e) {
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d(LOG_TAG, "onTaskFailure: " + e.getMessage());
        Toast.makeText(this, "onTaskFailure: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskCancelled () {
        mSwipeRefreshLayout.setRefreshing(false);
        Log.d(LOG_TAG, "onTaskCancelled");
        Toast.makeText(this, "onTaskCancelled", Toast.LENGTH_SHORT).show();
    }
}
