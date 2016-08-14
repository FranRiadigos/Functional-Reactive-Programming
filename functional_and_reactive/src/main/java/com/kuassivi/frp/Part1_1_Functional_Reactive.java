package com.kuassivi.frp;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.executor.MainExecutor;
import com.kuassivi.frp.service.VideoRepository;
import com.kuassivi.frp.util.LoadingSpinner;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static com.kuassivi.frp.domain.fn.Filter.byGenre;

@SuppressWarnings({"Convert2MethodRef", "WeakerAccess"})
public class Part1_1_Functional_Reactive {

    static MainExecutor mainExecutor = new MainExecutor();
    static Scheduler main;

    public static void main(String[] args) throws InterruptedException {

        Part1_1_Functional_Reactive app = new Part1_1_Functional_Reactive();

        main = Schedulers.from(mainExecutor);

        app.onCreate();

        mainExecutor.runLoop();
    }

    private void onCreate() {

        VideoRepository repository = new VideoRepository();

        repository.getVideos()
                  .filter(viewModel -> byGenre(viewModel, Genre.ADVENTURE))
                  .subscribeOn(Schedulers.io())
                  .observeOn(main)
                  .doAfterTerminate(mainExecutor::shutdown)
                  .compose(LoadingSpinner.attach())
                  .subscribe(System.out::println);
    }
}
