package com.example.enda.flickadvisor.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.adapters.BrowseRecyclerAdapter;
import com.example.enda.flickadvisor.services.TmdbServiceGenerator;
import com.example.enda.flickadvisor.services.api.TmdbMovieService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    private static final String TAG_FRAGMENT = "BROWSE_FRAGMENT";
    private View rootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public BrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_browse, container, false);

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.browse_recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(mLayoutManager);

        TmdbMovieService movieApiService = TmdbServiceGenerator.createService(TmdbMovieService.class);

        Call<JsonObject> call = movieApiService.discoverMovies("popularity.desc");
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
                    mAdapter = new BrowseRecyclerAdapter(getContext(), list);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG_FRAGMENT, t.getMessage());
            }
        });
        return rootView;
    }

}
