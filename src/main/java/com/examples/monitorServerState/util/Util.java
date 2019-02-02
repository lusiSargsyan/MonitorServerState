package com.examples.monitorServerState.util;

public final class Util {

    public static String getOs() {
        return System.getProperty("os.name").toUpperCase();
    }

    public static boolean isNumeric(String strNum) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }
}
