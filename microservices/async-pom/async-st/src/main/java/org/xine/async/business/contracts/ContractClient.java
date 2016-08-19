package org.xine.async.business.contracts;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientProperties;
import org.xine.async.business.contracts.entity.Contract;

public class ContractClient {

	private final Client client;
	private final WebTarget tut;

	ContractClient(String url) {
		this.client = ClientBuilder.newClient();
		this.client.property(ClientProperties.CONNECT_TIMEOUT, 100);
		this.client.property(ClientProperties.READ_TIMEOUT, 500);
		this.tut = this.client.target(url);
	}

	public Contract get(Long id) {
		final WebTarget path = this.tut.path("/{id}");
		final WebTarget bookId = path.resolveTemplate("id", id);

		final Builder invocation = bookId.request();
		final Contract contract = invocation.get(Contract.class);

		return contract;
	}

	public Contract create(Contract contract) {
		return null;
	}

	public void close() {
		this.client.close();
	}

}
