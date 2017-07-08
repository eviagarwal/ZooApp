package com.example.android.zooapp2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Evi on 7/5/2017.
 */

public class ExhibitsAdapter extends ArrayAdapter<Animal> {
    public ExhibitsAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView== null){
            holder= new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.exhibits_list_item, parent, false);
            holder.progress= (ProgressBar) convertView.findViewById(R.id.progress);
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.species= (TextView) convertView.findViewById(R.id.species);
            holder.thumbnail= (ImageView) convertView.findViewById(R.id.thumbnail);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.thumbnail.setVisibility(View.GONE);
        holder.progress.setVisibility(View.VISIBLE);
        holder.name.setText(getItem(position).getName());
        holder.species.setText(getItem(position).getSpecies());
       final ViewHolder tmp = holder;
        Picasso.with(getContext()).load(getItem(position).getThumbnail()).into(holder.thumbnail, new Callback() {
            @Override
            public void onSuccess() {
                tmp.progress.setVisibility(View.GONE);
                tmp.thumbnail.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                tmp.progress.setVisibility(View.GONE);

            }
        });
        return convertView;

    }
    class ViewHolder{
        ProgressBar progress;
        ImageView thumbnail;
        TextView name;
        TextView species;
    }
}
