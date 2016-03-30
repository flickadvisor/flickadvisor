package com.example.enda.flickadvisor.fragments;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.models.MovieReview;

import org.parceler.Parcels;

public class ReviewDialogFragment extends DialogFragment {

    private static String TAG_FRAGMENT;

    private RatingBar ratingBar;
    private TextView mDescription;
    private MovieReview mReview;

    OnAddReviewListener mCallback;

    public interface OnAddReviewListener {
        void onFinishReview(MovieReview movieReview);
    }

    public static ReviewDialogFragment newInstance(MovieReview userReview) {
        ReviewDialogFragment dialog = new ReviewDialogFragment();

        // put review into a Bundle as a Parcelable
        Bundle args = new Bundle();
        Parcelable parcel = Parcels.wrap(userReview);
        args.putParcelable("review", parcel);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TAG_FRAGMENT = this.toString();
        return inflater.inflate(R.layout.fragment_review_dialog, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        mReview = Parcels.unwrap(getArguments().getParcelable("review"));
        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        mDescription = (TextView) v.findViewById(R.id.reviewDescription);

        ratingBar.setRating(mReview.getRating());
        mDescription.setText(mReview.getDescription());

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
                if (ratingBar.getRating() > 0F) {
                    mReview.setRating(ratingBar.getRating());
                    mReview.setDescription(mDescription.getText().toString());
                    returnData(mReview);
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

    private void returnData(final MovieReview movieReview) {
        mCallback.onFinishReview(movieReview);
    }

}