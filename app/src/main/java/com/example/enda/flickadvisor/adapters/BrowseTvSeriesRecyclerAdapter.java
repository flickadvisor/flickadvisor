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
 * Created by enda on 27/02/16.
 */
public class BrowseTvSeriesRecyclerAdapter extends RecyclerView.Adapter<BrowseItemViewHolder> {

    private Context context;
    private List<JsonObject> mData = Collections.emptyList();

    public BrowseTvSeriesRecyclerAdapter(Context context, List<JsonObject> mData) {
        this.context = context;
        this.mData = mData;
    }

    public BrowseTvSeriesRecyclerAdapter() { }

    @Override
    public BrowseItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View mView = inflater.inflate(R.layout.movie_card_v1, parent, false);

        return new BrowseItemViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(BrowseItemViewHolder holder, int position) {
        final JsonObject current = mData.get(position);

        final String movieName = current.get("original_name").getAsString();
        holder.mTitle.setText(movieName);

        if (!current.get("poster_path").isJsonNull()) {
            // poster path
            String posterPath = current.get("poster_path").getAsString();
            String baseUrl = "http://image.tmdb.org/t/p/w185";
            // load image from provided path
            Picasso.with(context)
                    .load(baseUrl + posterPath)
                    .fit()
                    .into(holder.mImageView);
        }

//        holder.mCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // UID of selected movie
//                long id = current.get("id").getAsLong();
//                Intent intent = new Intent(context, MovieActivity.class);
//                intent.putExtra("movieId", id);
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
