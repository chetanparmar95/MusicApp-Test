package com.musicapp.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.musicapp.model.ApiResource;
import com.musicapp.model.Track;

import java.util.List;

/**
 * Created by chetan on 11/12/18.
 */

public class HomeViewModel extends AndroidViewModel {

    public LiveData<ApiResource<List<Track>>> trackObserver;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        trackObserver = HomeRepo.getInstance().getTrackList("Michael+jackson");
    }
}
