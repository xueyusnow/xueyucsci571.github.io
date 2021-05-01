package com.example.hhhw9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final List<SearchData> searchItems;
    private Context context;
    private SearchAdapter.OnItemClickListener mOnItemClickListener;
    public SearchAdapter(Context context, ArrayList<SearchData> items) {
        this.searchItems = items;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.searchitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        final SearchData sliderItem = searchItems.get(position);

        // Glide is use to load image
        // from url in your imageview.
        Glide.with(holder.itemView)
                .load(sliderItem.getImage())
                .fitCenter()
                .transform(new RoundedCorners(50))
                .into(holder.itemImage);
        holder.star.setImageResource(R.drawable.ic_rate);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });
        if(sliderItem.getYear().equals("")||sliderItem.getYear().equals("null"))
            holder.type.setText(sliderItem.getType());
        else
            holder.type.setText(sliderItem.getType()+"("+sliderItem.getYear().substring(0,4)+")");
        if(!sliderItem.getTitle().equals("")&&!sliderItem.getTitle().equals("null"))
            holder.title.setText(sliderItem.getTitle());
        if(sliderItem.getYear().equals("")||sliderItem.getYear().equals("null"))
            holder.searchrate.setText("0");
        else{
            float ratee=Float.parseFloat(sliderItem.getRate());
            holder.searchrate.setText(ratee/2+"");
        }

    }

    @Override
    public int getItemCount() {
        return searchItems==null? 0:searchItems.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView type;
        private TextView title;
        private TextView searchrate;
        private ImageView star;
        public SearchViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            type = view.findViewById(R.id.type);
            title = view.findViewById(R.id.searchtitle);
            searchrate = view.findViewById(R.id.searchrate);
            star=view.findViewById(R.id.star);
            if(searchItems.size()==0){
                star.setVisibility(View.GONE);
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}