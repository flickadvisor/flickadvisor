package com.example.enda.flickadvisor.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.models.MovieReview;
import com.example.enda.flickadvisor.util.GeneralHelper;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by enda on 27/03/16.
 */
public class MovieReviewsRecyclerAdapter extends RecyclerView.Adapter<MovieReviewHolder> {

    private Context context;
    private List<MovieReview> mReviews = Collections.emptyList();

    public MovieReviewsRecyclerAdapter(List<MovieReview> mReviews) {
        this.mReviews = mReviews;
    }

    @Override
    public MovieReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.review_list_item, parent, false);

        return new MovieReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewHolder holder, int position) {
        final MovieReview current = mReviews.get(position);

        // clear dynamically added stars to stop accumulation
        holder.mStarsContainer.removeAllViews();
        for (float i = 0; i < current.getRating(); i++) {
            Drawable drawable;
            if (current.getRating() - i >= 0.25 && current.getRating() - i <= 0.75) {
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_star_ratingbar_half);
            } else {
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_star_24dp);
            }

            ImageView image = new ImageView(context);
            image.setImageDrawable(drawable);
            holder.mStarsContainer.addView(image);
        }

        holder.mDescription.setText(current.getDescription());
        Date date = new Date(current.getDate());
        holder.mDate.setText(GeneralHelper.dateToSystemFormat(context, date));
    }

    @Override
    public int getItemCount() {
        return mReviews == null ? 0 : mReviews.size();
    }

}
