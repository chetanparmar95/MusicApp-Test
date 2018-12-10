package com.musicapp.ui.dashboard;


import com.musicapp.model.Track;
import com.musicapp.utils.BaseAdapter;

public interface OnTrackClickListener extends BaseAdapter.BaseInterface {
    void onProductClicked(Track track, int position);
}
