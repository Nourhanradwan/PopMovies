package com.example.nour__000.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nour__000 on 10/10/2015.
 */
public class TrailerAdapter extends BaseAdapter {

    Context context;
    ArrayList<String[]> trailers;
    LayoutInflater inflater;

    public TrailerAdapter(Context context, ArrayList<String[]> trailers) {
        this.context = context;
        this.trailers = trailers;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.trailer_list, parent, false);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.trailer_item);
            holder.tvName.setText(trailers.get(position)[0]);
            holder.icPlay = (ImageView) convertView.findViewById(R.id.imageView);
            //holder.icPlay.setImageResource(R.id.);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }


    class ViewHolder {

        public ViewHolder() {
        }

        TextView tvName;
        ImageView icPlay;


    }
}
