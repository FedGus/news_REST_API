package ru.mospolytech.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    ListAdapter adapter;
    List<NewsDetail> list;
    ApiInterface api;
    private CompositeDisposable disposables;
    private String geo_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        adapter = new ListAdapter(this, list);
        recyclerView = findViewById(R.id.list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        this.onClick(this.recyclerView);

        Spinner aSpinner = findViewById(R.id.spinner);

        ArrayList<Cities> cities_list = new ArrayList<Cities>();
        cities_list.add(new Cities("2552", "Москва"));
        cities_list.add(new Cities("2427", "Cанкт-Петербург"));
        cities_list.add(new Cities("2715", "Новосибирск"));
        cities_list.add(new Cities("1731", "Волгоград"));
        cities_list.add(new Cities("3358", "Казань"));

        ArrayAdapter<Cities> myAdapter = new ArrayAdapter<Cities>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, cities_list);

        aSpinner.setAdapter(myAdapter);
        aSpinner.setOnItemSelectedListener(this);
    }


    public void onClick(View view){
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

            findViewById(R.id.list).setVisibility(View.GONE);
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

            Cities spn = (Cities) adapterView.getItemAtPosition(position);
            disposables.add(api.newslist(spn.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((facts) -> {

                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        findViewById(R.id.list).setVisibility(View.VISIBLE);
                        list.clear();
                        list.addAll(facts.all);
                        adapter.notifyDataSetChanged();
                    }, (error) -> {
                        Toast.makeText(this, "При поиске возникла ошибка:\n" + error.getMessage(),
                                Toast.LENGTH_LONG).show();
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        findViewById(R.id.list).setVisibility(View.VISIBLE);

                    }));
            Toast.makeText(this, "Показаны новости для региона: " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
