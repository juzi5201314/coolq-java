package net.soeur.qqbot.websocket;

import net.soeur.qqbot.message.Logger;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class OjbectDecoder implements Decoder.Text<String>{

    public void init(EndpointConfig paramEndpointConfig) {
        // Auto-generated method stub
    }

    public void destroy() {
        // Auto-generated method stub
    }

    public String decode(String paramString) {
        // Auto-generated method stub
        Logger.debug("de" + paramString);
        return paramString.toLowerCase();
    }

    public boolean willDecode(String paramString) {
        Logger.debug("wde" + paramString);
        // Auto-generated method stub
        return true;
    }

}