package com.musicapp;

import android.app.Application;
import android.content.Context;

import com.musicapp.network.MySingleton;
import com.musicapp.utils.ImageLoader;

/**
 * Created by Chetan on 8/8/18.
 */

public class MusicApp extends Application {

    private static MusicApp mInstance = new MusicApp();
    private static Context mAppContext;

    private ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        mAppContext=this.getApplicationContext();
        imageLoader = new ImageLoader(this);
        MySingleton.getInstance(mAppContext);
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }


    public static MusicApp getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }


}
