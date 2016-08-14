package com.kuassivi.frp.domain.model;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.domain.contract.IFavourite;
import com.kuassivi.frp.domain.contract.IGenre;
import com.kuassivi.frp.domain.contract.IViewModel;
import com.kuassivi.frp.domain.contract.IWatcher;

public class VideoViewModel implements IViewModel, IGenre, IWatcher, IFavourite, Comparable<IViewModel> {

    private int id;
    private String title;
    private Genre genre;
    private Integer watchers;
    private boolean favourite;

    public VideoViewModel(int id, String title, Genre genre, Integer watchers) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.watchers = watchers;
    }

    VideoViewModel(IViewModel viewModel, boolean favourite) {
        VideoViewModel videoViewModel = (VideoViewModel) viewModel;
        this.id = videoViewModel.id;
        this.title = videoViewModel.title;
        this.genre = videoViewModel.genre;
        this.watchers = videoViewModel.watchers;
        this.favourite = favourite;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public Genre getGenre() {
        return genre;
    }

    @Override
    public Integer getWatchers() {
        return watchers;
    }

    @Override
    public boolean isFavourite() {
        return favourite;
    }

    @Override
    public int compareTo(IViewModel o) {
        return getId() > o.getId() ? 0 : -1;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VideoViewModel{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", genre=").append(genre);
        sb.append(", watchers=").append(watchers);
        sb.append(", favourite=").append(favourite);
        sb.append('}');
        return sb.toString();
    }
}
