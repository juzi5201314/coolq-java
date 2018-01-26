package net.soeur.qqbot.command;

public interface CommandReceiver {

    void exec(CommandSender sender, String[] args);

}
