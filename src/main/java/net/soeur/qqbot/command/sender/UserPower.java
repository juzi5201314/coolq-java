package net.soeur.qqbot.command.sender;

import net.soeur.qqbot.websocket.WebSocketClient;

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

}