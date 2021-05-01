package com.example.hhhw9;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.hhhw9.ui.movies.SliderData;
import com.example.hhhw9.ui.movies.TopratedAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.MovieDetailViewHolder> {

    // list for storing urls of images.
    private MovieDetailAdapter.OnItemClickListener mOnItemClickListener;
    private final ArrayList<SliderData> recommendationlist;
    private Context context;

    // Constructor
    public MovieDetailAdapter(Context context, ArrayList<SliderData> items) {
        this.context = context;
        this.recommendationlist=items;
    }

    // We are inflating the slider_layout
    // inside on Create View Holder method.
    @Override
    public MovieDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieDetailViewHolder(LayoutInflater.from(context).inflate(R.layout.recommendationitem, parent, false));
    }

    // Inside on bind view holder we will
    // set data to item of Slider View.
    @Override
    public void onBindViewHolder(@NonNull MovieDetailViewHolder holder, int position) {

        final SliderData sliderItem = recommendationlist.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(holder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .transform(new RoundedCorners(50))
                .into(holder.topRatedCard);
        holder.topRatedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });

    }

    // this method will return
    // the count of our list.
    @Override
    public int getItemCount() {
        return recommendationlist.size();
    }

    public class MovieDetailViewHolder extends RecyclerView.ViewHolder {
        // Adapter class for initializing
        // the views of our slider view.
        View itemView;
        ImageView topRatedCard;


        public MovieDetailViewHolder(View itemView) {
            super(itemView);
            topRatedCard=itemView.findViewById(R.id.item_image);
            this.itemView = itemView;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(MovieDetailAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}