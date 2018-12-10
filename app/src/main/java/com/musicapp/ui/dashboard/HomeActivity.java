package com.musicapp.ui.dashboard;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.musicapp.R;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }
}
