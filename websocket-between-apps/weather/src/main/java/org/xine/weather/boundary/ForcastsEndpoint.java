package org.xine.weather.boundary;

import java.io.IOException;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Singleton
@Startup
@ServerEndpoint("/forcasts")
public class ForcastsEndpoint {

	private Session session;

	@OnOpen
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		this.session = session;
	}

	public void sendWeatherForcast(String message) {
		try {
			if (session != null && session.isOpen()) {
				session.getBasicRemote().sendText(message);
			}
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Schedule(hour = "*", minute = "*", second = "*/2")
	public void ticker() {
		System.out.println(".");
		sendWeatherForcast("xine weather: " + System.currentTimeMillis());
	}
}
