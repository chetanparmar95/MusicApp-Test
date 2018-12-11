package com.musicapp.ui.dashboard;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.musicapp.utils.BaseActivity;
import com.musicapp.utils.PermissionUtils;

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
        checkStoragePermission();
    }

    @Override
    public void onProductClicked(Track track, int position) {
        Toast.makeText(this, track.getTrackName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MusicApp)getApplication()).getImageLoader().clearCache();
    }

    private void checkStoragePermission(){
        PermissionUtils.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtils.PermissionAskListener() {
            @Override
            public void onNeedPermission() {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            }

            @Override
            public void onPermissionPreviouslyDenied() {

            }

            @Override
            public void onPermissionDisabled() {
                //user will have to allow this permission from setting
            }

            @Override
            public void onPermissionGranted() {
                fetchTrackList();
            }
        });
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                //here we want to fetch track list whether permission is given or not
                //if storage permission is not given then we will not use file cache to store images
                fetchTrackList();
            }

        }
    }
}
