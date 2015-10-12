package com.example.nour__000.popmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements MainActivityFragment.Callback {

    boolean twopane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.container) != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MovieDetailFragment());
            twopane = true;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifmiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }
        if (id == R.id.favorites) {
            startActivity(new Intent(this, Favorite.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedItem(Response.ResultsEntity movie) {

        if (!twopane) {
            Intent intent = new Intent(getApplicationContext(), MovieDetail.class);


            intent.putExtra("movieId", movie.getId());
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("overview", movie.getOverview());
            intent.putExtra("release_date", movie.getRelease_date());
            intent.putExtra("poster", movie.getPoster_path());
            intent.putExtra("Rating", movie.getVote_average());
            startActivity(intent);
        } else {
            Bundle arguments = new Bundle();
            arguments.putParcelable("movieId", movie);
            MovieDetailFragment detail = new MovieDetailFragment();
            detail.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, detail).commit();

        }
    }
}
