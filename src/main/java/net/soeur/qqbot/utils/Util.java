package net.soeur.qqbot.utils;

import java.io.File;

public class Util {

    final public static String BASE_DIR = System.getProperty("user.dir") + File.separator;

    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

}
