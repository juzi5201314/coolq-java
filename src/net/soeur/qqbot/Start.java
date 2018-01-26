package net.soeur.qqbot;

import net.soeur.qqbot.api.Api;
import net.soeur.qqbot.command.Command;
import net.soeur.qqbot.command.CommandSender;
import net.soeur.qqbot.command.defaults.ExecCommand;
import net.soeur.qqbot.command.defaults.StatusCommand;
import net.soeur.qqbot.command.sender.SenderAdmin;
import net.soeur.qqbot.listen.Listener;
import net.soeur.qqbot.message.Logger;
import net.soeur.qqbot.websocket.WebSocketClient;

import java.util.Scanner;

public class Start {

    public static void main(String[] args) throws Exception {
        //初始化配置文件
        Config.init();
        //初始化监听器
        initListener();
        //注册指令
        registerCommands();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String cmd = scanner.nextLine();
            if (cmd.equals("stop"))
                break;
            else if (Command.isCommand(cmd))
            {
                Command.exec(cmd, new SenderAdmin());
                /*
                Pattern pattern = Pattern.compile("\"([\\s\\S]*)\"");
                Matcher matcher = pattern.matcher(arg);
                matcher.find();
                Logger.debug(matcher.group(1));
                */
                //Api.run(cmd.substring(2).trim(), new JSONObject(arg).toMap());
            }
        }

    }

    private static void initListener() {
        if (!Config.read("websocket_clinet").toString().isEmpty()) {
            try{
                WebSocketClient.Client.start(Config.read("websocket_listen").toString().trim(), "listener");
                if (Config.read("websocket_api_persistent_connections").equals("true"))
                WebSocketClient.Client.start(Config.read("websocket_api").toString().trim(), "api");
            }catch (Exception e) {
                Logger.throwException(e);
            }
        }else
            Logger.error("找不到合适的监听途径，监听器启动失败");
        Listener.init();
    }

    private static void registerCommands() {
        Command.register("status", new StatusCommand());
        Command.register("exec", new ExecCommand());
    }

}
