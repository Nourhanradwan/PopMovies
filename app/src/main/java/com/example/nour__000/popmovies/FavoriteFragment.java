package com.example.nour__000.popmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        GridAdapter myAdapter;
        GridView gridview;
        gridview = (GridView) view.findViewById(R.id.GridView_Movie);


        DbSource dbSource = new DbSource(getActivity());
        List<Response.ResultsEntity> posters = dbSource.allFavs();
        myAdapter = new GridAdapter(getActivity(), posters);
        gridview.setAdapter(myAdapter);

        return view;
    }
}
