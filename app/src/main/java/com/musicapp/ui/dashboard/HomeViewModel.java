package com.musicapp.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.musicapp.model.ApiResource;
import com.musicapp.model.Status;
import com.musicapp.model.Track;

import java.util.List;

/**
 * Created by chetan on 11/12/18.
 */

public class HomeViewModel extends AndroidViewModel {

    //public LiveData<ApiResource<List<Track>>> trackObserver;
    public MediatorLiveData<ApiResource<List<Track>>> dataObserver;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        dataObserver = new MediatorLiveData<>();
        //trackObserver = HomeRepo.getInstance().getTrackList("Michael+jackson");
    }

    public void getData(String query){
        final LiveData<ApiResource<List<Track>>> liveData = HomeRepo.getInstance().getTrackList(query);
        dataObserver.addSource(liveData, new Observer<ApiResource<List<Track>>>() {
            @Override
            public void onChanged(@Nullable ApiResource<List<Track>> listApiResource) {
                dataObserver.setValue(listApiResource);

                //not removing if status is still loading
                if(listApiResource != null && listApiResource.status != Status.LOADING)
                    dataObserver.removeSource(liveData);
            }
        });
    }
}
