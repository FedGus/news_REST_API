package ru.mospolytech.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsActivity extends AppCompatActivity {
    TextView newsHeader;
    TextView newsBody;
    TextView newsSource;
    TextView newsDate;
    ImageView newsImageFull;
    ApiInterface api;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsHeader = findViewById(R.id.newsHeader);
        newsBody = findViewById(R.id.newsBody);
        newsSource = findViewById(R.id.newsSource);
        newsDate = findViewById(R.id.newsDate);
        newsImageFull = findViewById(R.id.newsImageFull);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        if (getIntent().getExtras() != null){
            disposables.add(
                    api.news(getIntent().getStringExtra("newsid"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (news) -> {
                                        newsHeader.setText(news.title);
                                        newsBody.setText(news.textPreview);
                                        newsSource.setText("Источник: "+ news.source);

                                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                        cal.setTimeInMillis(news.date * 1000L);
                                        String date = DateFormat.format("dd.MM.yyyy hh:mm", cal).toString();
                                        newsDate.setText("Дата: "+ date );

                                        news.image_A.delete(0,7);
                                        Log.d(TAG, "onBindViewHolder: " + news.image_A);
                                        Glide.with(this).load("https://" + news.image_A + "").into(newsImageFull);
                                    },
                                    (error) -> {
                                        error.printStackTrace();
                                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
