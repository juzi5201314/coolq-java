package net.soeur.qqbot.listen;

import net.soeur.qqbot.Config;
import net.soeur.qqbot.api.Api;
import net.soeur.qqbot.command.Command;
import net.soeur.qqbot.command.sender.SenderUser;
import net.soeur.qqbot.message.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Listener {

    private static Map<String, String> private_type = new HashMap<>();
    private static Map<String, String> group_type = new HashMap<>();

    public static void init() {
        private_type.put("friend", "好友");
        private_type.put("group", "群临时会话");
        private_type.put("discuss", "讨论组临时会话");

        group_type.put("normal", "群消息");
        group_type.put("anonymous", "群匿名消息");
        group_type.put("notice", "群系统消息");
        group_type.put("discuss", "讨论组消息");
    }

    public static void call(String json) {
        try {
            long nt = System.nanoTime();
            JSONObject jsonObject = new JSONObject(json);
            if (Config.read("debug").equals("true"))
            analysis(jsonObject);
            call(jsonObject.toMap());
            Logger.debug("成功处理一条信息，耗时 " + ((System.nanoTime() - nt) / 1000000.00) + "ms");
            //Api.run(json.substring(2).trim(), new JSONObject(arg).toMap());
        }catch (Exception e) {
            Logger.debug("message: " + json + " 无法解析。error: " + e.getMessage());
        }
    }

    private static void call(Map<String, Object> obj) {

    }

    private static String analysis(JSONObject json) {
        if (!json.has("post_type")){
            Logger.debug("收到一条错误的上报信息" + json.toString());
        }
        switch (json.get("post_type").toString().trim()) {
            case "message":
                StringBuffer msg = new StringBuffer(json.getString("message"));
                msg.append(" <" + json.get("user_id") + ">");
                if (Command.isCommand(msg.toString()))
                    Command.exec(msg.toString(), new SenderUser(json.get("user_id").toString()));

                switch (json.getString("message_type")) {
                    case "private":
                        msg.append("[" + private_type.get(json.getString("sub_type")) + "]");
                        break;
                    case "group":
                        msg.append("[" + group_type.get(json.getString("sub_type")) + "]");
                        break;
                    case "discuss":
                        msg.append("[" + group_type.get("discuss") + "]");
                        break;
                }

                Logger.info(msg.toString());
                break;
        }
        return "";
    }


}
