package com.kuassivi.frp.data.mapper;

import com.kuassivi.frp.data.Video;
import com.kuassivi.frp.domain.model.VideoViewModel;

import static com.kuassivi.frp.util.Logger.debug;

public class VideoMapper {

    public static VideoViewModel toViewModel(Video video) {
        debug("- Mapping Video to VideoViewModel");
        return new VideoViewModel(
                video.getId(),
                video.getTitle(),
                video.getGenre(),
                video.getWatchers());
    }
}
