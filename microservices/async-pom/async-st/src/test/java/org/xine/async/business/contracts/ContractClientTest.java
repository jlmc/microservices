package org.xine.async.business.contracts;

import java.math.BigDecimal;

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

	@Test
	public void shouldCreateNewContract() {
		final Contract contract = Contract.of(121212L, "abcde", new BigDecimal(345.78));

		final Contract createdContract = this.client.create(contract);
	}

}
