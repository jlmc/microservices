package org.xine.presentation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xine.business.registations.boundary.WorkshopRegistration;
import org.xine.business.registations.entity.Workshop;

public class IndexTest {

	Index per;

	@Before
	public void initialize(){
		this.per = new Index();
		this.per.ws = Mockito.mock(WorkshopRegistration.class);
	}

	@Test
	public void newWorkshop() {
		this.per.newRegistration();
		final Workshop expected = this.per.getWorkshop();
		Mockito.verify(this.per.ws, Mockito.times(1)).register(expected);
	}

	@Test(expected = Exception.class)
	public void newRegisterWithBoundaryError() {
		this.per.newRegistration();
		final Workshop expected = this.per.getWorkshop();
		Mockito.when(this.per.ws).thenThrow(new Exception("Some thing is wrong"));
	}

}
