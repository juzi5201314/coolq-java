package net.soeur.qqbot.message;

import net.soeur.qqbot.Config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public static void throwException(Throwable e) {
        if (Config.read("debug").equals("true")) {
            e.printStackTrace();
        }else {
            error(e.getMessage());
        }
    }

    public static void info(String message) {
        out(message, LogType.INFO);
    }

    public static void warning(String message) {
        out(message, LogType.WARNING);
    }

    public static void error(String message) {
        out(message, LogType.ERROR);
    }

    public static void alert(String message) {
        out(message, LogType.ALERT);
    }

    public static void debug(String message) {
        if (Config.read("debug").equals("true"))
            out(message, LogType.DEBUG);
    }

    private static void out(String message, LogType type) {
        StringBuffer hear = new StringBuffer();
        switch (type) {
            case INFO:
                hear.append(type.toString());
                break;
            case ALERT:
                hear = new StringBuffer(Color.add(type.toString(), Color.TextColor.BLACK, Color.BgColor.BLUE));
                break;
            case DEBUG:
                hear = new StringBuffer(Color.add(type.toString(), Color.TextColor.BLACK, Color.BgColor.WHITE));
                break;
            case ERROR:
                hear = new StringBuffer(Color.add(type.toString(), Color.TextColor.BLACK, Color.BgColor.RED));
                break;
            case WARNING:
                hear = new StringBuffer(Color.add(type.toString(), Color.TextColor.RED, Color.BgColor.YELLOW));
                break;
            default:
                throw new RuntimeException("Log type error, no type: " + type);
        }
        System.out.println(hear.append("<" + (new SimpleDateFormat("hh:mm:ss")).format(new Date()) + "> ").append(message));
        message = null;
    }

    enum LogType {

        INFO("info"),
        DEBUG("deBug"),
        WARNING("warning"),
        ERROR("error"),
        ALERT("alert");

        final public static String COLOR_INFO = "";

        private StringBuilder msg;

        LogType(String msg) {
            this.msg = new StringBuilder(msg);
        }

        @Override
        public String toString() {
            return "[" + msg + "]";
        }
    }

}
