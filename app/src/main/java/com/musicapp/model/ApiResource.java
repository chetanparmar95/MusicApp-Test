package com.musicapp.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chetan on 5/3/18.
 */

public class ApiResource<T> {

    @NonNull
    public transient Status status;

    @Nullable
    public final String msg;

    public Boolean res_status;

    @SerializedName("results")
    @Nullable
    public final T data;

    public ApiResource(Status s, String m, T data) {
        this.data = data;
        msg=m;
        status=s;
    }


    public ApiResource(Throwable error) {
        status=Status.ERROR;
        msg = "";
        data=null;
    }

    public ApiResource(ApiResource<T> response) {
        status=Status.SUCCESS;
        this.msg=response.msg;
        this.res_status=response.res_status;
        this.data=response.data;

    }
}
