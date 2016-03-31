package com.example.enda.flickadvisor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.adapters.MovieReviewsRecyclerAdapter;
import com.example.enda.flickadvisor.models.Movie;
import com.example.enda.flickadvisor.models.MovieReview;

import org.parceler.Parcels;

import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;

public class MovieReviewListActivity extends AppCompatActivity {

    private Movie mMovie;
    protected RecyclerView mRecyclerView;
    protected MovieReviewsRecyclerAdapter mRecyclerViewAdapter;

    // view bindings
    @Bind(R.id.sort_movie_reviews_nav) ImageButton mSortReviewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_review_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().getExtras() != null) {
            mMovie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        }

        if (mMovie != null && mMovie.getReviews() != null) {
            sortReviewsByDate(mMovie.getReviews());
            createRecyclerReview();
        }
    }

    private void createRecyclerReview() {
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_review_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerViewAdapter = new MovieReviewsRecyclerAdapter(mMovie.getReviews());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @OnClick(R.id.sort_movie_reviews_nav)
    public void onClickSortReviews() {
        PopupMenu popup = new PopupMenu(this, mSortReviewsButton);
        popup.getMenuInflater()
                .inflate(R.menu.sort_reviews_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            if (item.getTitle() == getString(R.string.title_newest_first)) {

                sortReviewsByDate(mMovie.getReviews());
                mRecyclerViewAdapter.notifyDataSetChanged();

            } else if (item.getTitle().equals(getString(R.string.title_highest_rated_first))) {

                sortReviewsByRating(mMovie.getReviews());
            }
            return true;
        });
        popup.show();
    }

    private void sortReviewsByDate(RealmList<MovieReview> reviews) {
        Collections.sort(reviews, (reviewOne, reviewTwo)
                -> Long.compare(reviewOne.getDate(), reviewTwo.getDate()));
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void sortReviewsByRating(RealmList<MovieReview> reviews) {
        Collections.sort(reviews, (reviewOne, reviewTwo) ->
                Float.compare(reviewTwo.getRating(), reviewOne.getRating()));
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

}
