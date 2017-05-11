package org.xine.ticker.boundary;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

import org.xine.ticker.control.TickerEndpoint;

@Singleton
@Startup
public class Ticker {

	private WebSocketContainer container;

	@PostConstruct
	public void connect() {
		final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
		container = ContainerProvider.getWebSocketContainer();

		// connect to the remote server
		final TickerEndpoint tickerEndpoint = new TickerEndpoint();
		
		try {
			container.connectToServer(
					tickerEndpoint,
					cec,
					new URI("ws://localhost:8080/weather-1.0-SNAPSHOT/forcasts"));
		} catch (DeploymentException | IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
