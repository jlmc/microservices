package org.xine.channels.presentation.view;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.channels.business.configuration.boundary.Configurator;

@Named
@RequestScoped
public class DefinitionsView {

	@Inject
	Configurator configurator;
	@Inject
	Instance<Integer> interval;
	// @Inject
	// SnapshotProvider snapshotProvider;

	public int getInterval() {
		return this.interval.get().intValue();
	}
	//
	// public Snapshot getSnapshot() throws Exception {
	// return this.snapshotProvider.fetchSnapshot();
	// }

}
