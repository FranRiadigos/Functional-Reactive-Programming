package com.kuassivi.frp;

import static com.kuassivi.frp.util.Logger.log;

public class Part1_0_Drm {

    public static void main(String[] args) {

        Rx1_0_DrmWrapper drmWrapper = new Rx1_0_DrmWrapper("#secret_key#");

        drmWrapper.asSingle()
                  .map(String::length)
                  .subscribe(length -> log("Password length: " + length));
    }
}
