package net.soeur.qqbot.message;

import net.soeur.qqbot.utils.Util;

public class Color {

    public static String add(String message, TextColor textColor) {
        return add(message, textColor, BgColor.NO);
    }

    public static String add(String message, TextColor textcolor, BgColor bgcolor) {
        if (Util.isLinux()) {
            return "\033[" + bgcolor + ";" + textcolor + "m" + message + "\033[0m";
        } else {
            return message;
        }
    }


    public enum TextColor {

        BLACK("30"),
        RED("31"),
        GREEN("32"),
        YELLOW("33"),
        BLUE("34"),
        PURPLE("35"),
        DEEP_GREEN("36"),
        WHITE("37");

        private String code;

        TextColor(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }


    public enum BgColor {

        NO("0"),
        BLACK("40"),
        RED("41"),
        GREEN("42"),
        YELLOW("43"),
        BLUE("44"),
        PURPLE("45"),
        DEEP_GREEN("46"),
        WHITE("47");

        private String code;

        BgColor(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

}

