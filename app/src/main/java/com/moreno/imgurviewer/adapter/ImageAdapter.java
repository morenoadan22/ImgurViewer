package com.moreno.imgurviewer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moreno.imgurviewer.Common;
import com.moreno.imgurviewer.ImageDetailActivity;
import com.moreno.imgurviewer.R;
import com.moreno.imgurviewer.models.GalleryItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by adan on 1/11/16.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textTitle;
        public ImageView image;

        public ViewHolder(View v){
            super(v);
            textTitle = (TextView) v.findViewById(R.id.text_title);
            image = (ImageView) v.findViewById(R.id.image_container);
        }
    }

    private Context context;
    private static LayoutInflater inflater;
    private ArrayList<GalleryItem> galleryItems;

    public ImageAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.gallery_item_cell, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder (ViewHolder viewHolder, int position) {
        final GalleryItem item = galleryItems.get(position);
        viewHolder.textTitle.setText(item.getTitle());

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent imageDetailIntent = new Intent(context, ImageDetailActivity.class);
                imageDetailIntent.putExtra(Common.GALLERY_ID, item);
                imageDetailIntent.putParcelableArrayListExtra(Common.GALLERY, galleryItems);
                context.startActivity(imageDetailIntent);
            }
        });

        try {
            Picasso.with(context)
                    .load(item.getLink())
                    .error(R.color.charcoal)
                    .placeholder(R.drawable.animation_rotate)
                    .into(viewHolder.image);
        }catch(Exception e){
            viewHolder.image.setImageResource(R.color.charcoal);
        }

    }

    @Override
    public int getItemCount () {
        return galleryItems.size();
    }

    public void setData(ArrayList<GalleryItem> items){
        this.galleryItems = new ArrayList<>();
        for( GalleryItem item : items ){
            if( item.getLink() != null && !item.getLink().isEmpty() ){
                galleryItems.add(item);
            }
        }
        notifyDataSetChanged();
    }

}
