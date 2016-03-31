package com.example.enda.flickadvisor.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.activities.MovieActivity;
import com.example.enda.flickadvisor.interfaces.TMDbMovieApiService;
import com.example.enda.flickadvisor.models.User;
import com.example.enda.flickadvisor.services.UserRealmService;
import com.example.enda.flickadvisor.services.api.TMDbServiceGenerator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscoverFragment extends Fragment {

    private static String TAG_CONTEXT;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TAG_CONTEXT = getContext().toString();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        User currentUser = null;
        if (UserRealmService.isLoggedIn()) {
            currentUser = UserRealmService.getCurrentUser();
        }

        final String mLanguage = currentUser == null ? null : currentUser.getLanguage();

        // bind buttons from view
        Button mRandomSuggestionButton = (Button) v.findViewById(R.id.button_random_suggestion);
        Button mAdvancedSearchButton = (Button) v.findViewById(R.id.button_advanced_search);

        final TMDbMovieApiService movieApiService = TMDbServiceGenerator.createService(TMDbMovieApiService.class);

        // set button click listeners
        mRandomSuggestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<JsonObject> call = movieApiService.discoverMovies(null, Locale.getDefault().getLanguage(), 7, "vote_average.desc", 100);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (response.isSuccess()) {
                            JsonArray array = response.body().getAsJsonArray("results");

                            List<JsonObject> results = new ArrayList<>();
                            for (JsonElement element : array) {
                                JsonObject obj = element.getAsJsonObject();
                                results.add(obj);
                            }

                            selectRandomMovieFromList(results);
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                    }
                });
            }
        });

        mAdvancedSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void selectRandomMovieFromList(List<JsonObject> jsonObjectList) {
        JsonObject random = jsonObjectList.get(new Random().nextInt(jsonObjectList.size()));

        if (random != null) {
            Long movieId = random.get("id").getAsLong();
            Intent intent = new Intent(getContext(), MovieActivity.class);
            intent.putExtra("movieId", movieId);
            startActivity(intent);
        }
    }

}
