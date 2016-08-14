package com.kuassivi.frp.domain.fn;

import com.kuassivi.frp.domain.contract.IWatcher;

import static com.kuassivi.frp.util.Logger.debug;

public class Accumulator {

    public static int byWatchers(int accumulatedValue, IWatcher object) {
        //ThreadUtil.sleep(500);
        debug("Accumulating by Watchers: " + accumulatedValue);
        return accumulatedValue + object.getWatchers();
    }
}
