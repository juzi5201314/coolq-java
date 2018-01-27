package net.soeur.qqbot.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
     private Statement statement;
     protected String name;

     public void connection() throws SQLException {
          dbs.put(getName(), this);
          statement = connection.createStatement();
     }

     public String getName() {
          return name;
     }

     public void close() throws SQLException {
          statement.close();
          connection.close();
     }

     public void exec(String sql) throws SQLException {
          statement.executeUpdate(sql);
          connection.commit();
     }

     public ResultSet query(String sql) throws SQLException {
          return statement.executeQuery(sql);
     }

}