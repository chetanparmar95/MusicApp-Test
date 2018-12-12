package com.musicapp.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.VolleyError;
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

    public VolleyError error;

    public ApiResource(Status s, String m, T data) {
        this.data = data;
        msg=m;
        status=s;
    }


    public ApiResource(Throwable error) {
        this.status=Status.ERROR;
        this.msg = "";
        this.data=null;
        this.res_status = false;
    }

    public ApiResource(VolleyError volleyError){
        this.status=Status.ERROR;
        this.msg = "";
        this.data=null;
        this.res_status = false;
        this.error = volleyError;
    }

    public ApiResource(ApiResource<T> response) {
        this.status=Status.SUCCESS;
        this.msg=response.msg;
        this.res_status=response.res_status;
        this.data=response.data;

    }
}
