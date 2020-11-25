package ru.mospolytech.lab1;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NewsDetail {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("textPreview")
    String textPreview;

    @SerializedName("date")
    int date;

    @SerializedName("image_A")
    StringBuffer image_A;

    @SerializedName("image_full")
    StringBuffer image_full;

    @SerializedName("source")
    String source;
}
