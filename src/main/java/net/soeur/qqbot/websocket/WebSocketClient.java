package net.soeur.qqbot.websocket;

import net.soeur.qqbot.api.Api;
import net.soeur.qqbot.listen.Listener;
import net.soeur.qqbot.message.Color;
import net.soeur.qqbot.message.Logger;
import org.json.JSONObject;
import org.json.JSONPointerException;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ClientEndpoint(
        configurator = HandshakeHeader.class,
        subprotocols={"subprotocol1"}
)
public class WebSocketClient {

    private Session session;
    private String name;
    private String tagerUrl;

    public WebSocketClient(String name,  String tagerUrl) {
        this.name = name;
        this.tagerUrl = tagerUrl;
    }

    public String getName() {
        return name;
    }

    @OnOpen
    public void open(Session session){
        this.session = session;
        Logger.debug("session " + session.getId() + " 成功连接到 " + tagerUrl);
    }

    @OnMessage
    public void onMessage(String message){
        if (name.contains("listener"))
             Listener.call(message);
        else if (name.contains("api")){
             try {
                  Map<String, Object> data = new JSONObject(message).toMap();
                  Api.add(Long.valueOf(data.get("echo").toString()), data);
             }catch (JSONPointerException e) {
                  Logger.debug("收到一条错误的返回信息：" + message);
             }
        }
    }

    @OnClose
    public void onClose() throws Exception {
        Logger.debug("Websocket closed from " + tagerUrl);
    }


    @OnError
    public void onError(Session session, Throwable t) {
        Logger.throwException(t);
    }

    public void send(String message){
        session.getAsyncRemote().sendText(message);
    }

    public void send(Map<String, Object> message) {
        send(new JSONObject(message).toString());
    }

    public void close() throws IOException {
        if(session.isOpen()){
            session.close();
        }
    }

    public static class Client {

        private static Map<String, WebSocketClient> clients = new HashMap<String, WebSocketClient>();

        public static WebSocketClient start(String url, String clinetName) throws URISyntaxException, IOException, DeploymentException {
            long st = System.currentTimeMillis();
            WebSocketContainer conmtainer = ContainerProvider.getWebSocketContainer();
            WebSocketClient client = new WebSocketClient(clinetName, url);
            conmtainer.connectToServer(client, new URI(url));
            clients.put(clinetName, client);
            Logger.info("websocket client " + Color.add(clinetName, Color.TextColor.BLACK, Color.BgColor.GREEN) + " 成功启动, 耗时 " + (System.currentTimeMillis() - st) + "ms");
            return client;
        }

        public static WebSocketClient getClient(String name) {
            return clients.get(name);
        }

        public static Map<String, WebSocketClient> getClients() {
             return clients;
        }

    }

}
