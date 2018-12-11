package com.musicapp.ui.dashboard;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.musicapp.MusicApp;
import com.musicapp.R;
import com.musicapp.databinding.ActivityHomeBinding;
import com.musicapp.model.ApiResource;
import com.musicapp.model.Status;
import com.musicapp.model.Track;
import com.musicapp.ui.track_detail.TrackDetailActivity;
import com.musicapp.utils.BaseActivity;

import java.util.List;

public class HomeActivity extends BaseActivity implements OnTrackClickListener{

    private static final int REQUEST_STORAGE_PERMISSION = 1000;
    private HomeViewModel homeViewModel;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        fetchTrackList();
    }

    @Override
    public void onProductClicked(Track track, int position) {
        Intent intent = new Intent(this, TrackDetailActivity.class);
        intent.putExtra("data",track);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MusicApp)getApplication()).getImageLoader().clearCache();
    }

    private void fetchTrackList(){
        homeViewModel.trackObserver.observe(this, new Observer<ApiResource<List<Track>>>() {
            @Override
            public void onChanged(@Nullable ApiResource<List<Track>> listApiResource) {
                if(listApiResource != null && listApiResource.status != Status.LOADING) {
                    if (listApiResource.res_status && listApiResource.data != null) {
                        for (int i = 0; i < listApiResource.data.size(); i++) {
                            Log.e("Data", "onResponse: home " + listApiResource.data.get(i).getTrackName());
                            binding.setCallback(HomeActivity.this);
                            binding.setData(listApiResource.data);
                        }
                    }
                }
            }
        });
    }


}
