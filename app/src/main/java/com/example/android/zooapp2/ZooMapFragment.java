package com.example.android.zooapp2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Evi on 7/3/2017.
 */

public class ZooMapFragment extends Fragment implements OnMapReadyCallback {



    public static ZooMapFragment getInstance(){
        ZooMapFragment fragment= new ZooMapFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.google_map_fragment_layout, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment fragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);



    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {

        CameraPosition position= CameraPosition.builder()
                .target(new LatLng(39.7500, -104.9500))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setTrafficEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);

        MarkerOptions options= new MarkerOptions().position(new LatLng(39.7500, -104.9500));
        options.title("Zoo");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        googleMap.addMarker(options);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getString(R.string.pins_feed)).addConverterFactory(GsonConverterFactory.create()).build();
        PinsApiInterface pinsApiInterface= retrofit.create(PinsApiInterface.class);

        Call<List<Pin>> call= pinsApiInterface.getStreams();
        call.enqueue(new Callback<List<Pin>>() {
            @Override
            public void onResponse(Call<List<Pin>> call, Response<List<Pin>> response) {

                if(response.isSuccessful()){
                   List<Pin> pins= response.body();
                    for(Pin pin : pins){
                        MarkerOptions options = new MarkerOptions().position(new LatLng(pin.getLatitude(), pin.getLongitude()));
                        options.title(pin.getName());
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        googleMap.addMarker(options);

                    }

                }

            }

            @Override
            public void onFailure(Call<List<Pin>> call, Throwable t) {

            }
        });


    }
}
