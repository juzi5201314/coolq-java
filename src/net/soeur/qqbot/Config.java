package net.soeur.qqbot;

import net.soeur.qqbot.message.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Config {

    final private static String configName = "config";

    private static JSONObject data;

    public static Object read(String key) {
        return data.get(key);
    }

    public static void init() {
        try {
            File file = new File((new File("")).getCanonicalPath() + File.separator + configName + ".json");
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
