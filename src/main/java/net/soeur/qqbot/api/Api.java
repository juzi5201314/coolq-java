package net.soeur.qqbot.api;

import net.soeur.qqbot.utils.Util;
import net.soeur.qqbot.websocket.WebSocketClient;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Api {

    private static long uuid = 0;

    public static long run(String action, Map<String, Object> params) {
        long id = uuid ++;
        Map <String, Object> data = new HashMap<String, Object>();
        data.put("action", action.trim());
        data.put("params", params);
        data.put("echo", id);
        WebSocketClient.Client.getClient("api").send(new JSONObject(data).toString());
        return id;
    }

    public static Map<String, Object> get_stranger_info(String user_id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", user_id);
        run("get_stranger_info", map);
        return null;
    }

    public static long send_private_msg(final String message, final String user_id, final boolean auto_escape) {
        return run("send_private_msg", new HashMap<String, Object>(){
            {
                put("message", message);
                put("user_id", user_id);
                put("auto_escape", auto_escape);
            }
        });
    }

    public static long send_private_msg(final String message, final String user_id) {
        return send_private_msg(message, user_id, false);
    }

    public static long send_group_msg(final String message, final String group_id, final boolean auto_escape) {
        return run("send_group_msg", new HashMap<String, Object>(){
            {
                put("message", message);
                put("group_id", group_id);
                put("auto_escape", auto_escape);
            }
        });
    }

    public static long send_group_msg(final String message, final String group_id) {
        return send_private_msg(message, group_id, false);
    }

    public static long send_discuss_msg(final String message, final String discuss_id, final boolean auto_escape) {
        return run("send_discuss_msg", new HashMap<String, Object>(){
            {
                put("message", message);
                put("discuss_id", discuss_id);
                put("auto_escape", auto_escape);
            }
        });
    }

    public static long send_discuss_msg(final String message, final String discuss_id) {
        return send_private_msg(message, discuss_id, false);
    }

    public static long del_msg(final String message_id) {
        return run("delete_msg", new HashMap<String, Object>(){
            {
                put("message_id", message_id);
            }
        });
    }

    public static long send_like(final String user_id, final int count) {
        return run("send_like", new HashMap<String, Object>(){
            {
                put("user_id", user_id);
                put("times", count);
            }
        });
    }

     public static long set_group_kick(final String group_id, final String user_id, final boolean reject_add_request) {
          return run("set_group_kick", new HashMap<String, Object>(){
               {
                    put("user_id", user_id);
                    put("group_id", group_id);
                    put("reject_add_request", reject_add_request);
               }
          });
     }

     public static long set_group_ban(final String group_id, final String user_id, final int time) {
          return run("set_group_ban", new HashMap<String, Object>(){
               {
                    put("user_id", user_id);
                    put("group_id", group_id);
                    put("duration", time);
               }
          });
     }

     public static long set_group_anonymous_ban(final String group_id, final String flag, final int time) {
          return run("set_group_anonymous_ban", new HashMap<String, Object>(){
               {
                    put("flag", flag);
                    put("group_id", group_id);
                    put("duration", time);
               }
          });
     }

     public static long set_group_whole_ban(final String group_id, final boolean enable) {
          return run("set_group_whole_ban", new HashMap<String, Object>(){
               {
                    put("group_id", group_id);
                    put("enable", enable);
               }
          });
     }

     public static long set_group_admin(final String group_id, final String user_id, final boolean enable) {
          return run("set_group_admin", new HashMap<String, Object>(){
               {
                    put("user_id", user_id);
                    put("group_id", group_id);
                    put("enable", enable);
               }
          });
     }

     public static long set_group_anonymous(final String group_id, final boolean enable) {
          return run("set_group_anonymous", new HashMap<String, Object>(){
               {
                    put("group_id", group_id);
                    put("enable", enable);
               }
          });
     }

     public static long set_group_card(final String group_id, final String user_id, final String card) {
          return run("set_group_card", new HashMap<String, Object>(){
               {
                    put("group_id", group_id);
                    put("card", card);
                    put("user_id", user_id);
               }
          });
     }

     public static long set_group_leave(final String group_id, final boolean is_dismiss) {
          return run("set_group_leave", new HashMap<String, Object>(){
               {
                    put("group_id", group_id);
                    put("is_dismiss", is_dismiss);
               }
          });
     }

     public static long set_group_special_title(final String group_id, final String user_id, final String special_title) {
          return run("set_group_special_title", new HashMap<String, Object>(){
               {
                    put("group_id", group_id);
                    put("special_title", special_title);
                    put("user_id", user_id);
               }
          });
     }

     public static long set_discuss_leave(final String discuss_id) {
          return run("set_discuss_leave", new HashMap<String, Object>(){
               {
                    put("discuss_id", discuss_id);
               }
          });
     }

     public static long set_friend_add_request(final String flag, final boolean approve, final String remark) {
          return run("set_friend_add_request", new HashMap<String, Object>(){
               {
                    put("flag", flag);
                    put("approve", approve);
                    put("remark", remark);
               }
          });
     }

     public static long set_friend_add_request(final String flag, final boolean approve) {
         return set_friend_add_request(flag, approve, "");
     }

     public static long set_group_add_request(final String flag, final boolean approve, final String type, final String reason) {
          return run("set_group_add_request", new HashMap<String, Object>() {
               {
                    put("flag", flag);
                    put("approve", approve);
                    put("type", type);
                    put("reason", reason);
               }
          });
     }

     public static long get_stranger_info(final String user_id, final boolean no_cache) {
          return run("get_stranger_info", new HashMap<String, Object>(){
               {
                    put("no_cache", no_cache);
                    put("user_id", user_id);
               }
          });
     }

     public static long get_group_list() {
         return run("get_group_list ", new HashMap<String, Object>());
     }

     public static long get_group_member_info(final String group_id, final String user_id, final boolean no_cache) {
          return run("get_group_member_info", new HashMap<String, Object>(){
               {
                    put("group_id", group_id);
                    put("no_cache", no_cache);
                    put("user_id", user_id);
               }
          });
     }

     public static long get_cookies() {
          return run("get_cookies ", new HashMap<String, Object>());
     }

     public static long get_record(final String file, final String out_format) {
          return run("get_record", new HashMap<String, Object>(){
               {
                    put("file", file);
                    put("out_format", out_format);
               }
          });
     }

     public static long get_version_info() {
          return run("get_version_info ", new HashMap<String, Object>());
     }

     public static long set_restart() {
          return run("set_restart ", new HashMap<String, Object>());
     }

     public static long clean_data_dir(final String file) {
          return run("clean_data_dir", new HashMap<String, Object>(){
               {
                    put("data_dir", file);
               }
          });
     }



}
