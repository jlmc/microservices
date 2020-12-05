package org.xine.ticker.control;

import java.util.Optional;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class TickerEndpoint extends Endpoint {

    private Session session;

    private String lastMessage;

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        this.session = session;
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                System.out.println("RECEIVED:: " + message);
                lastMessage = message;
            }

        });

    }
    
    public Optional<String> getLastMessage() {
        return Optional.ofNullable(this.lastMessage);
    }

}
