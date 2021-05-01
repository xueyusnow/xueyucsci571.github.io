package com.example.hhhw9.ui.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hhhw9.MovieDetail;
import com.example.hhhw9.R;
import com.example.hhhw9.SearchAdapter;
import com.example.hhhw9.SearchData;
import com.example.hhhw9.TvDetail;
import com.example.hhhw9.ui.movies.MoviesFragment;
import com.example.hhhw9.ui.movies.SliderData;
import com.example.hhhw9.ui.movies.TopratedAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SearchFragment extends Fragment {
    private RequestQueue mQueue;
    private DashboardViewModel dashboardViewModel;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<SearchData> searchlist;
    private TextView notfound;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        mQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        searchView=root.findViewById(R.id.searchbar);
//        searchView.setFocusable(false);

//        searchView.setFocusable(false);



        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(Color.WHITE);
        textView.setHintTextColor(Color.parseColor("#bababa"));
        int magId = getResources().getIdentifier("android:id/search_mag_icon",null, null);
        ImageView icon =searchView.findViewById(magId);
        icon.setColorFilter(Color.parseColor("#f5f5f5"));
        int md = getResources().getIdentifier("android:id/search_close_btn",null, null);
        ImageView icon1 =searchView.findViewById(md);
        icon1.setColorFilter(Color.parseColor("#bababa"));
        notfound=root.findViewById(R.id.notfound);
        recyclerView=root.findViewById(R.id.recycler_view);
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                jsonparse(newText,new VolleyCallback(){
                    @Override
                    public void onSuccess(ArrayList<SearchData> list) {
                        searchlist=list;
                        SearchAdapter adapter = new SearchAdapter(getActivity().getApplicationContext(), searchlist);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent();

                                if(searchlist.get(position).getType().equals("movie")) {
                                    intent.setClass(getContext(), MovieDetail.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                    intent.putExtra("id", "#" + searchlist.get(position).getId());
                                }else {
                                    intent.setClass(getContext(), TvDetail.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                    intent.putExtra("id", "*" + searchlist.get(position).getId());
                                }
                                intent.putExtra("img",searchlist.get(position).getPoster());
                                intent.putExtra("name",searchlist.get(position).getTitle());
                                System.out.println(searchlist.get(position).getId()+" "+searchlist.get(position).getPoster()+" "+searchlist.get(position).getTitle());
                                view.getContext().startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
        return root;
    }
    @Override
    public void onResume(){
        super.onResume();
//        searchView.clearFocus();
//        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    imm.showSoftInput(v, InputMethodManager.SHOW_FORCED); //show software keyboard
//                } else {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //hide
//                }
//            }
//        });

    }
    private void jsonparse(String newText,final VolleyCallback callback){
        searchlist=new ArrayList<>();
//        String url = "https://api.themoviedb.org/3/search/multi?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&query="+newText;
        String url="http://xueyuhw9.wl.r.appspot.com/searches/"+newText;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
//                    JSONArray jsonArray = response.getJSONArray("results");
                    int len=Math.min(20,jsonArray.length());
                    for (int i = 0; i < len; i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        String id = employee.getString("id");
                        String type=employee.getString("media_type");
                        String title="";
                        String year="";
                        if(type.equals("movie")) {
                            try{
                                title = employee.getString("title");
                            }
                            catch(JSONException e){
                                title="";
                            }
                            try{
                                year = employee.getString("release_date");}
                            catch(JSONException e){
                                year="";
                            }
                        }
                        else {
                            try{
                                title = employee.getString("name");
                            }
                            catch(JSONException e){
                                title="";
                            }
                            try{
                                year = employee.getString("first_air_date");}
                            catch(JSONException e){
                                year="";
                            }
                        }
                        String rate="";
                        try{
                            rate=employee.getString("vote_average");}
                        catch(JSONException e){
                            rate="";
                        }
                        String url="";
                        try {
                            if (!employee.getString("backdrop_path").equals("null") &&!employee.getString("backdrop_path").equals(""))
                                url = "https://image.tmdb.org/t/p/w500" + employee.getString("backdrop_path");
                            else
                                url = "https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/movie-placeholder.jpg";
                        }catch(JSONException e){
                            url = "https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/movie-placeholder.jpg";
                        }
                        String urll="";
                        try {
                            if (!employee.getString("poster_path").equals("null") &&!employee.getString("poster_path").equals(""))
                                urll = "https://image.tmdb.org/t/p/w500" + employee.getString("poster_path");
                            else
                                urll = "https://cinemaone.net/images/movie_placeholder.png";
                        }catch(JSONException e){
                            urll = "https://cinemaone.net/images/movie_placeholder.png";
                        }
                        searchlist.add(new SearchData(url,title,type,id,year,rate,urll));
                    }
                    if(searchlist.size()==0){
                        notfound.setText("No results found.");
                        notfound.setVisibility(View.VISIBLE);
                    }else{
                        notfound.setVisibility(View.GONE);
                    }
                    callback.onSuccess(searchlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }
    public interface VolleyCallback{
        void onSuccess(ArrayList<SearchData> list);
    }
}