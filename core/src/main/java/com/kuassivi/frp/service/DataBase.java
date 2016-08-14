package com.kuassivi.frp.service;

import com.kuassivi.frp.domain.contract.IViewModel;
import com.kuassivi.frp.domain.model.ViewModel;
import com.kuassivi.frp.util.Logger;

import static com.kuassivi.frp.util.Logger.debug;
import static com.kuassivi.frp.util.ThreadUtil.sleep;

public class DataBase {

    public static <T> T checkAndUpdateForFavourite(T object) {
        sleep(1000, 5000);
        IViewModel viewModel = (IViewModel) object;
        if(Logger.DEBUG)
            debug("Mapping an update with Favourite: " + viewModel.getId());
        else
            Logger.showThread("Video Id: " + viewModel.getId());
        return new ViewModel.Builder<>(object, (viewModel.getId() % 3 == 0)).build();
    }
}
