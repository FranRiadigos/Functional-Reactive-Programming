package com.kuassivi.frp;

import rx.Observable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The following class exemplifies how we can code by following a declarative style.
 */
public class Part1_2_Declarative {

    public <T, E> T doImperative(Map<T, E> map, E value) {
        // How it does?
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return null;
    }

    public <T, E> Optional<T> doDeclarative(Map<T, E> map, E value) {
        // What it does?
        return map.entrySet()
                  .stream()
                  .filter(entry -> Objects.equals(entry.getValue(), value))
                  .map(Map.Entry::getKey)
                  .findFirst();
    }

    public <T, E> Observable<T> doRxDeclarative(Map<T, E> map, E value) {
        // What it does?
        return Observable.from(map.entrySet())
                         .filter(entry -> Objects.equals(entry.getValue(), value))
                         .map(Map.Entry::getKey)
                         .first();
    }

    public <T, E> T doRxDeclarativeAndReturnValue(Map<T, E> map, E value) {
        // What it does?
        return Observable.from(map.entrySet())
                         .filter(entry -> Objects.equals(entry.getValue(), value))
                         .map(Map.Entry::getKey)
                         .toBlocking()
                         .firstOrDefault(null);
    }
}