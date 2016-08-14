package com.kuassivi.frp.data;

import com.kuassivi.frp.domain.Genre;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<Video> videos = new ArrayList<>(10);
    static {
        videos.add(new Video(1, "Video 1", Genre.ACTION, 0));
        videos.add(new Video(2, "Video 2", Genre.ADVENTURE, 5));
        videos.add(new Video(3, "Video 3", Genre.ADVENTURE, 0));
        videos.add(new Video(4, "Video 4", Genre.COMEDY, 10));
        videos.add(new Video(5, "Video 5", Genre.SCI_FI, 15));
        videos.add(new Video(6, "Video 6", Genre.DRAMA, 0));
        videos.add(new Video(7, "Video 7", Genre.ACTION, 0));
        videos.add(new Video(8, "Video 8", Genre.FANTASY, 111));
        videos.add(new Video(9, "Video 9", Genre.ADVENTURE, 34));
        videos.add(new Video(10, "Video 10", Genre.COMEDY, 78));
    }

    public static List<Video> videosWithErrors = new ArrayList<>(10);
    static {
        videosWithErrors.add(new Video(1, "Video 1", Genre.ACTION, 1));
        videosWithErrors.add(new Video(2, "Video 2", Genre.ADVENTURE, 5));
        videosWithErrors.add(new Video(3, "Video 3", Genre.ACTION, null));
        videosWithErrors.add(new Video(4, "Video 4", Genre.ACTION, null));
        videosWithErrors.add(new Video(5, "Video 5", Genre.SCI_FI, 15));
        videosWithErrors.add(new Video(6, "Video 6", null, 0));
        videosWithErrors.add(new Video(7, "Video 7", Genre.ACTION, null));
        videosWithErrors.add(new Video(8, "Video 8", Genre.FANTASY, 111));
        videosWithErrors.add(new Video(9, "Video 9", Genre.ACTION, 34));
        videosWithErrors.add(new Video(10, "Video 10", Genre.COMEDY, 78));
    }

}
