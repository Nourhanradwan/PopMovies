package com.example.nour__000.popmovies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {
    TrailerAdapter ListAdapter;
    reviewAdapter ListreviewAdapter;

    public MovieDetailFragment() {
    }

    ImageView imageview;
    TextView title;
    TextView overview;
    TextView rating;
    TextView release;
    TextView reviews;
    TextView trailer;

    Gson gson = new Gson();


    ListView listView1;
    ListView listView2;

    int movieID;
    String Overview;
    String Poster;
    String Release_date;
    String Title;
    double Rating;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_detail_movie, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            movieID = bundle.getInt("movieId");
            Overview = bundle.getString("overview");
            Release_date = bundle.getString("rselease_date");
            Poster = bundle.getString("poster");
            Title = bundle.getString("title");
            Rating = bundle.getDouble("Rating");
        } else {
            Bundle getArgus = getArguments();
            Response.ResultsEntity movie = getArgus.getParcelable("movieId");
            movieID = movie.getId();
            Overview = movie.getOverview();
            Release_date = movie.getRelease_date();
            Poster = movie.getPoster_path();
            Title = movie.getTitle();
            Rating = movie.getVote_average();

        }

//        Bundle getIntentExtra = getActivity().getIntent().getExtras();
//        if (getIntentExtra != null) {
//            gsonobject = getIntentExtra.getParcelable("movieId");
//        } else {
//            Bundle getArgus = getArguments();
//            gsonobject = getArgus.getParcelable("movieId");
//        }

        final Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                MovieDB movieDB = new MovieDB(getActivity());
