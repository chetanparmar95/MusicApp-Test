package com.musicapp.ui.dashboard;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.JsonObject;
import com.musicapp.model.ApiResource;
import com.musicapp.model.Track;
import com.musicapp.network.GsonRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chetan on 11/12/18.
 */

public class HomeRepo {
    private static HomeRepo homeRepo;
    List<ApiResource<Track>> result;

    private HomeRepo(){
        result = new ArrayList<>();
    }

    public static HomeRepo getInstance(){
        if(homeRepo==null)
            homeRepo = new HomeRepo();
        return homeRepo;
    }

    public void getTrackList(){
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.GET, "", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data", "onResponse: "+response.toString() );
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
