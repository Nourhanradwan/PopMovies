package com.example.nour__000.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nour__000 on 9/18/2015.
 */
public class GridAdapter extends BaseAdapter {

    private List<Response.ResultsEntity> item;
    private Context context;
    private LayoutInflater inflater;

    public GridAdapter(Context context, List<Response.ResultsEntity> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {

        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.movie_item_layout, viewGroup, false);
        }
        Response.ResultsEntity opject = (Response.ResultsEntity) getItem(position);
        ImageView img = (ImageView) view.findViewById(R.id.image);
        String imageurl = opject.getPoster_path();


        Picasso.with(context).load("https://image.tmdb.org/t/p/w185" + imageurl).resize(185, 250).into(img);

        return view;
    }


}


