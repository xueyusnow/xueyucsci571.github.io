package com.example.hhhw9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.hhhw9.ui.movies.MoviesFragment;
import com.example.hhhw9.ui.movies.PopularAdapter;
import com.example.hhhw9.ui.movies.SliderData;
import com.example.hhhw9.ui.movies.TopratedAdapter;
import com.example.hhhw9.ui.watch.WatchAdapter;
import com.example.hhhw9.ui.watch.WatchFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MovieDetail extends AppCompatActivity {
    private RequestQueue mQueue;
    private String id;
    private String img;
    private String name;
    private MovieData movieData;
    private String backdrop;
    private String videoId;
    public boolean changed=false;
    private float transrate=0;
    private ScrollView scroll;
    private LinearLayout layout;
    private LayoutInflater inflater;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private ArrayList<SliderData> recommendationlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_movie_detail);
        scroll=findViewById(R.id.scroll);
        scroll.setVisibility(View.GONE);
        layout=findViewById(R.id.loading);
        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        img=intent.getStringExtra("img");
        name=intent.getStringExtra("name");
        mQueue= Volley.newRequestQueue(this);
        videoload(new VolleyCallback(){
            @Override
            public void onSuccess(String id){
                videoId=id;
                YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                if(!id.equals("null")&&!id.equals("")) {
                    ImageView imageView = findViewById(R.id.backdrop);
                    imageView.setVisibility(View.GONE);
                    getLifecycle().addObserver(youTubePlayerView);
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0);
                        }
                    });
                }
            }

        });
        jsonParse(new VolleyCallback(){
            @Override
            public void onSuccess(String path){
                if(!path.equals("null")&&!path.equals("")) {
                    ImageView imageView = findViewById(R.id.backdrop);
                    Glide.with(getApplicationContext())
                            .load(path)
                            .fitCenter()
                            .into(imageView);
                }
            }
        });
        ImageView add=findViewById(R.id.add);
        ImageView facebook=findViewById(R.id.facebook);
        ImageView twitter=findViewById(R.id.twitter);
        sharedpreferences = getSharedPreferences(mypreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        ArrayList<ArrayList<String>> watchlist=new ArrayList<>();
        String json = gson.toJson(watchlist);
        if(!sharedpreferences.contains("array")){
            editor.putString("array",json);
            editor.commit();
        }
        String json1 = sharedpreferences.getString("array", "");
        Type type = new TypeToken<List<ArrayList<String>>>() {}.getType();
        List<ArrayList<String>> cur=gson.fromJson(json1, type);
        if(cur!=null) {
            boolean flag=false;
            for (int i = 0; i < cur.size(); i++) {
                if (cur.get(i).get(0).equals(id)) {
                    add.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_remove_circle_outline_24));
                    flag = true;
                    changed=true;
                    break;
                }
            }
            if (!flag) {
                add.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_circle_outline_24));
                changed=false;
//                flag=true;
            }
        }else{
            add.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_circle_outline_24));
            changed=false;
        }

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!sharedpreferences.contains("array")) {
                    editor.putString("array",gson.toJson(new ArrayList<>()));
                }
                String json = sharedpreferences.getString("array", "");
                Type type = new TypeToken<List<ArrayList<String>>>() {}.getType();
                List<List<String>> cur=gson.fromJson(json, type);
                if(cur==null){
                    cur=new ArrayList<>();
                }
                if(!changed) {
                    add.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_remove_circle_outline_24));
                    changed=true;
                    cur.add(Arrays.asList(id,img,name));
                    String toast=name+" was added to Watchlist";
                    Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
                    editor.putString("array",gson.toJson(cur));
                    editor.apply();
