package org.xine.extendable.business.farmer.boundary;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;

@Singleton
@Lock(LockType.READ)
public class FarmerBrown {
	private final AtomicInteger checks = new AtomicInteger();

	@Schedules({
		@Schedule(month = "5", dayOfMonth = "20-Last", minute = "0", hour = "8"),
		@Schedule(month = "6", dayOfMonth = "1-10", minute = "0", hour = "8")
	})
	private void plantTheCorn() {
		// Dig out the planter!!!
	}

	@Schedules({
		@Schedule(month = "9", dayOfMonth = "20-Last", minute = "0", hour = "8"),
		@Schedule(month = "10", dayOfMonth = "1-10", minute = "0", hour = "8")
	})
	private void harvestTheCorn() {
		// Dig out the combine!!!
	}

	@Schedule(second = "*", minute = "*", hour = "*")
	private void checkOnTheDaughters() {
		checks.incrementAndGet();
	}

	public int getChecks() {
		return checks.get();
	}

}
