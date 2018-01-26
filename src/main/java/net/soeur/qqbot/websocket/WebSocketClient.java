package net.soeur.qqbot.websocket;

import net.soeur.qqbot.listen.Listener;
import net.soeur.qqbot.message.Color;
import net.soeur.qqbot.message.Logger;
import org.json.JSONObject;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ClientEndpoint(
        configurator = HandshakeHeader.class,
        decoders = {OjbectDecoder.class},
        encoders = {OjbectEncoder.class},
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

    @OnOpen
    public void open(Session session){
        this.session = session;
        Logger.debug("session " + session.getId() + " 成功连接到 " + tagerUrl);
    }

    @OnMessage
    public synchronized void onMessage(String message){
        if (name.contains("listener"))
        Listener.call(message);
    }

    @OnClose
    public void onClose() throws Exception {
        Logger.warning("Websocket closed from " + tagerUrl);
        Client.close(this);
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

        public static void close(WebSocketClient client) throws IOException {
            Collection<WebSocketClient> collection = clients.values();
            collection.remove(client);
        }
    }

}
