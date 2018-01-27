package net.soeur.qqbot.command;

import net.soeur.qqbot.command.sender.UserPower;

public interface CommandSender {

    void sendMessage(String message);
    void reply(String message);
    UserPower getPower();

}