package ru.mospolytech.lab1;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("news/v2/getMainPageForRubric?rubric_id=1000")
    Observable<NewsList> newslist(@Query("geo_id") String geo_id);

    @GET("news/v2/getNewsById")
    Observable<NewsDetail> news(@Query("id") String id);
}
