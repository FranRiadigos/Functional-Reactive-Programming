package com.kuassivi.frp.service;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.domain.model.VideoViewModel;
import rx.Observable;
import rx.schedulers.Schedulers;

import static com.kuassivi.frp.domain.fn.Filter.byGenre;

public class VideoService {

    private Observable<VideoViewModel> obVideos;

    public VideoService() {
        VideoRepository repository = new VideoRepository();
        obVideos = repository.getVideos()
                             .subscribeOn(Schedulers.io())
                             .share();
    }

    public Observable<VideoViewModel> filterBy(Genre genre) {
        return obVideos
                .filter(viewModel -> byGenre(viewModel, genre));
    }
}
