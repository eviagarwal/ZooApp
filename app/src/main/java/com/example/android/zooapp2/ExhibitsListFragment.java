package com.example.android.zooapp2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Evi on 7/3/2017.
 */

public class ExhibitsListFragment extends ListFragment{
    private ExhibitsAdapter mAdapter;

    public static ExhibitsListFragment getInstance(){
        ExhibitsListFragment fragment = new ExhibitsListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(false);
        getListView().setPadding(16, 16, 16, 16);
        getListView().setDivider(new ColorDrawable(Color.TRANSPARENT));
        getListView().setDividerHeight(16);
        getListView().setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        getListView().setClipToPadding(true);
        mAdapter= new ExhibitsAdapter(getActivity(), 0);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.exhibits_feed)).addConverterFactory(GsonConverterFactory.create()).build();
        AnimalApiInterface animalApiInterface= retrofit.create(AnimalApiInterface.class);

      Call<List<Animal>> call= animalApiInterface.getStreams();
        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if(response.isSuccessful()){
                    List<Animal> animals= response.body();
                    for(Animal animal: animals){
                        mAdapter.add(animal);
                    }
                    mAdapter.notifyDataSetChanged();
                    setListAdapter(mAdapter);
                    setListShown(true);
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent= new Intent(getActivity(), ExhibitDetailActivity.class);
        intent.putExtra("extra_animal", mAdapter.getItem(position));
        startActivity(intent);
    }
}