//                movieDB.insertRow(movieID, Poster, Release_date,Title, Rating,Rating, 1);
//                movieDB.setTrailersById(id, names, keys);
//                movieDB.setReviewsById(id, author, contents);
                DbSource dbsource = new DbSource(getActivity());
                boolean check = dbsource.addmovie(movieID, Title, Release_date, Overview, (float) Rating, Poster);
                Toast.makeText(getActivity(), check + "", Toast.LENGTH_LONG).show();
                ;


            }


        });


        int movieId = getActivity().getIntent().getExtras().getInt("movieId");
        Response object = (Response) getActivity().getIntent().getSerializableExtra("myobj");


        Task task = new Task();
        try {
            String resultStr = task.execute("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=f5a335aed31b96b3b82ccc2101bebc07").get();
            Response.ResultsEntity gsonobject = new Response.ResultsEntity();
            Gson parser = new Gson();
            gsonobject = parser.fromJson(resultStr, Response.ResultsEntity.class);

            title = (TextView) rootView.findViewById(R.id.title);
            title.setText(gsonobject.getTitle());

            overview = (TextView) rootView.findViewById(R.id.overview);
            overview.setText(gsonobject.getOverview());

            release = (TextView) rootView.findViewById(R.id.release_date);
            release.setText(gsonobject.getRelease_date());

            rating = (TextView) rootView.findViewById(R.id.rating);
            rating.setText(gsonobject.getVote_average() + "");

            imageview = (ImageView) rootView.findViewById(R.id.imageviewditals);
            String imageurl = gsonobject.getPoster_path();

            listView1 = (ListView) rootView.findViewById(R.id.listTrailer);

            listView2 = (ListView) rootView.findViewById(R.id.listReview);

//
            Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w185" + imageurl).into(imageview);
            //put the trailers url inside execute


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        FetchTrailer trailer = new FetchTrailer();
        trailer.execute();

        FetchReview review = new FetchReview();
        review.execute();


        return rootView;
    }


    private void watchYoutubeVideo(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        }
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    public class FetchTrailer extends AsyncTask<Void, Void, Void> {
        public ArrayList<String[]> PopMovies = new ArrayList<String[]>();
        private final String Tag = FetchTrailer.class.getSimpleName();

        ArrayList<String[]> DetailList;

        ArrayList<String[]> FetchDataFromJason(final String MovieJson) throws JSONException {
            final String Mve_result = "results";
            final String TrailerName = "name";
            final String TrailerKey = "key";
            final String baseURL = "https://www.youtube.com/watch?v=";


            JSONObject MovieJason = new JSONObject(MovieJson);
            JSONArray MovieArray = MovieJason.getJSONArray(Mve_result);

            DetailList = new ArrayList<>();
            for (int i = 0; i < MovieArray.length(); i++) {
                String MovieHashMap[] = new String[2];
                JSONObject Mve_object = MovieArray.getJSONObject(i);
                final String Name = Mve_object.getString(TrailerName);
                final String Key = Mve_object.getString(TrailerKey);

                String r[] = new String[2];
                MovieHashMap[0] = Name;
                MovieHashMap[1] = Key;

                r[0] = MovieHashMap[0];
                r[1] = MovieHashMap[1];

                DetailList.add(r);

            }

            return DetailList;

        }

        @Override
        protected Void doInBackground(Void... params) {
          /*  if (params.length == 0) {
                return null;
            }*/
            HttpURLConnection UrlConnection = null;
            BufferedReader reader = null;
            String MovieJsonStr = null;
            String Api_Key = "0df39df7b6499eab481883048862b53f";


            try {
                final String Movie_URL = "http://api.themoviedb.org/3/movie/" + movieID + "/videos?";
                final String ApI_Key = "api_key";

                //passing URL which fetching Our Data From it
                Uri BuildURi = Uri.parse(Movie_URL).buildUpon()
                        .appendQueryParameter(ApI_Key, Api_Key)
                        .build();

                URL url = new URL(BuildURi.toString());
                Log.v(Tag, "Built URI " + BuildURi.toString());

                //Making Connection
                UrlConnection = (HttpURLConnection) url.openConnection();
                UrlConnection.setRequestMethod("GET");
                UrlConnection.connect();

                InputStream inputStream = UrlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                MovieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(Tag, "Error ", e);

                return null;
            } finally {
                if (UrlConnection != null) {
                    UrlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(Tag, "Error closing stream", e);
                    }
                }
            }

            try {
                PopMovies = FetchDataFromJason(MovieJsonStr);
            } catch (JSONException e) {
                Log.e(Tag, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("size", DetailList.size() + "");
            if (DetailList == null || DetailList.size() == 0) {
            } else {
                ListAdapter = new TrailerAdapter(getActivity(), PopMovies);
                listView1.setAdapter(ListAdapter);
                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //pass object of clicked item
                        watchYoutubeVideo(PopMovies.get(position)[1]);
                    }
                });
                setListViewHeightBasedOnChildren(listView1);
            }
        }


    }


    //review

    public class FetchReview extends AsyncTask<Void, Void, Void> {
        public ArrayList<String[]> PopMovies = new ArrayList<String[]>();
        private final String Tag = FetchReview.class.getSimpleName();

        ArrayList<String[]> ReviewList;

        ArrayList<String[]> FetchDataFromJason(final String MovieJson) throws JSONException {
            final String Mve_result = "results";
            final String movieAuhor = "author";
            final String movieContent = "content";


            JSONObject MovieJason = new JSONObject(MovieJson);
            JSONArray MovieArray = MovieJason.getJSONArray(Mve_result);

            String content;
            String author;
            ReviewList = new ArrayList<>();
            for (int i = 0; i < MovieArray.length(); i++) {
                String review[] = new String[2];
                JSONObject movieOverview = MovieArray.getJSONObject(i);


                content = movieOverview.getString(movieContent);
                author = movieOverview.getString(movieAuhor);
                review[0] = content;
                review[1] = author;


                ReviewList.add(review);
            }

            return ReviewList;

        }


        @Override
        protected Void doInBackground(Void... params) {

            HttpURLConnection UrlConnection = null;
            BufferedReader reader = null;
            String MovieJsonStr = null;
            String Api_Key = "0df39df7b6499eab481883048862b53f";


            try {
                final String Movie_URL = "http://api.themoviedb.org/3/movie/" + movieID + "/reviews?";
                final String ApI_Key = "api_key";

                //passing URL which fetching Our Data From it
                Uri BuildURi = Uri.parse(Movie_URL).buildUpon()
                        .appendQueryParameter(ApI_Key, Api_Key)
                        .build();

                URL url = new URL(BuildURi.toString());
                Log.v(Tag, "Built URI " + BuildURi.toString());

                //Making Connection
                UrlConnection = (HttpURLConnection) url.openConnection();
                UrlConnection.setRequestMethod("GET");
                UrlConnection.connect();

                InputStream inputStream = UrlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {

                    return null;
                }
                MovieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(Tag, "Error ", e);

                return null;
            } finally {
                if (UrlConnection != null) {
                    UrlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(Tag, "Error closing stream", e);
                    }
                }
            }

            try {
                PopMovies = FetchDataFromJason(MovieJsonStr);
            } catch (JSONException e) {
                Log.e(Tag, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //   Log.d("size", ReviewList.size() + "");
            if (PopMovies == null || PopMovies.size() == 0) {
            } else {
                ListreviewAdapter = new reviewAdapter(getActivity(), PopMovies);
                listView2.setAdapter(ListreviewAdapter);
                setListViewHeightBasedOnChildren(listView2);
            }
        }


    }
}


