package com.example.hhhw9.ui.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import com.google.gson.Gson;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.hhhw9.MovieDetail;
import com.example.hhhw9.R;
import com.example.hhhw9.ui.watch.WatchFragment;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class TopratedAdapter extends RecyclerView.Adapter<TopratedAdapter.TopratedViewHolder> {
    private final List<SliderData> mTopratedItems;
    private Context context;
    public String menutitle="Add to Watchlist";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private OnItemClickListener mOnItemClickListener;
    public TopratedAdapter(Context context, ArrayList<SliderData> items) {
        this.mTopratedItems = items;
        this.context = context;
    }

    @NonNull
    @Override
    public TopratedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopratedViewHolder(LayoutInflater.from(context).inflate(R.layout.toprateditem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopratedViewHolder holder, int position) {
        final SliderData sliderItem = mTopratedItems.get(position);
        Glide.with(holder.itemView)
                .load(sliderItem.getImgUrl())
                .fitCenter()
                .transform(new RoundedCorners(50))
                .into(holder.itemImage);
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,position);
            }
        });
        final TextView button = holder.button;
        sharedpreferences = context.getSharedPreferences(mypreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        ArrayList<ArrayList<String>> watchlist=new ArrayList<>();
        String json = gson.toJson(watchlist);
        if(!sharedpreferences.contains("array")){
            editor.putString("array",json);
            editor.apply();
        }
        String s="#"+sliderItem.getId();
        button.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
//                View v=getView().findViewById(R.id.toprateditems);
                PopupMenu popupMenu = new PopupMenu(holder.button.getContext(), button);
                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                Menu menu=popupMenu.getMenu();
                MenuItem watch=menu.findItem(R.id.menu_watchlist);
                if(!sharedpreferences.contains("array")) {
                    editor.putString("array",gson.toJson(new ArrayList<String>()));
                    editor.commit();
                }
                String json = sharedpreferences.getString("array", "");
                Type type = new TypeToken<List<ArrayList<String>>>() {}.getType();
                List<ArrayList<String>> cur=gson.fromJson(json, type);

                if(cur!=null) {
                    boolean flag=false;
                    for (int i = 0; i < cur.size(); i++) {
                        if (cur.get(i).get(0).equals(s)) {
                            watch.setTitle("Remove from Watchlist");
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        watch.setTitle("Add to Watchlist");
                    }
                }else{
                    watch.setTitle("Add to Watchlist");
                }
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        switch(menuItem.getItemId()){
                            case R.id.menu_tmdb:
                                String html="https://www.themoviedb.org/movie/"+sliderItem.getId();
                                Uri uri = Uri.parse(html);
                                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                view.getContext().startActivity(intent);
                                break;
                            case R.id.menu_facebook:
                                String share="https://www.themoviedb.org/movie/"+sliderItem.getId();
                                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + share;
                                Uri uri2 = Uri.parse(sharerUrl);
                                Intent shareIntent1 = new Intent(Intent.ACTION_VIEW, uri2);
                                shareIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                view.getContext().startActivity(shareIntent1);
                                break;
                            case R.id.menu_twitter:
                                String text="Check this out!%0A";
                                String shareText="https://www.themoviedb.org/movie/"+sliderItem.getId();
                                String tweetUrl = "https://twitter.com/intent/tweet?text=" + text+ shareText;
                                Uri uri1 = Uri.parse(tweetUrl);
                                Intent shareIntent = new Intent(Intent.ACTION_VIEW, uri1);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                view.getContext().startActivity(shareIntent);
                                break;
                            case R.id.menu_watchlist:
                                Gson gson = new Gson();
                                String json=sharedpreferences.getString("array",null);
                                Type type = new TypeToken<List<ArrayList<String>>>() {}.getType();
                                List<List<String>> cur=gson.fromJson(json, type);
                                if(cur==null){
                                    cur=new ArrayList<>();
                                }
                                if(menuItem.getTitle().equals("Add to Watchlist")){
                                    System.out.println("yes");
                                    menutitle="Remove from Watchlist";
                                    menuItem.setTitle(menutitle);
                                    cur.add(Arrays.asList(s,sliderItem.getImgUrl(),sliderItem.getTitle()));
                                    editor.putString("array",gson.toJson(cur));
                                    String toast=sliderItem.getTitle()+" was added to Watchlist";
                                    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
                                    editor.apply();
                                    System.out.println(sharedpreferences.getString("array",""));
                                }else{
                                    menutitle="Add to Watchlist";
                                    menuItem.setTitle(menutitle);
                                    String toast=sliderItem.getTitle()+" was removed from Watchlist";
                                    Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
//                                    editor.remove(s);
                                    for(int i=0;i<cur.size();i++){
                                        if(cur.get(i).get(0).equals(s)){
                                            cur.remove(i);
                                            break;
                                        }
                                    }
                                    editor.putString("array",gson.toJson(cur));
                                    editor.commit();
                                    System.out.println(sharedpreferences.getString("array",""));
                                }
                                break;
                        }
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTopratedItems==null? 0:mTopratedItems.size();
    }

    public class TopratedViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImage;
        private TextView button;
        public TopratedViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            button = view.findViewById(R.id.popupbtn);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}