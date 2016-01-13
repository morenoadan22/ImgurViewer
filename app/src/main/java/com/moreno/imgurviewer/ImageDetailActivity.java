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
        ViewPager suggestedPager = (ViewPager) findViewById(R.id.pager_gallery);
        suggestedPager.setOffscreenPageLimit(pageAdapter.getCount());
        suggestedPager.setAdapter(pageAdapter);
        suggestedPager.setClipChildren(false);

        GalleryItem currentItem = getIntent().getParcelableExtra(Common.GALLERY_ID);
        int position = items.indexOf(currentItem);
        if( position != -1 ) {
            suggestedPager.setCurrentItem(position);
        }
    }

}
