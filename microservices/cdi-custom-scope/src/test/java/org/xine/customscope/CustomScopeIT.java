package org.xine.customscope;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CustomScopeIT {

	private static final String URI = "http://localhost:8080/cdi-custom-scope/resources/customscope/";
	// private final Client client;
	// private final WebTarget tut;

	private Client client;
	private WebTarget target;

	@Before
	public void initializeClient() {
		client = ClientBuilder.newClient();
		// this.client.property(ClientProperties.CONNECT_TIMEOUT, 100);
		// this.client.property(ClientProperties.READ_TIMEOUT, 500);
		target = client.target(URI);
		// client = ClientFactory.newClient();
		// target = client.target(URI);
	}

	@After
	public void shutdownClient() {
		client.close();
	}

	@Test
	public void creationAndDisposal() {
		final String expected = "000";
		String actual = getStringWithGET("injection");
		assertThat(actual, is("++"));
		actual = getStringWithGET("instanceCount");
		assertThat(actual, is("111"));
		actual = getStringWithGET("shutdown");
		assertThat(actual, is("+"));
		actual = getStringWithGET("instanceCount");
		assertThat(actual, is(expected));
	}

	@Test
	public void sameTypeInjectedTwice() {
		final String actual = getStringWithGET("sameinstance");
		assertThat(actual, is("+"));
	}

	@Test
	public void sameTypeInjectedTwiceFetchedLazily() {
		final String actual = getStringWithGET("samelazyinstance");
		assertThat(actual, is("+"));
	}

	private String getStringWithGET(String path) throws NullPointerException {
		return target.path(path).request(MediaType.TEXT_PLAIN).get(String.class);
	}


}
