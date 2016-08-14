package com.kuassivi.frp.data;

import com.kuassivi.frp.domain.Genre;

public class Video {

    private int id;
    private String title;
    private Genre genre;
    private Integer watchers;

    public Video(int id, String title, Genre genre, Integer watchers) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.watchers = watchers;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public Integer getWatchers() {
        return watchers;
    }
}
