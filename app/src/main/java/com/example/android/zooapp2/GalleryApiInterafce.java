package com.example.android.zooapp2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Evi on 7/5/2017.
 */

public interface GalleryApiInterafce {
    @GET("gallery.json")
    Call<List<GalleryImage>> getStreams();
}
