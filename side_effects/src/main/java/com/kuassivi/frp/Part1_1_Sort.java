package com.kuassivi.frp;

import java.util.Arrays;

/**
 * The following class exemplifies how a side effect can change an outside world variable.
 */
public class Part1_1_Sort {

    public static void main(String[] args) {
        int[] foo = new int[]{2, 4, 3, 1, 5};
        Bar bar = new Bar();
        bar.execute(foo);

        // foo gets reordered outside the Bar class
        System.out.println("foo -> " + Arrays.toString(foo));

        // Output: foo -> [1, 2, 3, 4, 5] // the outside variable becomes ordered...
    }

    private static class Bar {

        void execute(int[] values) {
            Arrays.sort(values); // Side effect
        }
    }
}
