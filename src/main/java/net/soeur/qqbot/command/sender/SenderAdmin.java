package net.soeur.qqbot.command.sender;

import net.soeur.qqbot.command.CommandSender;
import net.soeur.qqbot.message.Logger;

public class SenderAdmin implements CommandSender {

    public void sendMessage(String message) {
        Logger.info(message);
    }

}
