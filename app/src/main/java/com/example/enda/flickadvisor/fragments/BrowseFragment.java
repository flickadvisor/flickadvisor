package com.example.enda.flickadvisor.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.adapters.BrowseMoviesRecyclerAdapter;
import com.example.enda.flickadvisor.adapters.BrowseTvSeriesRecyclerAdapter;
import com.example.enda.flickadvisor.services.api.TMDbServiceGenerator;
import com.example.enda.flickadvisor.interfaces.TMDbMovieApiService;
import com.example.enda.flickadvisor.interfaces.TMDbTvApiService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    private static final String TAG_FRAGMENT = "BROWSE_FRAGMENT";
    private View rootView;
    private TMDbMovieApiService movieApiService;
    private TMDbTvApiService tvApiService;

    public BrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_browse, container, false);

        movieApiService = TMDbServiceGenerator.createService(TMDbMovieApiService.class);
        tvApiService = TMDbServiceGenerator.createService(TMDbTvApiService.class);

        getPopularMovies();
        getMoviesInTheatres();
        getPopularTvShows();

        return rootView;
    }

    private void getMoviesInTheatres() {
        Calendar calOne = Calendar.getInstance();
        Calendar calTwo = Calendar.getInstance();

        calOne.add(Calendar.MONTH, -1);

        Call<JsonObject> call = movieApiService.releasedBetween(
                getFormattedDate(calOne.getTime()),
                getFormattedDate(calTwo.getTime()));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccess()) {
                    JsonArray array = response.body().getAsJsonArray("results");

                    List<JsonObject> list = new ArrayList<>();
                    for (JsonElement element : array) {
                        JsonObject obj = element.getAsJsonObject();
                        list.add(obj);
                    }

                    createNewMovieRecyclerView(R.id.in_theatres_list, list);
                } else {
                    // TODO: Write a method to handle errors
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG_FRAGMENT, t.getMessage());
                Toast.makeText(getContext(), "An error has occurred connecting to our servers.", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void getPopularMovies() {
        Call<JsonObject> call = movieApiService.sortBy("popularity.desc");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccess()) {
                    JsonArray array = response.body().getAsJsonArray("results");

                    List<JsonObject> list = new ArrayList<>();
                    for (JsonElement element : array) {
                        JsonObject obj = element.getAsJsonObject();
                        list.add(obj);
                    }

                    createNewMovieRecyclerView(R.id.popular_movies_list, list);
                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG_FRAGMENT, t.getMessage());
                Toast.makeText(getContext(), "An error has occurred connecting to our servers.", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void getPopularTvShows() {
        Call<JsonObject> call = tvApiService.sortBy("popularity.desc");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccess()) {
                    JsonArray array = response.body().getAsJsonArray("results");

                    List<JsonObject> list = new ArrayList<>();
                    for (JsonElement element : array) {
                        JsonObject obj = element.getAsJsonObject();
                        list.add(obj);
                    }

                    createNewTvSeriesRecyclerView(R.id.popular_tv_list, list);
                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG_FRAGMENT, t.getMessage());
                Toast.makeText(getContext(), "An error has occurred connecting to our servers.", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void createNewMovieRecyclerView(int recyclerView, List<JsonObject> list) {
        RecyclerView.Adapter mAdapter = new BrowseMoviesRecyclerAdapter(getContext(), list);

        RecyclerView mRecyclerView =
                (RecyclerView)rootView.findViewById(recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(newLayoutManager());

        mRecyclerView.setAdapter(mAdapter);
    }

    private void createNewTvSeriesRecyclerView(int recyclerView, List<JsonObject> list) {
        RecyclerView.Adapter mAdapter = new BrowseTvSeriesRecyclerAdapter(getContext(), list);

        RecyclerView mRecyclerView =
                (RecyclerView)rootView.findViewById(recyclerView);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(newLayoutManager());

        mRecyclerView.setAdapter(mAdapter);

    }

    private String getFormattedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(date);
    }

    private RecyclerView.LayoutManager newLayoutManager() {
        return new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    }

}
