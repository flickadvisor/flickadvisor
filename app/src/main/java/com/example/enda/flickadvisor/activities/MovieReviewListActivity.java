package com.example.enda.flickadvisor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.adapters.MovieReviewsRecyclerAdapter;
import com.example.enda.flickadvisor.models.Movie;
import com.example.enda.flickadvisor.models.ReviewSorting;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class MovieReviewListActivity extends AppCompatActivity {

    private Movie mMovie;
    protected RecyclerView mRecyclerView;
    protected MovieReviewsRecyclerAdapter mRecyclerViewAdapter;

    // view bindings
    @Bind(R.id.spinner_sort_movie_reviews) Spinner mSortReviewsSpinner;

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

        if (mMovie != null && mMovie.getReviews().size() > 0) {
            createRecyclerReview();
            setUpSpinner();
        }
    }

    private void setUpSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.review_sortings_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSortReviewsSpinner.setAdapter(adapter);
    }

    private void createRecyclerReview() {
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_review_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerViewAdapter = new MovieReviewsRecyclerAdapter(mMovie.getReviews());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @OnItemSelected(R.id.spinner_sort_movie_reviews)
    public void onClickSortReviews(int position) {
        ReviewSorting selected = ReviewSorting.fromInt(position);

        if (selected == ReviewSorting.NewestFirst) {

        } else if (selected == ReviewSorting.TopRatedFirst) {
            
        }

        mRecyclerViewAdapter.notifyDataSetChanged();
    }

}
