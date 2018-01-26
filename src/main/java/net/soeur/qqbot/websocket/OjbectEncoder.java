package net.soeur.qqbot.websocket;

import net.soeur.qqbot.message.Logger;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class OjbectEncoder implements Encoder.Text<String> {

    public void init(EndpointConfig paramEndpointConfig) {
        //Auto-generated method stub
    }

    public void destroy() {
        //Auto-generated method stub
    }

    public String encode(String paramT) {
        //Auto-generated method stub
        Logger.debug("en" + paramT);
        return paramT.toUpperCase();
    }

}
