package com.kuassivi.frp;

import com.kuassivi.frp.api.PlayerEventAPI;
import com.kuassivi.frp.event.InitEvent;
import com.kuassivi.frp.event.PauseEvent;
import com.kuassivi.frp.event.PlayEvent;
import com.kuassivi.frp.event.StopEvent;
import com.kuassivi.frp.util.Logger;
import com.kuassivi.frp.util.ThreadUtil;
import rx.functions.Action1;

import static com.kuassivi.frp.util.Logger.log;

public class Part1_2_Events {

    public static void main(String[] args) {

        Rx1_2_PlayerEventBus playerBus = Rx1_2_PlayerEventBus.getInstance();
        PlayerEventAPI player = playerBus.getPlayer();

        playerBus.ofEvent(InitEvent.class)
                     .subscribe(initEvent -> {
                         log("Player initialised");
                         player.play();
                     });

        playerBus.ofEvent(PlayEvent.class).subscribe(playAction);
        playerBus.ofEvent(PauseEvent.class).subscribe(pauseAction);
        playerBus.ofEvent(StopEvent.class).subscribe(stopAction);

        player.initialise();

        // User click on Pause
        ThreadUtil.postpone(player::pause, 1100);

        // User click on Play
        ThreadUtil.postpone(player::play, 2100);
    }

    private static Action1<PlayEvent> playAction = Logger::log;
    private static Action1<PauseEvent> pauseAction = Logger::log;
    private static Action1<StopEvent> stopAction = Logger::log;
}
