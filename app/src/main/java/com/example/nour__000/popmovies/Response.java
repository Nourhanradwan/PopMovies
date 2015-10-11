package com.example.nour__000.popmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nour__000 on 9/30/2015.
 */
public class Response implements Serializable  {




    private int page;
    private int total_pages;
    private int total_results;
    private List<ResultsEntity> results;

    public void setPage(int page) {
        this.page = page;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity implements Parcelable {
        /**
         * adult : false
         * backdrop_path : /t5KONotASgVKq4N19RyhIthWOPG.jpg
         * genre_ids : [28,12,878,53]
         * id : 135397
         * original_language : en
         * original_title : Jurassic World
         * overview : Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.
         * release_date : 2015-06-12
         * poster_path : /uXZYawqUsChGSj54wcuBtEdUJbh.jpg
         * popularity : 48.893964
         * title : Jurassic World
         * video : false
         * vote_average : 7.0
         * vote_count : 2379
         */

        private boolean adult;
        private String backdrop_path;
        private int id;
        private String original_language;
        private String original_title;
        private String overview;
        private String release_date;
        private String poster_path;
        private double popularity;
        private String title;
        private boolean video;
        private double vote_average;
        private int vote_count;
        private List<Integer> genre_ids;

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        public boolean getAdult() {
            return adult;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public int getId() {
            return id;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public String getOverview() {
            return overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public double getPopularity() {
            return popularity;
        }

        public String getTitle() {
            return title;
        }

        public boolean getVideo() {
            return video;
        }

        public double getVote_average() {
            return vote_average;
        }

        public int getVote_count() {
            return vote_count;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int i) {
            dest.writeString(backdrop_path);
            dest.writeInt(id);
            dest.writeString(original_language);
            dest.writeString(original_title);
            dest.writeString(overview);
            dest.writeString(release_date);
            dest.writeString(poster_path);
            dest.writeDouble(popularity);
            dest.writeString(title);
            dest.writeDouble(vote_average);
            dest.writeInt(vote_count);

        }
    }
}
