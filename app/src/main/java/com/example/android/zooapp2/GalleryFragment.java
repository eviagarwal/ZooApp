package com.example.android.zooapp2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Evi on 7/3/2017.
 */

public class GalleryFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private GalleryImageAdapter mAdapter;

    public static GalleryFragment getInstance(){
        GalleryFragment fragment= new GalleryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery_grid,container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView= (GridView) view.findViewById(R.id.grid);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter= new GalleryImageAdapter(getActivity(),0);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setDrawSelectorOnTop(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.gallery_feed)).addConverterFactory(GsonConverterFactory.create()).build();
        GalleryApiInterafce galleryApiInterface = retrofit.create(GalleryApiInterafce.class);
        Call<List<GalleryImage>> call = galleryApiInterface.getStreams();
        call.enqueue(new Callback<List<GalleryImage>>() {
            @Override
            public void onResponse(Call<List<GalleryImage>> call, Response<List<GalleryImage>> response) {
                if(response.isSuccessful()) {
                    List<GalleryImage> images= response.body();
                    for (GalleryImage image : images){
                        mAdapter.add(image);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<GalleryImage>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        GalleryImage image = (GalleryImage) parent.getItemAtPosition(position);
        Intent intent = new Intent(getActivity(), GalleryDetailActivity.class);
        intent.putExtra("extra_image", image.getImage());
        intent.putExtra("extra_caption", image.getCaption());
        startActivity(intent);

    }
}
