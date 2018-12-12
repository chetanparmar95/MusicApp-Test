/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.musicapp.network;


import android.arch.lifecycle.LiveData;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.musicapp.model.ApiResource;
import com.musicapp.model.Status;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
 */
public class LiveDataCallAdapter<R> extends LiveData<ApiResource<R>> {
    private final Type responseType;
    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    public LiveData<ApiResource<R>> call(final int method , final String url) {
        return new LiveData<ApiResource<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    setValue(new ApiResource<R>(Status.LOADING,null,null));
                    JsonRequest<JSONObject> request = new JsonObjectRequest(method, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    ApiResource<R> result = new Gson().fromJson(String.valueOf(response),responseType);
                                    result.status= Status.SUCCESS;
                                    result.res_status = true;
                                    postValue(result);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            postValue(new ApiResource<R>(error));
                        }
                    });

                    MySingleton.getInstance().addToRequestQueue(request);
                }
            }
        };
    }
}
