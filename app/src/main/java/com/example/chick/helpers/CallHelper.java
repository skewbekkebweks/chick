package com.example.chick.helpers;

import java.util.concurrent.Callable;

public class CallHelper {
    @SafeVarargs
    public static void callFunctions(Callable<Void>... functions) {
        for (Callable<Void> function : functions) {
            try {
                function.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
