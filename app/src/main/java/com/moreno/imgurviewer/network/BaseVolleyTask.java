package com.moreno.imgurviewer.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.moreno.imgurviewer.Common;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by adan on 1/11/16.
 */
public abstract class BaseVolleyTask<E> implements Response.Listener<String>, Response.ErrorListener, TaskListener<String> {
    private static final String TAG = BaseVolleyTask.class.getSimpleName();

    private static final String CACHE_REQUEST_PREF_NAME = "cached_request_results";

    private static SharedPreferences cachedRequest = null;
    private final TaskListener<E> taskListener;
    private final int method;
    private String tmpCompleteUrl;
    private static RequestQueue requestQueue = null;

    public BaseVolleyTask(final int method, final Context ctx, final TaskListener<E> taskListener) {
        this.method = method;
        this.taskListener = taskListener;
        initQueue(ctx);

        if (cachedRequest == null) {
            cachedRequest = ctx.getSharedPreferences(CACHE_REQUEST_PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    private static void initQueue(final Context ctx) {
        if (requestQueue == null && ctx != null) {
            requestQueue = Volley.newRequestQueue(ctx);
        }

        if (requestQueue == null) {
            throw new NullPointerException("RequestQueue must be not null, you should call 'initQueue(context) before instantiating any request'");
        }
    }

    protected boolean shouldBeCached() {
        return true;
    }

    protected abstract String getRequestUrl();

    protected abstract E parseResponse(final JSONObject resultObj);

    protected LinkedHashSet<String> getParams() {
        return null;
    }

    protected Map<String, String> getHttpEntity() {
        return null;
    }

    private String getCompleteURL() {
        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(getRequestUrl());

        final Set<String> params = getParams();
        if (params != null && params.size() > 0) {
            urlBuilder.append(TextUtils.join("/", params));
        }

        return urlBuilder.toString();
    }

    public final void execute() {
        tmpCompleteUrl = getCompleteURL();

        final StringRequest request = new StringRequest(method, tmpCompleteUrl, this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Client-ID " +Common.IMGUR_CLIENT_ID);
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return getHttpEntity();
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(3000, 3, 1));

        requestQueue.add(request);
    }

    public final void onResponse(final String response) {
        if (shouldBeCached()) {
            final SharedPreferences.Editor editor = cachedRequest.edit();
            editor.putString(tmpCompleteUrl, response);
            editor.apply();
        }

        try {
            onTaskSuccess(response);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public final void onErrorResponse(final VolleyError error) {
        if (shouldBeCached()) {
            if ( TextUtils.isEmpty(tmpCompleteUrl)) {
                tmpCompleteUrl = getCompleteURL();
            }

            final String lastResponse = cachedRequest.getString(tmpCompleteUrl, null);

            if (!TextUtils.isEmpty(lastResponse)) {
                onResponse(lastResponse);
            } else {
                onTaskFailure(error);
            }
        }
    }

    public void onTaskSuccess(final String result) throws IOException, JSONException {
        JSONObject jResult = null;

        try{
            jResult = new JSONObject(result);
        }catch (JSONException je){
            je.printStackTrace();
        }

        if (jResult != null && jResult.has("error")) {
            taskListener.onTaskFailure(new Exception(jResult.get("error").toString()));
        } else {
            final E resultParsed = parseResponse(jResult);
            if (taskListener != null) {

                if (resultParsed != null) {
                    taskListener.onTaskSuccess(resultParsed);
                } else {
                    taskListener.onTaskFailure(new Exception("No response was received from the server."));
                }
            }
        }
    }

    public void onTaskFailure(final Exception e) {
        if (taskListener != null) {
            taskListener.onTaskFailure(e);
        } else {
            Log.i(TAG, "onTaskFailure-> No delegate implemented in " + getClass().getSimpleName() + ": " + ((e == null) ? "NULL" : e.getMessage()));
        }
    }

    public void onTaskCancelled() {
        if (taskListener != null) {
            taskListener.onTaskCancelled();
        } else {
            Log.i(TAG, "onTaskCancelled-> No delegate implemented in " + getClass().getSimpleName());
        }
    }


}
