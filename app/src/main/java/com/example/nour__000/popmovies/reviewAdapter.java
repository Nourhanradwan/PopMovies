package com.example.nour__000.popmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nour__000 on 10/10/2015.
 */
public class reviewAdapter  extends BaseAdapter {

    Context context;
    ArrayList<String[]> reviews;
    LayoutInflater inflater;

    public reviewAdapter(Context context, ArrayList<String[]> reviews) {
        this.context = context;
        this.reviews = reviews;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return reviews.size();
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
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.review_list, parent, false);
            holder=new ViewHolder();
            holder.author=(TextView)convertView.findViewById(R.id.author_item);
            holder.content=(TextView)convertView.findViewById(R.id.content_item);
            convertView.setTag(holder);
        }else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.author.setText(reviews.get(position)[0]);
        holder.content.setText(reviews.get(position)[1]);
        return convertView;
    }

    class ViewHolder{

        public ViewHolder()
        {}
        TextView author;
        TextView content;

    }
}
