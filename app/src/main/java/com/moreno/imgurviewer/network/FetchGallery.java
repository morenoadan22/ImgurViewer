package com.moreno.imgurviewer.network;

import android.content.Context;

import com.android.volley.Request;
import com.moreno.imgurviewer.Common;
import com.moreno.imgurviewer.models.GalleryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Created by adan on 1/11/16.
 */
public class FetchGallery extends BaseVolleyTask<ArrayList<GalleryItem>>{



    private String section;//section optional hot | top | user - defaults to hot
    private String sort;//sort optional viral | top | time | rising (only available with user section) - defaults to viral
    private int page;//page	optional integer - the data paging number

    public FetchGallery (Context ctx, TaskListener<ArrayList<GalleryItem>> taskListener) {
        super(Request.Method.GET, ctx, taskListener);
    }

    public void setPage(int page){
        this.page = page;
    }

    public void setSection(String section){
        this.section = section;
    }

    public void setSort(String sort){
        this.sort = sort;
    }

    public LinkedHashSet<String> getParams(){
        LinkedHashSet<String> paramSet = new LinkedHashSet<>(3);
        paramSet.add(section == null ? "hot" : section);
        paramSet.add(sort == null ? "viral" : sort);
        paramSet.add(String.valueOf(page));
        return paramSet;
    }

    @Override
    protected String getRequestUrl () {
        return Common.BASE_URL;
    }

    @Override
    protected ArrayList<GalleryItem> parseResponse (JSONObject response) {
        ArrayList<GalleryItem> items = new ArrayList<>();
        try {
            JSONArray data = response.getJSONArray("data");
            for( int i = 0; i < data.length(); i++ ){
                JSONObject jItem = data.getJSONObject(i);
//                if( !jItem.getString("type").contains("gif") ) {
                    items.add(new GalleryItem(data.getJSONObject(i)));
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return items;
    }
}
