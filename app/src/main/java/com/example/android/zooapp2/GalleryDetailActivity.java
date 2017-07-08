package com.example.android.zooapp2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GalleryDetailActivity extends AppCompatActivity {
    private TextView mCaption;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_detail);
        mCaption= (TextView) findViewById(R.id.caption);
        mImage= (ImageView) findViewById(R.id.image);

        if(getIntent()!=null){
            if(getIntent().getExtras().containsKey("extra_caption")){
                mCaption.setText(getIntent().getStringExtra("extra_caption"));
            }

            if(getIntent().getExtras().containsKey("extra_image")){
                Picasso.with(this).load(getIntent().getStringExtra("extra_image")).into(mImage);
            }
        }

    }
}
