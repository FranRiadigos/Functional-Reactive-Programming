package com.kuassivi.frp;

import com.kuassivi.frp.api.DrmAPI;
import com.kuassivi.frp.api.DrmError;
import com.kuassivi.frp.api.DrmListener;
import com.kuassivi.frp.api.DrmResponse;
import rx.Single;
import rx.SingleSubscriber;

import static com.kuassivi.frp.util.Logger.log;

public class Rx1_0_DrmWrapper {

    private String secretKey;

    public Rx1_0_DrmWrapper(String data) {
        this.secretKey = data;
    }

    public Single<String> asSingle() {
        return Single.create(new Single.OnSubscribe<String>() {
            @Override
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                try {
                    new DrmAPI(new DrmListener() {
                        @Override
                        public void success(DrmResponse response) {
                            log(response.getData());
                            singleSubscriber.onSuccess(response.getData());
                        }

                        @Override
                        public void error(DrmError error) {
                            singleSubscriber.onError(new Error(error.getErrorMessage()));
                        }
                    }).start(secretKey);
                } catch (Exception ex) {
                    singleSubscriber.onError(ex);
                }
            }
        });
    }
}
