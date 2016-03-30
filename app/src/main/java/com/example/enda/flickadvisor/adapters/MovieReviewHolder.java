package com.example.enda.flickadvisor.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.enda.flickadvisor.R;

/**
 * Created by enda on 27/03/16.
 */
public class MovieReviewHolder extends RecyclerView.ViewHolder {

    public LinearLayout mStarsContainer;
    public TextView mDescription;
    public TextView mDate;

    public MovieReviewHolder(View itemView) {
        super(itemView);

        mStarsContainer = (LinearLayout) itemView.findViewById(R.id.stars_container);
        mDescription = (TextView) itemView.findViewById(R.id.description);
        mDate = (TextView) itemView.findViewById(R.id.review_date);
    }
}
