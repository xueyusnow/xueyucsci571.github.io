package com.example.hhhw9.ui.watch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hhhw9.MovieDetail;
import com.example.hhhw9.R;
import com.example.hhhw9.TvDetail;
import com.example.hhhw9.ui.movies.MoviesViewModel;
import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class WatchFragment extends Fragment {
    public static final String mypreference = "mypref";

    private NotificationsViewModel notificationsViewModel;
    private List<ArrayList<String>> watchlist;
    private WatchAdapter adapter;
    private RecyclerView recyclerView;
    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_watch, container, false);
        final TextView textView = root.findViewById(R.id.text_watch);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        SharedPreferences sharedpreferences = getContext().getSharedPreferences(mypreference, MODE_PRIVATE);
        String json=sharedpreferences.getString("array",null);
        System.out.println(json);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Type type = new TypeToken<List<ArrayList<String>>>() {}.getType();
        Gson gson = new Gson();
        if(gson.fromJson(json, type)==null){
            watchlist=new ArrayList<>();
        }else {
            watchlist = gson.fromJson(json, type);
        }
        if(watchlist.size()==0){
            textView.setVisibility(View.VISIBLE);
        }else{
            textView.setVisibility(View.GONE);
        }
        recyclerView=root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new WatchAdapter(getActivity().getApplicationContext(), watchlist, root);
        adapter.setOnItemClickListener(new WatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                if(watchlist.get(position).get(0).startsWith("#"))
                    intent.setClass(getContext(), MovieDetail.class);
                else{
                    intent.setClass(getContext(), TvDetail.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("id",watchlist.get(position).get(0));
                intent.putExtra("img",watchlist.get(position).get(1));
                intent.putExtra("name",watchlist.get(position).get(2));
                view.getContext().startActivity(intent);
            }

        });
//        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper.Callback callback =new ItemMoveCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        adapter.setOnItemTouchListener(new WatchAdapter.StartDragListener() {
            @Override
            public void requestDrag(WatchAdapter.WatchViewHolder viewHolder) {
                touchHelper.startDrag(viewHolder);

            }
        });
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return root;
    }
    @Override
    public void onResume(){

//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        SharedPreferences sharedpreferences = getContext().getSharedPreferences(mypreference, MODE_PRIVATE);
//        String json=sharedpreferences.getString("array","");
//        Type type = new TypeToken<List<ArrayList<String>>>() {}.getType();
//        Gson gson = new Gson();
//        List<ArrayList<String>> cur=gson.fromJson(json, type);
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString("array",gson.toJson(cur));
//        editor.apply();
//        adapter.notifyDataSetChanged();
        adapter.mychanged();
        super.onResume();
    }
}