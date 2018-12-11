package com.musicapp.ui.dashboard;

import android.arch.lifecycle.LiveData;

import com.android.volley.Request;
import com.google.gson.reflect.TypeToken;
import com.musicapp.model.ApiResource;
import com.musicapp.model.Track;
import com.musicapp.network.LiveDataCallAdapter;
import com.musicapp.utils.Constants;

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

    public LiveData<ApiResource<List<Track>>> getTrackList(String query){
        LiveDataCallAdapter<List<Track>> call = new LiveDataCallAdapter<>(new TypeToken<ApiResource<List<Track>>>(){}.getType());
        return call.call(Request.Method.GET, Constants.BASE_URL+Constants.SEARCH_TRACK+"?term="+query);
    }
}
