package com.example.enda.flickadvisor.fragments;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enda.flickadvisor.R;

public class ReviewDialogFragment extends DialogFragment {

    private static final String TAG_FRAGMENT = "FRAGMENT_REVIEW_DIALOG";

    private RatingBar ratingBar;
    private TextView mDescription;

    OnAddReviewListener mCallback;

    public interface OnAddReviewListener {
        void onFinishReview(float rating, String description);
    }

    public static ReviewDialogFragment newInstance() {
        return new ReviewDialogFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_dialog, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        mDescription = (TextView) v.findViewById(R.id.reviewDescription);

        setButtonClickListeners(v);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnAddReviewListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAddReviewListener");
        }
    }

    private void setButtonClickListeners(View view) {
        Button mSaveButton = (Button) view.findViewById(R.id.btnSaveReview);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating() > 1F) {
                    returnData(ratingBar.getRating(), mDescription.getText() == null ? "" : mDescription.getText().toString());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Please select a rating between 1 and 5.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button mCancelButton = (Button) view.findViewById(R.id.btnCancelReview);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }

    private void returnData(float rating, String description) {
        mCallback.onFinishReview(rating, description);
    }

}