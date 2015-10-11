package com.example.nour__000.popmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {

    }
//public  Task task = new Task ();
    private GridAdapter myAdapter;
    GridView gridview;


/*    @Override
    public void onStart() {
        super.onStart();

        //Start download
        String Sort_By = getPreferredLocation(getActivity());
        if(Sort_By=="favourite"){


        }else {
                Toast.makeText(getActivity(),
                        "You Don't have any Favourite",
                        Toast.LENGTH_SHORT)
                        .show();

            }
        }else {
            mGridData.clear();
            new Task().execute(Sort_By);
        }

    }

    void onLocationChanged( ) {
        updateMovies();
        //   getLoaderManager().restartLoader(FORECAST_LOADER, null, this);
    }
    private void updateMovies() {
        FetchMoviesTask weatherTask = new FetchMoviesTask();
        String location = getPreferredLocation(getActivity());
        weatherTask.execute(location);
    }

    public static String getPreferredLocation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_sort_key),
                context.getString(R.string.pref_sort_default));
    } */







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridview = (GridView) rootView.findViewById(R.id.GridView_Movie);
        View();



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Response.ResultsEntity gsonobj = new Response.ResultsEntity();
                gsonobj = (Response.ResultsEntity) myAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetail.class);
                intent.putExtra("movieId", gsonobj.getId());
                startActivity(intent);



            }
        });
        return rootView ;
    }

    @Override
    public void onResume() {
        super.onResume();
        View();
    }

    private void View() {

        Task task = new Task();

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = sharedPrefs.getString(getString(R.string.pref_units_key), getString(R.string.pref_most_popular));

        if (sortBy.equals(getString(R.string.pref_highest_rated))) {

            try {
                String Str1 = task.execute("http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=0df39df7b6499eab481883048862b53f").get();
                Response Response1 = new Response();
                Gson parser1 = new Gson();
                Response1 = parser1.fromJson(Str1, Response.class);

                myAdapter = new GridAdapter(getActivity(), Response1.getResults());
                gridview.setAdapter(myAdapter);


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        if (sortBy.equals(getString(R.string.pref_most_popular))) {
            if (task==null){

                Response.ResultsEntity Responseob = new Response.ResultsEntity();


            }
            else {
                try {
                    String Str2 = task.execute("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=0df39df7b6499eab481883048862b53f").get();
                    Response Responseobject = new Response();
                    Gson parser = new Gson();
                    Responseobject = parser.fromJson(Str2, Response.class);
                    myAdapter = new GridAdapter(getActivity(), Responseobject.getResults());
                    gridview.setAdapter(myAdapter);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
