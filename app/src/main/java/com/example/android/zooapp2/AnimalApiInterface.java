package com.example.android.zooapp2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Evi on 7/4/2017.
 */

public interface AnimalApiInterface {
    @GET("exhibits.json")
     Call<List<Animal>> getStreams();

}
