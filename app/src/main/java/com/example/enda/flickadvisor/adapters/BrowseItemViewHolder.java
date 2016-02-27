package com.example.enda.flickadvisor.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enda.flickadvisor.R;

/**
 * Created by enda on 21/02/16.
 */
public class BrowseItemViewHolder extends RecyclerView.ViewHolder {

    public CardView mCardView;
    public TextView mTitle;
    public ImageView mImageView;

    public BrowseItemViewHolder(View itemView) {
        super(itemView);

        mCardView = (CardView)itemView.findViewById(R.id.browse_card);
        mTitle = (TextView)itemView.findViewById(R.id.title);
        mImageView = (ImageView)itemView.findViewById(R.id.preview_card_image);

    }
}
