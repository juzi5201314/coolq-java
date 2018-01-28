package net.soeur.qqbot.command.defaults;

import net.soeur.qqbot.command.Command;
import net.soeur.qqbot.command.CommandReceiver;
import net.soeur.qqbot.command.CommandSender;
import net.soeur.qqbot.command.sender.UserPower;
import net.soeur.qqbot.message.Logger;
import net.soeur.qqbot.utils.DataBase;
import net.soeur.qqbot.utils.sqlite.SqliteDB;

import java.sql.ResultSet;

public class PowerCommand implements CommandReceiver {

     public void exec(CommandSender sender, String[] args) {
          if (sender.getPower().under(UserPower.OWNER)){
               sender.reply(Command.NOT_POWER);
               return;
          }
          if (args.length < 2)
               return;
          try {
               SqliteDB sqliteDB = DataBase.getSqliteDB("power");
               switch (args[1]) {
                    case "all":
                         ResultSet resultSet = sqliteDB.query("SELECT * FROM USER");
                         StringBuilder stringBuilder = new StringBuilder("权限列表:");
                         while (resultSet.next()){
                              stringBuilder
                                      .append("\n")
                                      .append(resultSet.getString("ID"))
                                      .append(" => ")
                                      .append(resultSet.getString("POWER"));
                         }
                         sender.reply(stringBuilder.toString());
                         break;
                    case "set":
                    case "unset":
                         if (args.length < 4)
                              return;
                         boolean hasUser = sqliteDB.query("SELECT * FROM USER WHERE ID='" + args[2] + "'").next();
                         if (args[1].equals("set")){
                              if (!hasUser) {
                                   sqliteDB.exec("INSERT INTO USER(ID,POWER) VALUES('" + args[2] + "'," + "'" + UserPower.getPower(args[3]).toString() + "')");
                                   sender.reply("执行成功");
                              }else {
                                   sqliteDB.exec("UPDATE USER SET POWER='" + UserPower.getPower(args[3]).toString() + "' WHERE ID='" + args[2] + "'");
                                   sender.reply("更新成功");
                              }
                         }else {
                              if (hasUser) {
                                   sqliteDB.exec("DELETE from USER where ID='" + args[2] + "'");
                                   sender.reply("删除成功");
                              }else
                                   sender.reply("权限列表里没有 " + args[2]);
                         }
                         break;
               }
          }catch (Exception e) {
               Logger.throwException(e);
          }
     }

}
