package org.xine.async.business.contracts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xine.async.business.contracts.entity.Contract;

public class ContractClientTest {

	private ContractClient client;

	@Before
	public void initializeClient() {
		this.client = new ContractClient("http://localhost:8080/async/resources/contracts");
	}

	@After
	public void shutdownClient() {
		this.client.close();
	}

	@Test
	public void shouldGetContractById() {

		// curl -i -v -X GET -H "Content-Type: application/json" -H "Accept:
		// application/json" http://localhost:8080/async/resources/contracts/4

		final Contract result = this.client.get(2L);

		System.out.println(result);
	}

}
