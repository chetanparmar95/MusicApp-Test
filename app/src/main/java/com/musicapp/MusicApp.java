package com.musicapp;

import android.app.Application;

import com.musicapp.network.MySingleton;
import com.musicapp.utils.ImageLoader;

/**
 * Created by Chetan on 8/8/18.
 */

public class MusicApp extends Application {

    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        imageLoader = new ImageLoader(this);
        MySingleton.getInstance(this);
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
