package com.example.hhhw9.ui.watch;

import android.content.Context;
import android.content.SharedPreferences;

import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.hhhw9.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static android.content.Context.MODE_PRIVATE;

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.WatchViewHolder> implements ItemMoveCallback.ItemTouchHelperContract{
    private List<ArrayList<String>> mWatchItems;
    private Context context;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private WatchAdapter.OnItemClickListener mOnItemClickListener;
    private WatchAdapter.StartDragListener  mStartDragListener;
    private View myview;
    public WatchAdapter(Context context, List<ArrayList<String>> items,View view) {
        this.mWatchItems = items;
        this.context = context;
        this.myview=view;
    }

    @NonNull
    @Override
    public WatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WatchAdapter.WatchViewHolder(LayoutInflater.from(context).inflate(R.layout.watchlistitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WatchViewHolder holder, int position) {
        final ArrayList<String> sliderItem = mWatchItems.get(position);
        Glide.with(holder.itemView)
                .load(sliderItem.get(1))
                .fitCenter()
                .transform(new RoundedCorners(50))
                .into(holder.itemImage);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });
        holder.itemImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() ==
                        MotionEvent.ACTION_DOWN) {
                    mStartDragListener.requestDrag(holder);
//                    mStartDragListener.buttonClick(holder);
                }
                return false;
            }
        });
//        notifyDataSetChanged();
//        this.notifyItemChanged(position);

        sharedpreferences = context.getSharedPreferences(mypreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String s=sliderItem.get(0);
        if(s.startsWith("#")){
            holder.type.setText("Movie");
        }else{
            holder.type.setText("TV");
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toast=mWatchItems.get(position).get(2)+" was removed from Watchlist";
                mWatchItems.remove(position);
                if(mWatchItems.size()==0) {
                    TextView t = myview.findViewById(R.id.text_watch);
                    t.setVisibility(View.VISIBLE);
                }
                Gson gson = new Gson();
                String json = gson.toJson(mWatchItems);
                editor.putString("array", json);
                editor.apply();
                Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mWatchItems==null? 0:mWatchItems.size();
    }

    public class WatchViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView button;
        private TextView type;
        public WatchViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            button = view.findViewById(R.id.removebtn);
            type=view.findViewById(R.id.type);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
    public interface StartDragListener {
        void requestDrag(WatchViewHolder viewHolder);
    }
    public void setOnItemTouchListener(StartDragListener startDragListener) {
        mStartDragListener = startDragListener;
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        sharedpreferences = context.getSharedPreferences(mypreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        Collections.swap(mWatchItems, fromPosition, toPosition);
        String json = gson.toJson(mWatchItems);
        editor.putString("array", json);
        editor.apply();
        notifyDataSetChanged();
    }
    @Override
    public void onRowSelected(WatchViewHolder myViewHolder) {
//        myViewHolder.rowView.setBackgroundColor(Color.GRAY);

    }
    @Override
    public void onRowClear(WatchViewHolder myViewHolder){

    }
    public void mychanged(){
        SharedPreferences sharedpreferences = context.getSharedPreferences(mypreference, MODE_PRIVATE);
        String json=sharedpreferences.getString("array","");
        Type type = new TypeToken<List<ArrayList<String>>>() {}.getType();
        Gson gson = new Gson();
        mWatchItems=gson.fromJson(json, type);
        if(mWatchItems.size()==0) {
            TextView t = myview.findViewById(R.id.text_watch);
            t.setVisibility(View.VISIBLE);
        }
        notifyDataSetChanged();
    }
}
