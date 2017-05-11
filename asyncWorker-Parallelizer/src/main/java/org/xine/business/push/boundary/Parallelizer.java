package org.xine.business.push.boundary;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.xine.business.push.control.Multiplier;

@Stateless
public class Parallelizer {

	@Inject
	Multiplier asyncWorker;

	public List<Long> execute(final int number) {
		final List<Future<Long>> futures = new LinkedList<>();

		for (int i = 0; i <= number; i++) {
			final Future<Long> future = this.asyncWorker.multiply(i, i);
			futures.add(future);
		}

		final List<Long> results = new LinkedList<>();

		for (final Future<Long> future : futures) {
			try {
				results.add(future.get());
			} catch (InterruptedException | ExecutionException e) {
				throw new EJBException();
			}
		}


		return results;
	}

}