//                    notifyDataSetChanged();
                }
                else{
                    add.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_circle_outline_24));
                    for(int i=0;i<cur.size();i++){
                        if(cur.get(i).get(0).equals(id)){
                            cur.remove(i);
                            break;
                        }
                    }
                    changed=false;
                    String toast=name+" was removed from Watchlist";
                    Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
                    editor.putString("array",gson.toJson(cur));
                    boolean f=editor.commit();
                    System.out.println(f);
                }
            }
        });
        facebook.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String share="https://www.themoviedb.org/movie/"+id.substring(1,id.length());
                String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + share;
                Uri uri2 = Uri.parse(sharerUrl);
                Intent shareIntent1 = new Intent(Intent.ACTION_VIEW, uri2);
                shareIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(shareIntent1);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String text="Check this out!%0A";
                String shareText="https://www.themoviedb.org/movie/"+id.substring(1,id.length());
                String tweetUrl = "https://twitter.com/intent/tweet?text=" + text+ shareText;
                Uri uri1 = Uri.parse(tweetUrl);
                Intent shareIntent = new Intent(Intent.ACTION_VIEW, uri1);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(shareIntent);
            }
        });
        castload();
        reviewload();
        recommendationlist=new ArrayList<>();
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recommendationload(new VolleyCallback1(){
            @Override
            public void onSuccess(ArrayList<SliderData> list){
                recommendationlist=list;
                System.out.println(list.get(0).getImgUrl());
                MovieDetailAdapter adapter = new MovieDetailAdapter(getApplicationContext(), recommendationlist);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                adapter.setOnItemClickListener(new MovieDetailAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view,int position) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), MovieDetail.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        intent.putExtra("id","#"+recommendationlist.get(position).getId());
                        intent.putExtra("img",recommendationlist.get(position).getImgUrl());
                        intent.putExtra("name",recommendationlist.get(position).getTitle());
                        view.getContext().startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                layout.setVisibility(View.GONE);
                scroll.setVisibility(View.VISIBLE);
            }
        });
    }

    protected void jsonParse(final VolleyCallback callback) {
        final TextView Title=findViewById(R.id.title);
        final TextView Overview=findViewById(R.id.overview);
        final TextView Genres=findViewById(R.id.genres);
        final TextView Year=findViewById(R.id.year);

//        String url = "https://api.themoviedb.org/3/movie/"+id.substring(1,id.length())+"?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1";
        String url="http://xueyuhw9.wl.r.appspot.com/detail/"+id.substring(1,id.length());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {
                try {
                    JSONObject response = array.getJSONObject(0);
                    String title = response.getString("title");
                    if(!title.equals("null")&&!title.equals(""))
                        Title.setText(title);
                    else{
                        Title.setVisibility(View.GONE);
                    }
                    String overview=response.getString("overview");
                    String backdrop="";
                    try {
                        String poster1=response.getString("backdrop_path");
                        if(!poster1.equals("null"))
                            backdrop = "https://image.tmdb.org/t/p/w500" +poster1;
                        else
                            backdrop ="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/movie-placeholder.jpg";
                    }catch (JSONException e){
                        backdrop ="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW6/imgs/movie-placeholder.jpg";
                    }
                    JSONArray genresarray=response.getJSONArray("genres");
                    String genres="";
                    for(int i=0;i<genresarray.length();i++){
                        JSONObject genresObject=genresarray.getJSONObject(i);
                        if(i!=genresarray.length()-1){
                            genres+=genresObject.getString("name")+", ";
                        }else{
                            genres+=genresObject.getString("name");
                        }
                    }
                    String year=response.getString("release_date");
                    if(!overview.equals("null")&&!overview.equals(""))
                        Overview.setText(overview);
                    else{
                        Overview.setVisibility(View.GONE);
                        TextView Overview1=findViewById(R.id.overview1);
                        Overview1.setVisibility(View.GONE);
                    }
                    if(!genres.equals("null")&&!genres.equals(""))
                        Genres.setText(genres);
                    else{
                        Genres.setVisibility(View.GONE);
                        TextView Genres1=findViewById(R.id.genres1);
                        Genres1.setVisibility(View.GONE);
                    }
                    if(!year.equals("null")&&!year.equals(""))
                        Year.setText(year.substring(0,4));
                    else{
                        Year.setVisibility(View.GONE);
                        TextView Year1=findViewById(R.id.year1);
                        Year1.setVisibility(View.GONE);
                    }
                    callback.onSuccess(backdrop);
                } catch (JSONException e) {
                    e.printStackTrace();
                    findViewById(R.id.title).setVisibility(View.GONE);
                    findViewById(R.id.overview).setVisibility(View.GONE);
                    findViewById(R.id.overview1).setVisibility(View.GONE);
                    findViewById(R.id.genres).setVisibility(View.GONE);
                    findViewById(R.id.genres1).setVisibility(View.GONE);
                    findViewById(R.id.year).setVisibility(View.GONE);
                    findViewById(R.id.year1).setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                findViewById(R.id.title).setVisibility(View.GONE);
                findViewById(R.id.overview).setVisibility(View.GONE);
                findViewById(R.id.overview1).setVisibility(View.GONE);
                findViewById(R.id.genres).setVisibility(View.GONE);
                findViewById(R.id.genres1).setVisibility(View.GONE);
                findViewById(R.id.year).setVisibility(View.GONE);
                findViewById(R.id.year1).setVisibility(View.GONE);
            }
        });
        mQueue.add(request);
    }
    protected void videoload(final VolleyCallback callback){
//        String videourl = "https://api.themoviedb.org/3/movie/"+id.substring(1,id.length())+"/videos?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1";
        String videourl="http://xueyuhw9.wl.r.appspot.com/videos/"+id.substring(1,id.length());
        JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, videourl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray videos) {
                try {
//                    JSONArray videos = response.getJSONArray("results");
                    videoId = videos.getJSONObject(0).getString("key");
                    callback.onSuccess(videoId);
                    System.out.println("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                    YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                    youTubePlayerView.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                youTubePlayerView.setVisibility(View.GONE);
            }
        });
        mQueue.add(request1);
    }
    protected void castload(){
//        String url = "https://api.themoviedb.org/3/movie/"+id.substring(1,id.length())+"/credits?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1";
        String url="http://xueyuhw9.wl.r.appspot.com/credits/"+id.substring(1,id.length());
        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray casts) {
                try {
                    ArrayList<ImageView> line1=new ArrayList<ImageView>();
                    line1.add(findViewById(R.id.castpic0));
                    line1.add(findViewById(R.id.castpic1));
                    line1.add(findViewById(R.id.castpic2));
                    line1.add(findViewById(R.id.castpic3));
                    line1.add(findViewById(R.id.castpic4));
                    line1.add(findViewById(R.id.castpic5));
                    ArrayList<TextView> line2=new ArrayList<>();
                    line2.add(findViewById(R.id.castname0));
                    line2.add(findViewById(R.id.castname1));
                    line2.add(findViewById(R.id.castname2));
                    line2.add(findViewById(R.id.castname3));
                    line2.add(findViewById(R.id.castname4));
                    line2.add(findViewById(R.id.castname5));
//                    JSONArray casts=response.getJSONArray("cast");
                    for(int i=0;i<6;i++) {
                        if(i < casts.length()) {
                            JSONObject cast = casts.getJSONObject(i);
                            try {
                                String pic =cast.getString("profile_path");
                                String pict;
                                if(!pic.equals("null"))
                                    pict="https://image.tmdb.org/t/p/w500" +pic;
                                else
                                    pict="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW8/ReviewsPlaceholderImage.jpg";
                                Glide.with(getApplicationContext())
                                        .load(pict)
                                        .fitCenter()
                                        .into(line1.get(i));
                            }catch(JSONException e){
                                String pic="https://bytes.usc.edu/cs571/s21_JSwasm00/hw/HW8/ReviewsPlaceholderImage.jpg";
                                Glide.with(getApplicationContext())
                                        .load(pic)
                                        .fitCenter()
                                        .into(line1.get(i));
                            }
                            try {
                                String name = cast.getString("name");
                                line2.get(i).setText(name);
                            }catch(JSONException e){
                                line2.get(i).setText("");
                            }
                        }else{
                            for(int j=i;j<6;j++){
                                line1.get(j).setVisibility(View.GONE);
                                line2.get(j).setVisibility(View.GONE);
                            }
                            if(i==0){
                                findViewById(R.id.casttitle).setVisibility(View.GONE);
                                findViewById(R.id.line1).setVisibility(View.GONE);
                                findViewById(R.id.line2).setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    findViewById(R.id.line1).setVisibility(View.GONE);
                    findViewById(R.id.line2).setVisibility(View.GONE);
                    findViewById(R.id.casttitle).setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                findViewById(R.id.line1).setVisibility(View.GONE);
                findViewById(R.id.line2).setVisibility(View.GONE);
                findViewById(R.id.casttitle).setVisibility(View.GONE);
            }
        });
        mQueue.add(request2);
    }
    protected void reviewload(){
//        String url = "https://api.themoviedb.org/3/movie/"+id.substring(1,id.length())+"/reviews?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1";
        String url="http://xueyuhw9.wl.r.appspot.com/reviews/"+id.substring(1,id.length());
        JsonArrayRequest request3 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray reviews) {
                try {
                    ArrayList<TextView> line1=new ArrayList<>();
                    line1.add(findViewById(R.id.reviewtitle0));
                    line1.add(findViewById(R.id.reviewtitle1));
                    line1.add(findViewById(R.id.reviewtitle2));
                    ArrayList<TextView> line2=new ArrayList<>();
                    line2.add(findViewById(R.id.rate0));
                    line2.add(findViewById(R.id.rate1));
                    line2.add(findViewById(R.id.rate2));
                    ArrayList<TextView> line3=new ArrayList<>();
                    line3.add(findViewById(R.id.content0));
                    line3.add(findViewById(R.id.content1));
                    line3.add(findViewById(R.id.content2));
                    ArrayList<ImageView> line4=new ArrayList<>();
                    line4.add(findViewById(R.id.star0));
                    line4.add(findViewById(R.id.star1));
                    line4.add(findViewById(R.id.star2));
                    ArrayList<CardView> line5=new ArrayList<>();
                    line5.add(findViewById(R.id.review0));
                    line5.add(findViewById(R.id.review1));
                    line5.add(findViewById(R.id.review2));
//                    JSONArray reviews=response.getJSONArray("results");
                    for(int i=0;i<3;i++) {
                        if(i < reviews.length()) {
                            JSONObject review = reviews.getJSONObject(i);
                            String time = review.getString("created_at");
                            String author="";
                            try {
//                                JSONObject author_details=review.getJSONObject("author_details");
                                author = review.getString("username");
                            }catch(JSONException e){
                                author = "anynomous user";
                            }
                            final String authort=author;
                            DateFormat df5 = new SimpleDateFormat("E, MMM dd yyyy");
                            String date=df5.format(Date.from(LocalDateTime.parse(time.substring(0,time.length()-1)).atZone(ZoneId.systemDefault()).toInstant()));
                            line1.get(i).setText("By "+author+" on "+date);
                            String content=review.getString("content");
                            if(!content.equals("null")&&!content.equals("")){
                                line3.get(i).setText(content);
                            }
                            String rate="";
                            try{
//                                JSONObject rates=review.getJSONObject("author_details");
                                rate = review.getString("rating");
                                if(!rate.equals("null")){
                                    double ratee = Double.parseDouble(rate) / 2;
                                    line2.get(i).setText(ratee + "/5");
                                }
                                else{
                                    line2.get(i).setText("0/5");
                                }
                            }catch (JSONException e){
                                line2.get(i).setText("0/5");
                            }
                            final String ratet=rate;
                            line5.get(i).setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View view){
                                    Intent intent = new Intent();
                                    intent.setClass(view.getContext(), Review.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                    intent.putExtra("rate", ratet);
                                    intent.putExtra("content",content);
                                    intent.putExtra("date",date);
                                    intent.putExtra("author",authort);
                                    view.getContext().startActivity(intent);
                                }
                            });
                        }else{
                            for(int j=i;j<3;j++){
                                line1.get(j).setVisibility(View.GONE);
                                line2.get(j).setVisibility(View.GONE);
                                line3.get(j).setVisibility(View.GONE);
                                line4.get(j).setVisibility(View.GONE);
                            }
                            if(i==0){
                                findViewById(R.id.review).setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    findViewById(R.id.review0).setVisibility(View.GONE);
                    findViewById(R.id.review1).setVisibility(View.GONE);
                    findViewById(R.id.review2).setVisibility(View.GONE);
                    findViewById(R.id.review).setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                findViewById(R.id.review0).setVisibility(View.GONE);
                findViewById(R.id.review1).setVisibility(View.GONE);
                findViewById(R.id.review2).setVisibility(View.GONE);
                findViewById(R.id.review).setVisibility(View.GONE);
            }
        });
        mQueue.add(request3);
    }
    protected void recommendationload(final VolleyCallback1 callback){
//        String url = "https://api.themoviedb.org/3/movie/"+id.substring(1,id.length())+"/recommendations?api_key=38ed65c377771b36628b1df55ae458b9&language=en-US&page=1";
        String url="http://xueyuhw9.wl.r.appspot.com/recommendation/"+id.substring(1,id.length());
        JsonArrayRequest request4 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray recommendations) {
                try {
//                    JSONArray recommendations=response.getJSONArray("results");
                    for(int i=0;i<10;i++){
                        JSONObject recom=recommendations.getJSONObject(i);
                        String id=recom.getString("id");
                        String title=recom.getString("title");
                        String poster="";
                        try {
                            String poster1=recom.getString("poster_path");
                            if(!poster1.equals("null"))
                                poster = "https://image.tmdb.org/t/p/w500" +poster1;
                            else
                                poster ="https://cinemaone.net/images/movie_placeholder.png";
                        }catch (JSONException e){
                            poster ="https://cinemaone.net/images/movie_placeholder.png";
                        }
                        recommendationlist.add(new SliderData(id,poster,title));
                    }
                    callback.onSuccess(recommendationlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                    findViewById(R.id.recommendedtitle).setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    scroll.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                findViewById(R.id.recommendedtitle).setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                scroll.setVisibility(View.VISIBLE);
            }
        });
        mQueue.add(request4);
    }
    private interface VolleyCallback{
        void onSuccess(String ss);
    }
    private interface VolleyCallback1{
        void onSuccess(ArrayList<SliderData> list);
    }
}