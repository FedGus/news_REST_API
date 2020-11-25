package ru.mospolytech.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsActivity extends AppCompatActivity {
    TextView newsHeader;
    TextView newsText;
    TextView newsBody;
    ImageView newsImageFull;
    ApiInterface api;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsHeader = findViewById(R.id.newsHeader);
        newsText = findViewById(R.id.newsText);
        newsBody = findViewById(R.id.newsBody);
        newsImageFull = findViewById(R.id.newsImageFull);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        if (getIntent().getExtras() != null){
            disposables.add(
                    api.news(getIntent().getStringExtra("newsid"))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (fact) -> {
                                        newsHeader.setText(fact.title);
                                        newsBody.setText(fact.textPreview);

                                        fact.image_full.delete(0,7);
                                        Log.d(TAG, "onBindViewHolder: " + fact.image_full);
                                        Glide.with(this).load("https://" + fact.image_full + "").into(newsImageFull);
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
