package com.musicapp.ui.track_detail;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.musicapp.R;
import com.musicapp.databinding.ActivityTrackDetailBinding;
import com.musicapp.model.Track;

public class TrackDetailActivity extends AppCompatActivity {

    private ActivityTrackDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_track_detail);
        Track track = getIntent().getParcelableExtra("data");
        binding.setData(track);
    }
}
