package com.example.enda.flickadvisor.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.enda.flickadvisor.R;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by enda on 21/02/16.
 */
public class BrowseRecyclerAdapter extends RecyclerView.Adapter<BrowseItemViewHolder> {

    private Context context;
    private List<JsonObject> mData = Collections.emptyList();

    public BrowseRecyclerAdapter(Context context, List<JsonObject> mData) {
        this.context = context;
        this.mData = mData;
    }

    public BrowseRecyclerAdapter() { }

    @Override
    public BrowseItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View mView = inflater.inflate(R.layout.browse_preview_view, parent, false);

        return new BrowseItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(BrowseItemViewHolder holder, int position) {
        JsonObject current = mData.get(position);

        holder.mTitle.setText(current.get("original_title").getAsString());


        if (!current.get("backdrop_path").isJsonNull()) {
            // poster path
            String posterPath = current.get("poster_path").getAsString();
            String baseUrl = "http://image.tmdb.org/t/p/w185";
            // load image from provided path
            Picasso.with(context)
                    .load(baseUrl + posterPath)
                    .fit()
                    .into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
