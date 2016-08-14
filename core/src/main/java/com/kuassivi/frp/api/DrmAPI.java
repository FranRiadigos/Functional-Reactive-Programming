package com.kuassivi.frp.api;

import com.kuassivi.frp.util.ThreadUtil;

public class DrmAPI {

    private DrmListener listener;

    public DrmAPI(DrmListener listener) {
        this.listener = listener;
    }

    public void start(String secretKey) {
        ThreadUtil.postpone(() -> {
            listener.success(() -> secretKey + " accepted");
        }, 1000);
    }
}
