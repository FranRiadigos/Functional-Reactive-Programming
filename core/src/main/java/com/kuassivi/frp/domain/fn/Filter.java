package com.kuassivi.frp.domain.fn;

import com.kuassivi.frp.domain.Genre;
import com.kuassivi.frp.domain.contract.IGenre;

import static com.kuassivi.frp.util.Logger.debug;

@SuppressWarnings("WeakerAccess")
public class Filter {

    public static <T extends IGenre> boolean byGenre(T object, Genre genre) {
        debug("Filtering by Genre: " + object.getGenre());
        return object.getGenre().equals(genre);
    }
}
