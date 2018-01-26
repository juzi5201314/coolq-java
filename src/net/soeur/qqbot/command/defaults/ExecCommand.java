package net.soeur.qqbot.command.defaults;

import net.soeur.qqbot.api.Api;
import net.soeur.qqbot.command.CommandReceiver;
import net.soeur.qqbot.command.CommandSender;
import net.soeur.qqbot.command.sender.SenderUser;

import java.util.HashMap;

public class ExecCommand implements CommandReceiver {

     public void exec(CommandSender sender, String[] args) {
          if (sender instanceof SenderUser || args.length < 2)
               return;
          Api.run(args[1], new HashMap<String, Object>(){
               {
                    for (String arg : args) {
                         String[] argss = arg.split("=");
                         if (argss.length < 2)
                              continue;
                         put(argss[0], argss[1]);
                    }
               }
          });
     }
}
