package com.example.hhhw9.ui.tvs;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hhhw9.TvDetail;
import com.example.hhhw9.R;
import com.example.hhhw9.ui.movies.SliderAdapter;
import com.example.hhhw9.ui.movies.SliderData;
import com.example.hhhw9.ui.movies.StringUtil;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TvsFragment extends Fragment {
    private RequestQueue mQueue;
    private ArrayList<SliderData> sliderDataArrayList;
    private ArrayList<SliderData> sliderDataArrayList1;
    private ArrayList<SliderData> sliderDataArrayList2;
    private TvsViewModel mViewModel;

    public static TvsFragment newInstance() {
        return new TvsFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(TvsViewModel.class);
        View root = inflater.inflate(R.layout.tvs_fragment, container, false);
//        mTextViewResult = root.findViewById(R.id.text_view_result);

        ProgressBar spinner=root.findViewById(R.id.progressBar);
        TextView loading=root.findViewById(R.id.loading);
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
                        intent.setClass(getContext(), TvDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("id","*"+sliderDataArrayList.get(position).getId());
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
                        intent.setClass(getContext(), TvDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("id","*"+sliderDataArrayList1.get(position).getId());
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
        jsonParse2(new TvsFragment.VolleyCallback(){
            @Override
            public void onSuccess(ArrayList<SliderData> list){
                sliderDataArrayList2=list;
                PopularAdapter adapter = new PopularAdapter(getActivity().getApplicationContext(), sliderDataArrayList2);
                recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter.setOnItemClickListener(new PopularAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view,int position) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), TvDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("id","*"+sliderDataArrayList2.get(position).getId());
                        intent.putExtra("img",sliderDataArrayList2.get(position).getImgUrl());
                        intent.putExtra("name",sliderDataArrayList2.get(position).getTitle());
                        view.getContext().startActivity(intent);
                    }
                });
                recyclerView2.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        TextView textView=root.findViewById(R.id.textfooter1);
        String html = "<a href='https://www.themoviedb.org/'>Powered by TMDB</a>";
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(html));
        StringUtil.stripUnderlines((Spannable)
        textView.getText());
        spinner.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TvsViewModel.class);
        // TODO: Use the ViewModel
    }
    private void jsonParse(final TvsFragment.VolleyCallback callback) {
//        String url = "https://api.themoviedb.org/3/trending/tv/day?api_key=38ed65c377771b36628b1df55ae458b9";
        String url="http://xueyuhw9.wl.r.appspot.com/trendingtv";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
//                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < 6; i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        String id = employee.getString("id");
                        String title=employee.getString("name");
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
    private void jsonParse1(final TvsFragment.VolleyCallback callback) {
//        String url1 = "https://api.themoviedb.org/3/tv/top_rated?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1";
        String url1="http://xueyuhw9.wl.r.appspot.com/topratedtv";
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url1, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
//                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < 10; i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        String id = employee.getString("id");
                        String title=employee.getString("name");
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
    private void jsonParse2(final TvsFragment.VolleyCallback callback) {
//        String url2 = "https://api.themoviedb.org/3/tv/popular?api_key=38ed65c377771b36628b1df55ae458b9&language=enUS&page=1";
        String url2="http://xueyuhw9.wl.r.appspot.com/populartv";
        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url2, null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
//                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < 10; i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        String id = employee.getString("id");
                        String title=employee.getString("name");
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
    private interface VolleyCallback{
        void onSuccess(ArrayList<SliderData> sliderDataArrayList);
    }
}