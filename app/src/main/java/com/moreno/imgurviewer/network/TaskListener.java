package com.moreno.imgurviewer.network;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by adan on 1/11/16.
 * <br />
 * <br />
 * Interface that behaves as a listener to indicate when a result from a response was received
 */
public interface TaskListener<T> {
    void onTaskSuccess (T result) throws IOException, JSONException;
    void onTaskFailure (Exception e);
    void onTaskCancelled ();
}
