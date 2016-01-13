package com.moreno.imgurviewer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.moreno.imgurviewer.R;
import com.moreno.imgurviewer.models.GalleryItem;
import com.squareup.picasso.Picasso;

/**
 * Created by adan on 1/12/16.
 *
 * Fragment class that renders single static image or gif content inside a full
 * screen view
 */
public class FullImageFragment extends Fragment{

    private  GalleryItem item;
    private Context context;

    public FullImageFragment newInstance(GalleryItem item, Context context){
        this.context = context;
        this.item = item;
        return this;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_gallery_item, container, false);

        if( context != null ){
            ImageView imageFullScreenView = (ImageView) v.findViewById(R.id.image_full_view);
            WebView webViewScreenView = (WebView) v.findViewById(R.id.web_full_view);

            TextView textTitle = (TextView) v.findViewById(R.id.text_title);
            TextView textId = (TextView) v.findViewById(R.id.text_id);
            TextView textType = (TextView) v.findViewById(R.id.text_type);
            textTitle.setText(item.getTitle());
            textId.setText(item.getId());
            textType.setText(item.getType());
            textTitle.setSelected(true);

            if( !item.getType().contains("gif") ) {
                imageFullScreenView.setVisibility(View.VISIBLE);
                webViewScreenView.setVisibility(View.GONE);
                Picasso.with(context)
                        .load(item.getLink())
                        .placeholder(R.anim.animation_rotate)
                        .into(imageFullScreenView);
            }else{
                webViewScreenView.setVisibility(View.VISIBLE);
                imageFullScreenView.setVisibility(View.GONE);
                webViewScreenView.getSettings().setJavaScriptEnabled(true);
                webViewScreenView.getSettings().setUseWideViewPort(true);
                webViewScreenView.setInitialScale(1);
                webViewScreenView.getSettings().setLoadWithOverviewMode(true);
                webViewScreenView.loadUrl(item.getLink());
            }
        }

        return v;
    }
}
