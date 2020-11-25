package ru.mospolytech.lab1;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    List<NewsDetail> list;

    public ListAdapter(Context context, List<NewsDetail> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsDetail news = list.get(position);
        holder.factIdText.setText(news.title);
        holder.sourceView.setText("Источник: "+ news.source);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(news.date * 1000L);
        String date = DateFormat.format("dd.MM.yyyy hh:mm", cal).toString();


            holder.dateNews.setText("Дата: "+ date );



        news.image_A.delete(0,7);
        Log.d(TAG, "onBindViewHolder: " + news.image_A);
        Glide.with(context).load("https://" + news.image_A + "").into(holder.factImage);
        holder.item.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsActivity.class);
            intent.putExtra("newsid", news.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView factImage;
        TextView factIdText;
        TextView dateNews;
        TextView sourceView;
        LinearLayout item;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            factImage = itemView.findViewById(R.id.newsImage);
            factIdText = itemView.findViewById(R.id.newsIdText);
            sourceView = itemView.findViewById(R.id.sourceView);
            dateNews = itemView.findViewById(R.id.dateNews);
            item = itemView.findViewById(R.id.item);
        }
    }
}
