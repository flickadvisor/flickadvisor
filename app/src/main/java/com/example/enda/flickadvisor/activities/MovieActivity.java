package com.example.enda.flickadvisor.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.interfaces.MovieApiService;
import com.example.enda.flickadvisor.interfaces.TMDbMovieApiService;
import com.example.enda.flickadvisor.models.Genre;
import com.example.enda.flickadvisor.models.Movie;
import com.example.enda.flickadvisor.models.MovieReview;
import com.example.enda.flickadvisor.models.UserTbl;
import com.example.enda.flickadvisor.models.UserMovie;
import com.example.enda.flickadvisor.services.UserRealmService;
import com.example.enda.flickadvisor.services.api.ApiServiceGenerator;
import com.example.enda.flickadvisor.services.api.TMDbServiceGenerator;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {

    private static final String TAG_ACTIVITY = "MOVIE_ACTIVITY";
    private Movie movie;
    private UserMovie mUserMovie;
    private UserTbl user;
    private Toolbar toolbar;
    // api service builders
    private TMDbMovieApiService tmbdMovieApiService;
    private MovieApiService movieApiService;
    private boolean relationshipExists = false;

    // view bindings
    @Bind(R.id.movie_progress) View mProgressView;
    @Bind(R.id.movie_content) ScrollView mMovieContentView;
    @Bind(R.id.poster) ImageView mPosterView;
    @Bind(R.id.title) TextView mTitleView;
    @Bind(R.id.subTitle) TextView mSubtitleView;
    @Bind(R.id.movie_language) TextView mLanguageTextView;
    @Bind(R.id.movie_runtime) TextView mRuntimeTextView;
    @Bind(R.id.stars_container) LinearLayout mStarsContainer;
    @Bind(R.id.movie_overview) TextView mOverviewTextView;
    @Bind(R.id.fabRate) FloatingActionButton mRateButton;
    @Bind(R.id.fabWatchLater) FloatingActionButton mWatchLaterButton;
    @Bind(R.id.fabSeenIt) FloatingActionButton mSeenItButton;
    @Bind(R.id.fabFavourite) FloatingActionButton mFavouriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        user = UserRealmService.getCurrentUser();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // show progress indicator until all elements are loaded to the view
        showProgress(true);
        Bundle args = getIntent().getExtras();
        long movieId = args.getLong("movieId", 0L);

        tmbdMovieApiService =
                TMDbServiceGenerator.createService(TMDbMovieApiService.class);
        movieApiService =
                ApiServiceGenerator.createService(MovieApiService.class);

        if (movieId != 0L) {
            getMovieWithId(movieId);
        }

    }

    private void getMovieReviews(long movieId) {
        Call<RealmList<MovieReview>> call = movieApiService.getMovieReviews(movieId);
        call.enqueue(new Callback<RealmList<MovieReview>>() {
            @Override
            public void onResponse(Call<RealmList<MovieReview>> call, Response<RealmList<MovieReview>> response) {
                if (response.isSuccess()) {
                    movie.setReviews(response.body());
                }

                if (movie.getReviews() != null && movie.getReviews().size() > 9) {
                    addRatingStarsToLayout(getAverageRating(movie.getReviews()));
                } else {
                    addRatingStarsToLayout(movie.getVoteAverage() / 2);
                }
            }

            @Override
            public void onFailure(Call<RealmList<MovieReview>> call, Throwable t) {
                addRatingStarsToLayout(movie.getVoteAverage() / 2);
                Log.d(TAG_ACTIVITY, t.getMessage());
            }
        });
    }

    private void getUserMovieRelationship(long movieId, long userId) {
        Call<UserMovie> call = movieApiService.getUserMovie(movieId, userId);
        call.enqueue(new Callback<UserMovie>() {
            @Override
            public void onResponse(Call<UserMovie> call, Response<UserMovie> response) {
                if (response.isSuccess()) {
                    mUserMovie = response.body();
                    relationshipExists = true;
                } else {
                    mUserMovie = new UserMovie(movie.getId(), user.getId()); // no relationship found, create new
                }
                setFabButtonSelectedState(mFavouriteButton, mUserMovie.isFavourite());
                setFabButtonSelectedState(mSeenItButton, mUserMovie.isOnWatchedList());
                setFabButtonSelectedState(mWatchLaterButton, mUserMovie.isOnWatchList());
            }

            @Override
            public void onFailure(Call<UserMovie> call, Throwable t) {
                Log.d(TAG_ACTIVITY, t.getMessage());
            }
        });
    }

    private void setFabButtonSelectedState(FloatingActionButton fab, boolean isSelected) {
        int id = fab.getId();
        int iconId = 0;
        Drawable drawable;
        String labelText = null;

        switch (id) {
            case R.id.fabFavourite:
                iconId = isSelected ? R.drawable.ic_favorite : R.drawable.ic_favorite_outline;
                labelText = isSelected ? "Unfavourite" : "Favourite";
                break;
            case R.id.fabSeenIt:
                iconId = isSelected ? R.drawable.ic_close : R.drawable.ic_done;
                labelText = isSelected ? "I've not seen it" : "I've seen it";
                break;
            case R.id.fabWatchLater:
                iconId = isSelected ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_outline;
                labelText = isSelected ? "Saved" : "Save";
                break;
        }

        drawable = ContextCompat.getDrawable(this, iconId);
        fab.setImageDrawable(drawable);
        fab.setLabelText(labelText);
    }

    private void addRatingStarsToLayout(float averageRating) {
        for (float i = 0; i < averageRating; i++) {
            Drawable drawable;
            if (averageRating - i >= 0.25 && averageRating - i <= 0.75) {
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_star_half_24dp);
            } else {
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_star_24dp);
            }

            ImageView image = new ImageView(this);
            image.setImageDrawable(drawable);
            mStarsContainer.addView(image);
        }
    }

    private float getAverageRating(RealmList<MovieReview> reviews) {
        float sum = 0F;
        for (MovieReview review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }

    private void bindDataToView(Movie movie) {
        toolbar.setTitle(movie.getTitle());

        loadImageToPoster(movie.getPosterPath());

        Calendar c = new GregorianCalendar();
        c.setTime(movie.getReleaseDate());
        String titleText = String.format("%s (%s)", movie.getTitle(), c.get(Calendar.YEAR));
        mTitleView.setText(titleText);

        String subTitleText;
        if (movie.getGenres().size() > 1) {
            subTitleText = String.format(
                    "%s & %s", movie.getGenres().get(0)
                            .getName(), movie.getGenres()
                            .get(1).getName());
        } else {
            subTitleText = movie.getGenres().get(0).getName();
        }
        mSubtitleView.setText(subTitleText);
        mLanguageTextView.setText(movie.getLanguage());
        mRuntimeTextView.setText(String.format("%s minutes", movie.getRuntime()));
        mOverviewTextView.setText(movie.getOverview());

        showProgress(false);

    }

    private void loadImageToPoster(String posterPath) {
        Picasso.with(this)
                .load(posterPath)
                .fit()
                .into(mPosterView);
    }

    private void getMovieWithId(long movieId) {
        Call<JsonObject> call = tmbdMovieApiService.getMovieWithId(movieId);
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
                    getMovieReviews(movie.getId());
                    bindDataToView(movie);

                    if (UserRealmService.isLoggedIn()) {
                        getUserMovieRelationship(movie.getId(), user.getId());
                    }
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

    @OnClick(R.id.fabFavourite)
    public void onClickFavourite() {
        if (UserRealmService.isLoggedIn()) {
            boolean newState = !mUserMovie.isFavourite();

            mUserMovie.setIsFavourite(newState);

            setFabButtonSelectedState(mFavouriteButton, newState);

            if (newState) {
                setFabButtonSelectedState(mSeenItButton, true);
                mUserMovie.setIsOnWatchedList(true);
            }

            updateUserMovie(mUserMovie);

        } else {
            signInToast();
        }
    }

    private void signInToast() {
        Toast.makeText(this, "You must be signed in to do this.", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fabRate)
    public void onClickRate() {
    }

    @OnClick(R.id.fabSeenIt)
    public void onClickAddToSeenIt() {
        if (UserRealmService.isLoggedIn()) {
            boolean newState = !mUserMovie.isOnWatchedList();

            mUserMovie.setIsOnWatchedList(newState);
            setFabButtonSelectedState(mSeenItButton, newState);

            if (!newState) {
                mUserMovie.setIsFavourite(false);
                setFabButtonSelectedState(mFavouriteButton, false);
            }

            updateUserMovie(mUserMovie);

        } else {
            signInToast();
        }
    }

    @OnClick(R.id.fabWatchLater)
    public void onClickAddToWatchLater() {
        if (UserRealmService.isLoggedIn()) {
            boolean newState = !mUserMovie.isOnWatchList();

            mUserMovie.setIsOnWatchList(newState);
            setFabButtonSelectedState(mWatchLaterButton, newState);

            updateUserMovie(mUserMovie);
        } else {
            signInToast();
        }
    }

    private void updateUserMovie(final UserMovie userMovie) {
        if (relationshipExists) { // update
            Call<UserMovie> call = movieApiService.updateUserMovie(userMovie);
            call.enqueue(new Callback<UserMovie>() {
                @Override
                public void onResponse(Call<UserMovie> call, Response<UserMovie> response) {
                    if (response.isSuccess()) {
                        mUserMovie = response.body();
                    }
                }

                @Override
                public void onFailure(Call<UserMovie> call, Throwable t) {
                    Log.e(TAG_ACTIVITY, t.getLocalizedMessage());
                }
            });
        } else {
            Call<UserMovie> call = movieApiService.createUserMovie(userMovie);
            call.enqueue(new Callback<UserMovie>() {
                @Override
                public void onResponse(Call<UserMovie> call, Response<UserMovie> response) {
                    if (response.isSuccess()) {
                        mUserMovie = response.body();
                    }
                    Log.d(TAG_ACTIVITY, response.message());
                }

                @Override
                public void onFailure(Call<UserMovie> call, Throwable t) {
                    Log.e(TAG_ACTIVITY, t.getLocalizedMessage());
                }
            });
        }
    }
}
