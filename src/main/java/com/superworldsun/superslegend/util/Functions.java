package com.superworldsun.superslegend.util;

public class Functions {
    private Functions() {}


    public static void repeat(int times, Runnable action) {
        while (times > 0) {
            action.run();
            times--;
        }
    }

}
