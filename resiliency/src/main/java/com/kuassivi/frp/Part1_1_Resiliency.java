package com.kuassivi.frp;

import com.kuassivi.frp.data.DummyData;
import com.kuassivi.frp.data.Video;
import com.kuassivi.frp.data.mapper.VideoMapper;
import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.domain.model.VideoViewModel;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

import static com.kuassivi.frp.domain.fn.GenresObs.filterByGenre;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class Part1_1_Resiliency {

    public static void main(String[] args) throws InterruptedException {

        // Imperative way
        List<VideoViewModel> modelList = new ArrayList<>();
        for (Video video : DummyData.videosWithErrors) {
            try {
                VideoViewModel model = VideoMapper.toViewModel(video);
                if(model.getGenre().equals(Genre.ACTION))
                    continue;
                modelList.add(model);
            } catch (Exception ex) {
                // Discard the error
                // Notify 3rd party Crash service...
            }
            // Keep looping
        }

        // Reactive way
        Observable.from(DummyData.videosWithErrors)
                  .map(VideoMapper::toViewModel)
                  .flatMap(videoViewModel ->
                          filterByGenre(Observable.just(videoViewModel), Genre.ACTION)
                                  .onErrorResumeNext(throwable -> {
                                      // Discard the error
                                      // Notify 3rd party Crash service...
                                      return Observable.empty();
                                  }))
                  .subscribe(System.out::println);
    }
}
