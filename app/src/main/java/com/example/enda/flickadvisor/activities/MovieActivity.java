package com.example.enda.flickadvisor.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.models.Genre;
import com.example.enda.flickadvisor.models.Movie;
import com.example.enda.flickadvisor.services.TMDbServiceGenerator;
import com.example.enda.flickadvisor.services.interfaces.TMDbMovieApiService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import icepick.Icepick;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {

    private static final String TAG_ACTIVITY = "ACTIVITY_MOVIE";
    private Movie movie;
    private Toolbar toolbar;

    // view items
    @Bind(R.id.poster) ImageView mPosterView;
    @Bind(R.id.title) TextView mTitleView;
    @Bind(R.id.movie_progress) View mProgressView;
    @Bind(R.id.movie_content) ScrollView mMovieContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Icepick.restoreInstanceState(this, savedInstanceState);
        }

        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // show progress indicator until all elements are loaded to the view
        showProgress(true);
        Bundle args = getIntent().getExtras();
        long movieId = args.getLong("movieId", 0L);

        if (movieId != 0L)
            getMovieWithId(movieId);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void setupViewFromModel(Movie movie) {
        toolbar.setTitle(movie.getTitle());

        loadImageToPoster(movie.getPosterPath());

        Calendar c = new GregorianCalendar();
        c.setTime(movie.getReleaseDate());
        String titleText = String.format("%s (%s)", movie.getTitle(), c.get(Calendar.YEAR));
        mTitleView.setText(titleText);

        showProgress(false);

    }

    private void loadImageToPoster(String posterPath) {
        Picasso.with(this)
                .load(posterPath)
                .fit()
                .into(mPosterView);
    }

    private void getMovieWithId(long movieId) {
        TMDbMovieApiService movieApiService =
                TMDbServiceGenerator.createService(TMDbMovieApiService.class);

        Call<JsonObject> call = movieApiService.getMovieWithId(movieId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccess()) {
                    JsonObject json = response.body();

                    movie = new Movie(json);
                    RealmList<Genre> genres = new RealmList<>();
                    for (JsonElement obj : json.getAsJsonArray("genres")) {
                        genres.add(new Genre(obj.getAsJsonObject()));
                    }
                    movie.setGenres(genres);

                    setupViewFromModel(movie);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG_ACTIVITY, t.getMessage());
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mMovieContentView.setVisibility(show ? View.GONE : View.VISIBLE);
            mMovieContentView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMovieContentView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mMovieContentView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
