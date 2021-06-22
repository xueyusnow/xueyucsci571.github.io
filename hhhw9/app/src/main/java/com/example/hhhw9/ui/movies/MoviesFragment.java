package com.example.hhhw9.ui.movies;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hhhw9.MovieDetail;
import com.example.hhhw9.R;
import com.example.hhhw9.ui.home.HomeFragment;
import com.example.hhhw9.ui.search.SearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MoviesFragment extends Fragment {
    private RequestQueue mQueue;
    private ArrayList<SliderData> sliderDataArrayList;
    private ArrayList<SliderData> sliderDataArrayList1;
    private ArrayList<SliderData> sliderDataArrayList2;
    private MoviesViewModel mViewModel;

    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(MoviesViewModel.class);
        View root = inflater.inflate(R.layout.movies_fragment, container, false);
//        mTextViewResult = root.findViewById(R.id.text_view_result);
        ((HomeFragment)(MoviesFragment.this.getParentFragment())).changeLayout(0);
        ScrollView layout=root.findViewById(R.id.containerchoice);
        LinearLayout loading=root.findViewById(R.id.loading);
        layout.setVisibility(View.GONE);
        mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        SliderView sliderView = root.findViewById(R.id.slider);

        sliderDataArrayList = new ArrayList<>();
        jsonParse(new VolleyCallback(){
            @Override
            public void onSuccess(ArrayList<SliderData> sliderDataArrayList){
                SliderAdapter adapter = new SliderAdapter(getActivity().getApplicationContext(), sliderDataArrayList);
                adapter.setOnItemClickListener(new SliderAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view,int position) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), MovieDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("id","#"+sliderDataArrayList.get(position).getId());
                        intent.putExtra("img",sliderDataArrayList.get(position).getImgUrl());
                        intent.putExtra("name",sliderDataArrayList.get(position).getTitle());
                        view.getContext().startActivity(intent);
                    }
                });
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                sliderView.setSliderAdapter(adapter);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
            }
        });
        sliderDataArrayList1 = new ArrayList<>();
        RecyclerView recyclerView=root.findViewById(R.id.recycler_view);
        jsonParse1(new VolleyCallback(){
            @Override
            public void onSuccess(ArrayList<SliderData> list){
                sliderDataArrayList1=list;
                TopratedAdapter adapter = new TopratedAdapter(getActivity().getApplicationContext(), sliderDataArrayList1);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter.setOnItemClickListener(new TopratedAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view,int position) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), MovieDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("id","#"+sliderDataArrayList1.get(position).getId());
                        intent.putExtra("img",sliderDataArrayList1.get(position).getImgUrl());
                        intent.putExtra("name",sliderDataArrayList1.get(position).getTitle());
                        view.getContext().startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        sliderDataArrayList2 = new ArrayList<>();
        RecyclerView recyclerView2=root.findViewById(R.id.recycler_view2);
        jsonParse2(new VolleyCallback(){
            @Override
            public void onSuccess(ArrayList<SliderData> list){
                sliderDataArrayList2=list;
                PopularAdapter adapter = new PopularAdapter(getActivity().getApplicationContext(), sliderDataArrayList2);
                recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter.setOnItemClickListener(new PopularAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view,int position) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), MovieDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("id","#"+sliderDataArrayList2.get(position).getId());
                        intent.putExtra("img",sliderDataArrayList2.get(position).getImgUrl());
                        intent.putExtra("name",sliderDataArrayList2.get(position).getTitle());
                        view.getContext().startActivity(intent);
                    }
                });
                recyclerView2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                layout.setVisibility(View.VISIBLE);
                ((HomeFragment)(MoviesFragment.this.getParentFragment())).changeLayout(1);
                loading.setVisibility(View.GONE);
            }
        });
        TextView textView=root.findViewById(R.id.textfooter1);
        String html = "<a href='https://www.themoviedb.org/'>Powered by TMDB</a>";
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(html));
        StringUtil.stripUnderlines((Spannable)
        textView.getText());

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
        // TODO: Use the ViewModel
    }
    private void jsonParse(final VolleyCallback callback) {
//        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1";
        String url="http://xueyuhw9.wl.r.appspot.com/nowplaying";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
//                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < 6; i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        String id = employee.getString("id");
                        String title=employee.getString("title");
                        String url="";
                        if(!employee.getString("poster_path").equals("null")&&!employee.getString("poster_path").equals(""))
                            url="https://image.tmdb.org/t/p/w500"+employee.getString("poster_path");
                        else
                            url="https://cinemaone.net/images/movie_placeholder.png";
                        sliderDataArrayList.add(new SliderData(id,url,title));
                    }
                    callback.onSuccess(sliderDataArrayList);
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
    private void jsonParse1(final VolleyCallback callback) {
//        String url1 = "https://api.themoviedb.org/3/movie/top_rated?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1";
        String url1="http://xueyuhw9.wl.r.appspot.com/toprated";
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url1, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
//                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < 10; i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        String id = employee.getString("id");
                        String title=employee.getString("title");
                        String url="";
                        if(!employee.getString("poster_path").equals("null")&&!employee.getString("poster_path").equals(""))
                            url="https://image.tmdb.org/t/p/w500"+employee.getString("poster_path");
                        else
                            url="https://cinemaone.net/images/movie_placeholder.png";
                        sliderDataArrayList1.add(new SliderData(id,url,title));
                    }
                    callback.onSuccess(sliderDataArrayList1);

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
        mQueue.add(request1);
    }
    private void jsonParse2(final VolleyCallback callback) {
//        String url2 = "https://api.themoviedb.org/3/movie/popular?api_key=38ed65c377771b36628b1df55ae458b9&language=enUS&page=1";
        String url2="http://xueyuhw9.wl.r.appspot.com/popular";
        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url2, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
//                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < 10; i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        String id = employee.getString("id");
                        String title=employee.getString("title");
                        String url="";
                        if(!employee.getString("poster_path").equals("null")&&!employee.getString("poster_path").equals(""))
                            url="https://image.tmdb.org/t/p/w500"+employee.getString("poster_path");
                        else
                            url="https://cinemaone.net/images/movie_placeholder.png";
                        sliderDataArrayList2.add(new SliderData(id,url,title));
                    }
                    callback.onSuccess(sliderDataArrayList2);

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
        mQueue.add(request2);
    }
    public interface VolleyCallback{
        void onSuccess(ArrayList<SliderData> sliderDataArrayList);
    }
}