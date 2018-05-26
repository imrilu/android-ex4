package com.postpc.imri.ex4;

import android.media.Image;
import com.google.gson.JsonElement;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

public interface MyService {
    @Headers("Authorization: Client-ID " + Config.CLIENT_ID)

    @GET("/album/{albumHash}")
    void getAlbum(@Path("albumHash") String albumHash, Callback<JsonElement> cb);
}