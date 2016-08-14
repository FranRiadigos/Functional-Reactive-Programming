package com.kuassivi.frp.service;

import com.kuassivi.frp.data.DummyData;
import com.kuassivi.frp.data.mapper.VideoMapper;
import com.kuassivi.frp.domain.model.VideoViewModel;
import rx.Observable;

import java.util.concurrent.TimeUnit;

import static com.kuassivi.frp.util.Logger.showThread;

public class VideoRepository {

    public Observable<VideoViewModel> getVideos() {
        return Observable.from(DummyData.videos)
                         .doOnSubscribe(() -> showThread("Subscribed to retrieve Videos"))
                         .delay(2000, TimeUnit.MILLISECONDS)
                         .map(VideoMapper::toViewModel);
    }

    public Observable<VideoViewModel> getVideosWithErrors() {
        return Observable.from(DummyData.videosWithErrors)
                         .doOnSubscribe(() -> showThread("Subscribed to retrieve Videos"))
                         .delay(1500, TimeUnit.MILLISECONDS)
                         .map(VideoMapper::toViewModel);
    }
}
