package com.musicapp;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.musicapp.utils.BaseAdapter;
import com.musicapp.utils.ImageLoader;

import java.util.List;

/**
 * Created by chetan on 10/12/18.
 */

public class BindingAdapter {

    private static String TAG = "BindingAdapter";


    @android.databinding.BindingAdapter(value = {"app:adapter","app:listener","app:itemlist"}, requireAll=false)
    public static <E> void bindAdapter(RecyclerView recyclerView, int id, BaseAdapter.BaseInterface listener, List<E> list) {
        if(recyclerView.getAdapter()==null){
            BaseAdapter BA=new BaseAdapter(id,list,listener);
            recyclerView.setAdapter(BA);
        }else{
            ((BaseAdapter)recyclerView.getAdapter()).setListener(listener);
            ((BaseAdapter)recyclerView.getAdapter()).setData(list);
        }
    }


    @android.databinding.BindingAdapter("app:loadImg")
    public static void loadImg(ImageView imageView, String url) {
        ImageLoader imageLoader = ((MusicApp)imageView.getContext().getApplicationContext()).getImageLoader();
        imageLoader.displayImage(url,imageView);
    }


}
