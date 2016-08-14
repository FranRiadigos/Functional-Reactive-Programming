package com.kuassivi.frp;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The following class exemplifies how we use to code in an imperative style.
 */
public class Part1_1_Imperative {

    public void doSomething(String[] items) {

        Set<Integer> isoCodes;

        // We understand the code above because we are familiar with this style of programming,
        // but if the code gets huge we will probably waste many time analysing how it works
        // instead of understanding what it just does.

        // Before a refactor
        isoCodes = new HashSet<>();
        int i, len;
        for (i = 0, len = items.length; i < len; i++) {
            String item = items[i].trim();
            if(item.isEmpty()) {
                continue;
            }
            Pattern p = Pattern.compile("[^A-Za-z0-9]");
            Matcher m = p.matcher(item);
            if(m.find()) {
                continue;
            }
            isoCodes.add( findISOCodeByString(item) );
        }

        // After a refactor
        isoCodes = new HashSet<>();
        for (String item : items) {
            if(!item.isEmpty() && hasNoSpecialChars(item)) {
                isoCodes.add( findISOCodeByString(item) );
            }
        }
    }

    private int findISOCodeByString(String str) {
        return 0;
    }

    private boolean hasNoSpecialChars(String str) {
        Pattern p = Pattern.compile("[^A-Za-z0-9]");
        Matcher m = p.matcher(str);
        return m.find();
    }
}
