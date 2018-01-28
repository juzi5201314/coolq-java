package net.soeur.qqbot.command.sender;

import net.soeur.qqbot.message.Logger;
import net.soeur.qqbot.utils.DataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum UserPower {
     OWNER("owner", 5),
     ADMIN("admin", 4),
     DIRECTOR("director", 3),
     WARDEN("warden", 2),
     USER("user", 1);

     private String power;
     private int level;

     UserPower(String power, int level) {
          this.power = power;
          this.level = level;
     }

     public boolean exceed(UserPower userPower) {
          return level > userPower.level;
     }

     public boolean under(UserPower userPower) {
          return level < userPower.level;
     }

     @Override
     public String toString() {
          return power;
     }

     public static UserPower getPower(String power) {
          if (power.equals(OWNER.toString()))
               return OWNER;
          else if (power.equals(ADMIN.toString()))
               return ADMIN;
          else if (power.equals(DIRECTOR))
               return DIRECTOR;
          else  if (power.equals(WARDEN))
               return WARDEN;
          else
               return USER;
     }

     public static List<String> getUsers(UserPower power) {
          try {
               ResultSet resultSet = DataBase.getSqliteDB("power").query("SELECT * FROM USER WHERE POWER='" + power.toString() + "'");
               List<String> list = new ArrayList<>();
               while (resultSet.next())
                    list.add(resultSet.getString("ID"));
               return list;
          }catch (SQLException e) {
               Logger.throwException(e);
          }
          return null;
     }

}