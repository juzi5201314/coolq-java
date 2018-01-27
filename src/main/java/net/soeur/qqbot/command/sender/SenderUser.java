package net.soeur.qqbot.command.sender;

import net.soeur.qqbot.api.Api;
import net.soeur.qqbot.command.CommandSender;
import net.soeur.qqbot.message.Logger;
import net.soeur.qqbot.utils.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SenderUser implements CommandSender{

    private String user_id;
    private UserPower power;
    private String group_id;
    private String discuss_id;

    public SenderUser(String user_id, String group_id, String discuss_id) {
        this.user_id = user_id;
        this.group_id = group_id;
        this.discuss_id = discuss_id;
        try {
            ResultSet resultSet = DataBase.getSqliteDB("power").query("SELECT * FROM USER WHERE ID='" + getId() + "'");
            if (resultSet.next())
                this.power = UserPower.getPower(resultSet.getString("POWER"));
            else
                this.power = UserPower.USER;
        }catch (SQLException e) {
            Logger.throwException(e);
        }
    }

    public boolean inGroup() {
        return getGroupId() != null;
    }

    public boolean inDiscuss() {
        return getDiscussId() != null;
    }

    public String getGroupId() {
        return group_id;
    }

    public String getDiscussId() {
        return discuss_id;
    }

    public void reply(String message) {
        if (inGroup())
            Api.send_group_msg(message, getGroupId());
        else if (inDiscuss())
            Api.send_discuss_msg(message, getDiscussId());
        else
            sendMessage(message);

    }

    public String getId() {
        return user_id;
    }

    public UserPower getPower() {
        return power;
    }

    public void sendMessage(String message) {
        Api.send_private_msg(message, user_id);
    }



}
