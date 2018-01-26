package net.soeur.qqbot.websocket;

import net.soeur.qqbot.Config;
import org.json.JSONObject;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.HandshakeResponse;
import java.util.*;

public class HandshakeHeader extends ClientEndpointConfig.Configurator {

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {
        JSONObject config = (JSONObject) Config.read("Token");
        Set<String> keys = config.keySet();
        for(String key : keys) {
            String value = config.get(key).toString().trim();
            List<String> token = new ArrayList<String>();
            if (value.indexOf(",") > 0){
                for (String v : value.split(","))
                    token.add(v);
            }else
                token.add(value);
            headers.put(key, token);
        }
    }

    @Override
    public void afterResponse(HandshakeResponse handshakeResponse) {

    }

}