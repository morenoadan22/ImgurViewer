package com.moreno.imgurviewer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.moreno.imgurviewer.Common;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by adan on 1/11/16.
 */
public class GalleryItem implements Parcelable{

    private String id;
    private String title;
    private String type;
    private String link;

    public GalleryItem(JSONObject json){
        try {
            setId(json.getString(Common.GALLERY_ID));
            setTitle(json.getString(Common.GALLERY_TITLE));
            setType(json.getString(Common.GALLERY_TYPE));
            setLink(json.getString(Common.GALLERY_LINK));
        }catch(JSONException je){
            je.printStackTrace();
        }
    }

    public boolean equals(Object o){
        if( o instanceof GalleryItem ){
            GalleryItem gi = (GalleryItem) o;
            return gi.getId().equals(getId());
        }
        return false;
    }

    public GalleryItem (Parcel in) {
        setId(in.readString());
        setTitle(in.readString());
        setType(in.readString());
        setLink(in.readString());
    }

    public static final Creator<GalleryItem> CREATOR = new Creator<GalleryItem>() {
        @Override
        public GalleryItem createFromParcel (Parcel in) {
            return new GalleryItem(in);
        }

        @Override
        public GalleryItem[] newArray (int size) {
            return new GalleryItem[size];
        }
    };

    public String getId(){
        return this.id != null ? id : "";
    }

    public String getTitle(){
        return this.title != null ? title : "";
    }

    public String getType(){
        return this.type != null ? type : "";
    }

    public String getLink(){
        return this.link != null ? link : "";
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setLink(String link){
        this.link = link;
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel parcel, int flag) {
        parcel.writeString(getId());
        parcel.writeString(getTitle());
        parcel.writeString(getType());
        parcel.writeString(getLink());
    }
}
