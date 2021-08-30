package com.example.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NewsItemClicked {
    RecyclerView recycle;
    NewsListAdapter mAdapt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle = (RecyclerView)findViewById(R.id.recycle);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        mAdapt = new NewsListAdapter(this);
        recycle.setAdapter(mAdapt);
        fetchData();


    }
    private void fetchData(){
        ArrayList<News> newsArray = new ArrayList<>();
        String url ="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
        JsonObjectRequest newsObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");
                            int size = response.getInt("totalResults");

                            for(int i=0;i<size;i++){
                                News temp = new News();
                                 temp.title = jsonArray.getJSONObject(i).getString("title");
                                temp.author = jsonArray.getJSONObject(i).getString("author");
                                temp.url = jsonArray.getJSONObject(i).getString("url");
                                temp.imageUrl = jsonArray.getJSONObject(i).getString("urlToImage");
                                newsArray.add(temp);

                            }
                            mAdapt.updatedNews(newsArray);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }


                });


        MySingleton.getInstance(this).addToRequestQueue(newsObjectRequest);

    }

    @Override
    public void onItemClicked(String item) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item));

    }
}