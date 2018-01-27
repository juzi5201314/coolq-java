package net.soeur.qqbot.utils.sqlite;

import net.soeur.qqbot.message.Logger;
import net.soeur.qqbot.utils.DataBase;
import net.soeur.qqbot.utils.SqlDB;
import net.soeur.qqbot.utils.Util;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteDB extends DataBase implements SqlDB {

     final public static String DATA_PATH = "database";

     public SqliteDB(String name) {
          this.name = name;
     }

     public void connection() throws SQLException {
          try {
               Class.forName("org.sqlite.JDBC");
          }catch (ClassNotFoundException e) {
               Logger.error("找不到sqlite拓展。请检查程序完整性！");
          }
          connection = DriverManager.getConnection("jdbc:sqlite:" + Util.BASE_DIR + DATA_PATH + File.separator + name + ".db");
          super.connection();
     }



}
