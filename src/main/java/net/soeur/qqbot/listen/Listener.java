package net.soeur.qqbot.listen;

import net.soeur.qqbot.Config;
import net.soeur.qqbot.command.Command;
import net.soeur.qqbot.command.sender.SenderUser;
import net.soeur.qqbot.message.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Listener {

    private static Map<Listen, List<Events>> listeners = new HashMap<Listen, List<Events>>();

    public static void call(String json) {
        try {
            long nt = System.nanoTime();
            JSONObject jsonObject = new JSONObject(json);
            analysis(jsonObject);
            Logger.debug("成功处理一条信息，耗时 " + ((System.nanoTime() - nt) / 1000000.00) + "ms");
        }catch (Exception e) {
            Logger.debug("message: " + json + " 无法解析。error: " + e.getMessage());
        }
    }

    private static void analysis(JSONObject json) {
        if (!json.has("post_type")){
            Logger.debug("收到一条错误的上报信息" + json.toString());
            return;
        }

        Events event = Events.UNKNOWN;

        switch (json.get("post_type").toString().trim()) {
             case "message":

                 //指令
                if (Command.isCommand(json.getString("message"))) {
                     Command.exec(json.getString("message"), new SenderUser(json.get("user_id").toString(), json.isNull("group_id") ? null
                             : json.get("group_id").toString(), json.isNull("discuss_id") ? null : json.get("discuss_id").toString()));
                     return;
                }

                //判断消息类型
                switch (json.getString("message_type")) {
                    //如果是私聊消息
                    case "private":
                         switch (json.getString("sub_type")) {
                              case "friend":
                                   event = Events.MESSAGE_PRIVATE_FRIEND;
                                   break;
                              case "group":
                                   event = Events.MESSAGE_PRIVATE_GROUP;
                                   break;
                              case "discuss":
                                   event = Events.MESSAGE_PRIVATE_DISCUSS;
                                   break;
                         }
                        break;
                    //如果是群消息
                    case "group":
                         switch (json.getString("sub_type")) {
                              case "normal":
                                   event = Events.MESSAGE_GROUP_NORMAL;
                                   break;
                              case "anonymous":
                                   event = Events.MESSAGE_GROUP_ANONYMOUS;
                                   break;
                              case "notice":
                                   event = Events.MESSAGE_GROUP_NOTICE;
                                   break;
                         }
                        break;
                    //如果是讨论组消息
                    case "discuss":
                         event = Events.MESSAGE_DISCUSS;
                         break;
                }
                break;

             case "event":
                  switch (json.getString("event")) {
                       case "group_upload":
                            event = Events.EVENT_GROUP_UPLOAD;
                            break;
                       case "group_admin":
                            switch (json.getString("sub_type")) {
                                 case "set":
                                      event = Events.EVENT_GROUP_ADMIN_SET;
                                      break;
                                 case  "unset":
                                      event = Events.EVENT_GROUP_ADMIN_UNSET;
                                      break;
                            }
                            break;
                       case "group_decrease":
                            switch (json.getString("sub_type")) {
                                 case "leave":
                                      event = Events.EVENT_GROUP_DECREASE_LEAVE;
                                      break;
                                 case "kick":
                                      event = Events.EVENT_GROUP_DECREASE_KICK;
                                      break;
                                 case "kick_me":
                                      event = Events.EVENT_GROUP_DECREASE_KICK_ME;
                                      break;
                            }
                            break;
                       case "group_increase":
                            switch (json.getString("sub_type")) {
                                 case "approve":
                                      event = Events.EVENT_GROUP_INCREASE_ADDROVE;
                                      break;
                                 case "invite":
                                      event = Events.EVENT_GROUP_INCREASE_INVITE;
                                      break;
                            }
                            break;
                       case "friend_add":
                            event = Events.EVENT_FRIEND_ADD;
                            break;
                  }
                break;

             case "request":
                  switch (json.getString("request_type")) {
                       case "friend":
                            event = Events.REQUEST_FRIEND;
                            break;
                       case "group":
                            switch (json.getString("sub_type")) {
                                 case "add":
                                      event = Events.REQUEST_GROUP_ADD;
                                      break;
                                 case "invite":
                                      event = Events.REQUEST_GROUP_INVITE;
                                      break;
                            }
                            break;
                  }
                  break;
        }

        for (Listen list : listeners.keySet()) {
             if (listeners.get(list).contains(event))
                  list.onEvent(event, json.toMap());
        }

    }

    public static void register(Listen listener, List<Events> list) {
         if (hasListener(listener))
              Logger.debug("模块 " + listener.getModel().getName() + " 试图注册一个已经存在的监听器");
         else
              listeners.put(listener, list);
    }

    private static boolean hasListener(Listen listener) {
         return listeners.containsKey(listener);
    }

}
