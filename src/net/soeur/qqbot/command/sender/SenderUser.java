package net.soeur.qqbot.command.sender;

import net.soeur.qqbot.api.Api;
import net.soeur.qqbot.command.CommandSender;

public class SenderUser implements CommandSender{

    private String user_id;
    private UserLevel level;

    public SenderUser(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return user_id;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void sendMessage(String message) {
        Api.send_private_msg(message, user_id);
    }

    public enum UserLevel {

    }

}
