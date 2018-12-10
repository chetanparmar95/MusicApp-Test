package com.musicapp.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * Created by chetan on 11/12/18.
 */

public class HomeViewModel extends AndroidViewModel {

    public HomeViewModel(@NonNull Application application) {
        super(application);
        HomeRepo.getInstance().getTrackList();
    }
}
