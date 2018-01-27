package net.soeur.qqbot.command;

import java.util.HashMap;
import java.util.Map;

public class Command {

    final private static String COMMAND_NOT_FOUND = "命令不存在";
    final public static String NOT_POWER = "你没有权限";

    private static Map<String, CommandReceiver> commands = new HashMap<String, CommandReceiver>();

    public static void exec(String command, CommandSender sender) {
        String[] args = getArgs(command);
        if (!has(args[0])) {
            sender.sendMessage(COMMAND_NOT_FOUND);
            return;
        }
        commands.get(args[0]).exec(sender, args);
    }

    private static String[] getArgs(String command) {
        return command.replace("./", "").split(" ");
    }

    public static boolean isCommand(String string) {
        return string.length() > 2 && string.substring(0, 2).equals("./");
    }

    public static boolean register(String name, CommandReceiver receiver) {
        if (has(name))
            return false;
        else
            commands.put(name, receiver);
        return true;
    }

    public static boolean has(String name) {
        return commands.containsKey(name);
    }

}
