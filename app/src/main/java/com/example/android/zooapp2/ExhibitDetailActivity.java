package com.example.android.zooapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ExhibitDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exhibit_detail);
        Animal animal= getIntent().getExtras().getParcelable("extra_animal");

        TextView species= (TextView) findViewById(R.id.species);
        TextView description= (TextView) findViewById(R.id.description);
        ImageView image= (ImageView) findViewById(R.id.image);
        species.setText(animal.getSpecies());
        description.setText(animal.getDescription());
        Picasso.with(this).load(animal.getImage()).into(image);
    }
}
