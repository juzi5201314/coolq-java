package net.soeur.qqbot;

import net.soeur.qqbot.command.Command;
import net.soeur.qqbot.command.defaults.ExecCommand;
import net.soeur.qqbot.command.defaults.PowerCommand;
import net.soeur.qqbot.command.defaults.StatusCommand;
import net.soeur.qqbot.command.sender.SenderAdmin;
import net.soeur.qqbot.message.Logger;
import net.soeur.qqbot.model.Model;
import net.soeur.qqbot.utils.DataBase;
import net.soeur.qqbot.utils.sqlite.SqliteDB;
import net.soeur.qqbot.websocket.WebSocketClient;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.SQLException;
import java.util.Scanner;

public class Start {

    public static void main(String[] args) throws Exception {
        //初始化配置文件
        Config.setConfig(new Config("config"));
        //初始化监听器
        initListener();
        //注册指令
        registerCommands();
        //打开sqlite连接
        openSqliteDB();
        //加载模块
        loadModels();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String cmd = scanner.nextLine();
            if (cmd.equals("stop"))
                break;
            else if (Command.isCommand(cmd))
            {
                Command.exec(cmd, new SenderAdmin());
            }
        }
        //程序停止
        onStop();
    }

    private static void openSqliteDB() {
        new File(SqliteDB.DATA_PATH).mkdirs();
        try {
            //数据库power，用于存储用户权限信息
            SqliteDB userPowerDB = new SqliteDB("power");
            userPowerDB.connection();
            //如果没有表USER，则创建表
            if (!userPowerDB.query("SELECT * FROM sqlite_master WHERE name='USER'").next())
                 //id为用户qq号码，power为权限
                 userPowerDB.exec("CREATE TABLE USER(" +
                    "ID TEXT NOT NULL," +
                    "POWER TEXT NOT nULL" +
                    ")");
        }catch (SQLException e) {
            Logger.throwException(e);
        }
    }

    private static void onStop() {
        try {
            for (String clientName : WebSocketClient.Client.getClients().keySet())
                WebSocketClient.Client.getClient(clientName).close();
            for (String dbName : DataBase.getDBs().keySet())
                DataBase.getDB(dbName).close();
        }catch (Exception e) {
            Logger.throwException(e);
        }
    }

    private static void initListener() {
        if (!Config.read("websocket_clinet").toString().trim().equals("")) {
            try{
                WebSocketClient.Client.start(Config.read("websocket_listen").toString().trim(), "listener");
                if (Config.read("websocket_api_persistent_connections").equals("true"))
                WebSocketClient.Client.start(Config.read("websocket_api").toString().trim(), "api");
            }catch (Exception e) {
                Logger.throwException(e);
            }
        }else
            Logger.error("找不到合适的监听途径，监听器启动失败");
    }

    private static void registerCommands() {
        Command.register("status", new StatusCommand());
        Command.register("exec", new ExecCommand());
        Command.register("power", new PowerCommand());
    }

    private static void loadModels() {
        JSONObject jsonObject = (JSONObject) Config.read("model");
        File modelPath = new File(Model.BASE_PATH);
        modelPath.mkdirs();
        for (String name : jsonObject.keySet()) {
            try {
                URLClassLoader loader = new URLClassLoader(new URL[]{new URL("file:" + modelPath.getCanonicalPath() + File.separator + name + ".jar")});
                Class clazz = loader.loadClass(jsonObject.getString(name));
                loadModel(clazz.newInstance());
            } catch (ClassNotFoundException ce) {
                try {
                    loadModel(name, jsonObject.getString(name), modelPath);
                } catch (IllegalAccessException e) {
                    Logger.throwException(e);
                } catch (InstantiationException e) {
                    Logger.throwException(e);
                } catch (IOException e) {
                    Logger.throwException(e);
                }
            } catch (Exception e) {
                Logger.throwException(e);
            }
        }
    }

    public static void loadModel(Object object) {
        if (object instanceof Model)
            ((Model) object).start();
        else
            Logger.warning(object.getClass().toString() + " 不是一个模块");
    }

    public static void loadModel(String name, String Mainclass, File modelPath) throws IllegalAccessException, InstantiationException, IOException {
        try {
            URLClassLoader loader = new URLClassLoader(new URL[]{new URL("file:" + modelPath.getCanonicalPath() + File.separator)});
            Class clazz = loader.loadClass(Mainclass);
            loadModel(clazz.newInstance());
        } catch (ClassNotFoundException e) {
            Logger.warning("无法加载模块 " + name + "，原因：找不到主类" + e.getMessage());
        }
    }

}
