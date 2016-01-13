package com.moreno.imgurviewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.moreno.imgurviewer.adapter.LargeImageAdapter;
import com.moreno.imgurviewer.fragment.FullImageFragment;
import com.moreno.imgurviewer.models.GalleryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adan on 1/12/16.
 * <br />
 * <br />
 * Activity class which displays a full-screen gallery view
 * with swipe recognition to traverse through the images
 */
public class ImageDetailActivity extends FragmentActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        ArrayList<GalleryItem> items = getIntent().getParcelableArrayListExtra(Common.GALLERY);
        populatePager(items);
    }

    public void populatePager(ArrayList<GalleryItem> items){
        List<Fragment> fragmentList = new ArrayList<>(items.size());
        for( GalleryItem item : items ){
            FullImageFragment mix = new FullImageFragment();
            fragmentList.add(mix.newInstance(item, this));
        }

        LargeImageAdapter pageAdapter = new LargeImageAdapter(getSupportFragmentManager(), fragmentList);
        ViewPager imagePager = (ViewPager) findViewById(R.id.pager_gallery);
        imagePager.setOffscreenPageLimit(pageAdapter.getCount() / 4); //Load a quarter of the pages to improve performance
        imagePager.setAdapter(pageAdapter);
        imagePager.setClipChildren(false);

        GalleryItem currentItem = getIntent().getParcelableExtra(Common.GALLERY_ID);
        int position = items.indexOf(currentItem);
        if( position != -1 ) {
            imagePager.setCurrentItem(position);
        }
    }

}
