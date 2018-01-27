package net.soeur.qqbot.command.sender;

import net.soeur.qqbot.command.CommandSender;
import net.soeur.qqbot.message.Logger;

import javax.websocket.ClientEndpoint;

public class SenderAdmin implements CommandSender {

    @Override
    public void sendMessage(String message) {
        Logger.info(message);
    }

    @Override
    public void reply(String message) {
        sendMessage(message);
    }

    @Override
    public UserPower getPower() {
        return UserPower.OWNER;
    }
}
