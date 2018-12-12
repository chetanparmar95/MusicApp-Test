package com.musicapp.ui.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.musicapp.MusicApp;
import com.musicapp.R;
import com.musicapp.databinding.ActivityHomeBinding;
import com.musicapp.model.ApiResource;
import com.musicapp.model.Status;
import com.musicapp.model.Track;
import com.musicapp.ui.track_detail.TrackDetailActivity;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnTrackClickListener{

    private HomeViewModel homeViewModel;
    private ActivityHomeBinding binding;

    private BroadcastReceiver networkChangeBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null
                    && activeNetwork.isConnectedOrConnecting();
            if(isConnected) homeViewModel.getData("Michael+jackson");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getData("Michael+jackson");
        observeTrackList();
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
        //it will throw an exception if receiver is not registered
        //I'm registering receiver only if internet connection is not available
        try{
            unregisterReceiver(networkChangeBroadcast);
        }catch (IllegalArgumentException ignored){}
    }

    private void observeTrackList(){
        homeViewModel.dataObserver.observe(this, new Observer<ApiResource<List<Track>>>() {
            @Override
            public void onChanged(@Nullable ApiResource<List<Track>> listApiResource) {
                if(listApiResource != null && listApiResource.status != Status.LOADING) {
                    if (listApiResource.res_status && listApiResource.data != null) {
                        binding.setCallback(HomeActivity.this);
                        binding.setData(listApiResource.data);
                        binding.setShow(false);
                    }else{
                        //checking type of error using VolleyError object
                        binding.setShow(true);
                        if (listApiResource.error instanceof TimeoutError || listApiResource.error instanceof NoConnectionError) {
                            Toast.makeText(HomeActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                            registerReceiver(networkChangeBroadcast, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                        } else{
                            Toast.makeText(HomeActivity.this, getString(R.string.message_api_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }


}
