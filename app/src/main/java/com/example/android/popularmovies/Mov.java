package com.example.android.popularmovies;

import com.google.gson.annotations.SerializedName;

public class Mov {


    @SerializedName("id")
    private String Id;

    @SerializedName("title")
    private String Title;

    @SerializedName("poster_path")
    private String PosterPath;

    public Mov(String mId, String mTitle, String mPoster)
    {
        Id = mId;
        Title = mTitle;
        PosterPath = mPoster;
    }

    public String getId() {
        return Id;
    }

    public void setId(String mId) {
        Id = mId;
    }

    public String getmTitle() {
        return Title;
    }

    public void setmTitle(String mTitle) {
        this.Title = mTitle;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setmPosterPath(String mPosterPath) {
        this.PosterPath = mPosterPath;
    }
}

