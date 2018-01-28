package net.soeur.qqbot;

import net.soeur.qqbot.message.Logger;
import net.soeur.qqbot.utils.Util;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private static Config config;

    public static Object read(String key) {
        return config.get(key);
    }

    public static void setConfig(Config config) {
        Config.config = config;
    }

    private String fileName;
    private JSONObject data;
    private File file;

    public Config(String name, Map<String, Object> c) {
         this.fileName = name;
         this.file = new File(Util.BASE_DIR + getFileName() + ".json");
         if (!file.exists()) {
            try {
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(new JSONObject(c).toString().getBytes());
                fileOutputStream.close();
            }catch (IOException e) {
                Logger.throwException(e);
            }
        }
         init();
    }

    public Config(String name) {
        this(name, new HashMap<>());
    }

    public String getFileName() {
        return fileName;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void init() {
        try {
            if (!file.exists() || !file.isFile() || !file.canRead()) {
                Logger.error("找不到配置文件");
                throw new FileNotFoundException("Config file not found");
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            int size = fileInputStream.available();
            byte[] buff = new byte[size];
            fileInputStream.read(buff);
            fileInputStream.close();

            data = new JSONObject(new String(buff, "utf-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Logger.throwException(e);
        }

    }
}
