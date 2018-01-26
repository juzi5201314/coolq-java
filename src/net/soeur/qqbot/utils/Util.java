package net.soeur.qqbot.utils;

public class Util {

    public static boolean isLinux() {
        return System.getProperty("utils.name").toLowerCase().contains("linux");
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }



}
