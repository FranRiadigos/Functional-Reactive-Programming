package com.kuassivi.frp;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.executor.MainExecutor;
import com.kuassivi.frp.service.VideoRepository;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static com.kuassivi.frp.domain.fn.Filter.byGenre;

@SuppressWarnings({"Convert2MethodRef", "WeakerAccess"})
public class Part1_1_Functional {

    static MainExecutor mainExecutor = new MainExecutor();
    static Scheduler main;

    public static void main(String[] args) throws InterruptedException {

        Part1_1_Functional app = new Part1_1_Functional();

        main = Schedulers.from(mainExecutor);

        app.onCreate();

        mainExecutor.runLoop();
    }

    private void onCreate() {

        VideoRepository repository = new VideoRepository();

        repository.getVideos()
                  .filter(viewModel -> byGenre(viewModel, Genre.ADVENTURE))
                  .observeOn(main)
                  .doAfterTerminate(mainExecutor::shutdown)
                  .subscribe(System.out::println);
    }
}
