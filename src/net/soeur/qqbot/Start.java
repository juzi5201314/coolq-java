package net.soeur.qqbot;

import net.soeur.qqbot.api.Api;
import net.soeur.qqbot.command.Command;
import net.soeur.qqbot.command.CommandSender;
import net.soeur.qqbot.command.defaults.ExecCommand;
import net.soeur.qqbot.command.defaults.StatusCommand;
import net.soeur.qqbot.command.sender.SenderAdmin;
import net.soeur.qqbot.listen.Listener;
import net.soeur.qqbot.message.Logger;
import net.soeur.qqbot.model.Model;
import net.soeur.qqbot.websocket.WebSocketClient;
import org.json.JSONObject;

import java.util.Scanner;

public class Start {

    public static void main(String[] args) throws Exception {
        //初始化配置文件
        Config.init();
        //初始化监听器
        initListener();
        //注册指令
        registerCommands();
        //加载模块
        loadModel();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String cmd = scanner.nextLine();
            if (cmd.equals("stop"))
                break;
            else if (Command.isCommand(cmd))
            {
                Command.exec(cmd, new SenderAdmin());
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
    }

    private static void registerCommands() {
        Command.register("status", new StatusCommand());
        Command.register("exec", new ExecCommand());
    }

    private static void loadModel() {
        JSONObject jsonObject = (JSONObject) Config.read("model");
        for (String name : jsonObject.keySet()) {
            try {
                Class<? extends Model> subclass = Class.forName(jsonObject.getString(name)).asSubclass(Model.class);
                Model model = subclass.newInstance();
            }catch (ClassNotFoundException e) {
                Logger.warning("无法加载模块 " + name + "，原因：找不到主类" + e.getMessage());
            }catch (IllegalAccessException e) {
                Logger.throwException(e);
            }catch (InstantiationException e) {
                Logger.throwException(e);
            }
        }
    }

}
