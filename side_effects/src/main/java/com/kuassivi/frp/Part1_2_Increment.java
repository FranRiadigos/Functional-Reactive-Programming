package com.kuassivi.frp;

import rx.Observable;

/**
 * The following class exemplifies how a side effect modifies a downstream operation.
 */
public class Part1_2_Increment {

    public static void main(String[] args) {

        int a = 0, b = 0;

        System.out.println("- Before execution");
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        int c = Observable.just(a)
                          .map(integer -> ++integer) // no side effect
                          //.map(...)                // "a" haven't changed
                          .toBlocking().first();

        int d = ++b; // there is a side effect, it changes the value of b

        System.out.println("- After execution");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
    }
}
