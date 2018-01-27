package net.soeur.qqbot.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class DataBase {

     private static Map<String, DataBase> dbs = new HashMap<String, DataBase>();

     public static DataBase getDB(String name) {
          return dbs.get(name);
     }

     public static Map<String, DataBase> getDBs() {
          return dbs;
     }

     protected Connection connection;
     protected String name;

     public void connection() throws SQLException {
          dbs.put(getName(), this);
     }

     public String getName() {
          return name;
     }

     public void close() throws SQLException {
          connection.close();
     }

}