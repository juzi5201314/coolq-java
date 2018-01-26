package net.soeur.qqbot.api;

import net.soeur.qqbot.utils.Util;
import net.soeur.qqbot.websocket.WebSocketClient;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Api {

    public static void run(String action, Map<String, Object> params) {
        Map <String, Object> data = new HashMap<String, Object>();
        data.put("action", action.trim());
        data.put("params", params);
        WebSocketClient.Client.getClient("api").send(new JSONObject(data).toString());
    }

    public static Map<String, Object> get_stranger_info(String user_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", user_id);
        run("get_stranger_info", map);
        return null;
    }

    public static void send_private_msg(final String message, final String user_id, final boolean auto_escape) {
        run(Util.getMethodName(), new HashMap<String, Object>(){
            {
                put("message", message);
                put("user_id", user_id);
                put("auto_escape", auto_escape);
            }
        });
    }

    public static void send_private_msg(String message, String user_id) {
        send_private_msg(message, user_id, false);
    }



}
