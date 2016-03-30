package com.example.enda.flickadvisor.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.fragments.ReviewDialogFragment;
import com.example.enda.flickadvisor.interfaces.MovieApiService;
import com.example.enda.flickadvisor.interfaces.TMDbMovieApiService;
import com.example.enda.flickadvisor.interfaces.UserApiService;
import com.example.enda.flickadvisor.models.Genre;
import com.example.enda.flickadvisor.models.Movie;
import com.example.enda.flickadvisor.models.MovieReview;
import com.example.enda.flickadvisor.models.UserMovie;
import com.example.enda.flickadvisor.models.UserTbl;
import com.example.enda.flickadvisor.services.UserRealmService;
import com.example.enda.flickadvisor.services.api.ApiServiceGenerator;
import com.example.enda.flickadvisor.services.api.TMDbServiceGenerator;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity implements ReviewDialogFragment.OnAddReviewListener {

    //region Globals
    private String TAG_ACTIVITY = this.toString();
    private static Movie movie;
    private static UserMovie mUserMovie;
    private UserTbl user;
    private static boolean relationshipExists = false;
    private Toolbar toolbar;
    private ReviewDialogFragment reviewDialog;

    // api service builders
    private TMDbMovieApiService tmbdMovieApiService;
    private MovieApiService movieApiService;
    private UserApiService userApiService;

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
    @Bind(R.id.openReviewsList) Button mOpenReviews;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        user = UserRealmService.getCurrentUser();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tmbdMovieApiService
                = TMDbServiceGenerator.createService(TMDbMovieApiService.class);
        movieApiService
                = ApiServiceGenerator.createService(MovieApiService.class);
        userApiService
                = ApiServiceGenerator.createService(UserApiService.class);

        // show progress indicator until all elements are loaded to the view
        showProgress(true);

        if (getIntent().getExtras() != null) {
            Bundle args = getIntent().getExtras();
            long movieId = args.getLong("movieId", 0L);

            if (movieId != 0L) {
                getMovieWithId(movieId);
            }
        } else if (movie != null) {
            bindDataToView(movie);
            bindStarsToView();
            setFabButtonStates();
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

                bindStarsToView();
            }

            @Override
            public void onFailure(Call<RealmList<MovieReview>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                Log.d(TAG_ACTIVITY, "Failed to get movies reviews.");
            }
        });
    }

    private void bindStarsToView() {
        if (movie.getReviews() != null && movie.getReviews().size() >= 10) {
            addRatingStarsToLayout(getAverageRating(movie.getReviews()));
        } else {
            addRatingStarsToLayout(movie.getVoteAverage());
        }
    }

    private void getUserMovieRelationship(long movieId, long userId) {
        Call<UserMovie> call = userApiService.getUserMovie(userId, movieId);
        call.enqueue(new Callback<UserMovie>() {
            @Override
            public void onResponse(Call<UserMovie> call, Response<UserMovie> response) {
                if (response.isSuccess()) {
                    mUserMovie = response.body();
                    relationshipExists = true;
                } else if (response.code() == 404) {
                    mUserMovie = new UserMovie(movie.getId(), user); // no relationship found, create new
                }
                setFabButtonStates();
            }

            @Override
            public void onFailure(Call<UserMovie> call, Throwable t) {
            }
        });
    }

    private void setFabButtonStates() {
        setFabButtonSelectedState(mFavouriteButton, mUserMovie.isFavourite());
        setFabButtonSelectedState(mSeenItButton, mUserMovie.isOnWatchedList());
        setFabButtonSelectedState(mWatchLaterButton, mUserMovie.isOnWatchList());
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
                drawable = ContextCompat.getDrawable(this, R.drawable.ic_star_ratingbar_half);
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
        MovieReview userReview = null;

        // check if the user has already rated this movie
        if (!movie.getReviews().isEmpty()) {
            for (MovieReview review : movie.getReviews()) {
                if (Objects.equals(review.getUser().getId(), user.getId())) {
                    userReview = review;
                }
            }
        }

        if (userReview == null) {
            // new rating
            userReview = new MovieReview(movie.getId(), user);
        }

        // pass the Movie Review to dialog and create a new instance
        reviewDialog = ReviewDialogFragment.newInstance(userReview);
        reviewDialog.show(getFragmentManager(), "ReviewDialogFragment");

        if (!mUserMovie.isOnWatchedList()) {
            onClickAddToSeenIt();
        }
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

    @OnClick(R.id.openReviewsList)
    public void openReviews() {
        Intent intent = new Intent(this, MovieReviewListActivity.class);
        Parcelable parcel = Parcels.wrap(movie);
        intent.putExtra("movie", parcel);
        startActivity(intent);
    }

    private void updateUserMovie(final UserMovie userMovie) {
        if (userMovie != null) {
            if (relationshipExists) { // update
                Call<UserMovie> call = userApiService.updateUserMovie(userMovie);
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
                Call<UserMovie> call = userApiService.createUserMovie(userMovie);
                call.enqueue(new Callback<UserMovie>() {
                    @Override
                    public void onResponse(Call<UserMovie> call, Response<UserMovie> response) {
                        if (response.isSuccess()) {
                            mUserMovie = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserMovie> call, Throwable t) {
                        Log.d(TAG_ACTIVITY, "Error");
                    }
                });
            }
        }
    }

    @Override
    public void onFinishReview(MovieReview review) {
        reviewDialog.dismiss(); // close review dialog after getting the data

        addMovieReview(review);
    }

    private void addMovieReview(final MovieReview review) {
        if (review.getId() == null) {
            // new review
            Call<MovieReview> call = movieApiService.createMovieReview(review);
            call.enqueue(new Callback<MovieReview>() {
                @Override
                public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {
                    if (response.isSuccess()) {
                        movie.getReviews().add(response.body());
                    }
                }

                @Override
                public void onFailure(Call<MovieReview> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG_ACTIVITY, "Error creating movie review.");
                }
            });
        } else {
            // update review
            Call<MovieReview> call = movieApiService.updateMovieReview(review);
            call.enqueue(new Callback<MovieReview>() {
                @Override
                public void onResponse(Call<MovieReview> call, Response<MovieReview> response) {
                    if (response.isSuccess()) {
                        replaceOldReviewInList(movie.getReviews(), response.body());
                    }
                }

                @Override
                public void onFailure(Call<MovieReview> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG_ACTIVITY, "Error creating movie review.");
                }
            });
        }
    }

    // I wish Android had Java 8 so I could use Lambdas, this is the bets option
    private void replaceOldReviewInList(RealmList<MovieReview> reviews, MovieReview review) {
        for (int i = 0; i < reviews.size(); i++) {
            if (Objects.equals(reviews.get(i).getId(), review.getId())) {
                reviews.set(i, review);
            }

        }
    }
}
