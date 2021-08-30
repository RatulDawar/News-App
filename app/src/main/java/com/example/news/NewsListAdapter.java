package com.example.news;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<newsHolder> {
    ArrayList<News> array;
    NewsItemClicked listener;

    NewsListAdapter( NewsItemClicked listener){
        array = new ArrayList<>();
        this.listener = listener;
     }

    @NonNull
    @Override
    public newsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View converted = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        newsHolder current = new newsHolder(converted);

        converted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(current.url);
            }
        });
        return current;
    }

    @Override
    public void onBindViewHolder(@NonNull newsHolder holder, int position) {
        News currentItem = array.get(position);
        holder.title.setText(currentItem.title);
        holder.url = currentItem.url;
        Glide.with(holder.itemView.getContext()).load(currentItem.imageUrl).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public void updatedNews(ArrayList<News> updatedNews){
        array.clear();
        array.addAll(updatedNews);
        notifyDataSetChanged();
    }

}
class newsHolder extends RecyclerView.ViewHolder{

    public newsHolder(@NonNull View itemView) {
        super(itemView);
    }
    TextView title = itemView.findViewById(R.id.textView);
    ImageView image = itemView.findViewById(R.id.imageView);
    String url;


}
interface NewsItemClicked {
   public void onItemClicked(String item);
}

