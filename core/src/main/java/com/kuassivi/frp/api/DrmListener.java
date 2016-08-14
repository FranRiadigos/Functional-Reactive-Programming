package com.kuassivi.frp.api;

public interface DrmListener {
    void success(DrmResponse response);
    void error(DrmError error);
}
