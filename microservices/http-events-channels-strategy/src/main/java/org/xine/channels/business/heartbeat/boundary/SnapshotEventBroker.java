package org.xine.channels.business.heartbeat.boundary;

import java.io.Writer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.xine.channels.business.heartbeat.boundary.Severity.Level;
import org.xine.channels.business.heartbeat.control.Serializer;
import org.xine.channels.business.heartbeat.entity.Snapshot;
import org.xine.channels.presentation.publication.BrowserWindow;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SnapshotEventBroker {

	private final ConcurrentLinkedQueue<BrowserWindow> browsers = new ConcurrentLinkedQueue<>();
	// private final ConcurrentLinkedQueue<BrowserWindow> escalationBrowsers =
	// new ConcurrentLinkedQueue<>();
	private final ConcurrentHashMap<String, Snapshot> escalations = new ConcurrentHashMap<>();

	private Timer timer;

	@Inject
	private Instance<Integer> intervale;
	@Resource
	TimerService timerService;
	@Inject
	Serializer serializer;

	@PostConstruct
	public void startTimer() {
		final ScheduleExpression expression = new ScheduleExpression()
				.hour("*")
				.minute("*")
				.second("*/"+this.intervale.get());

		this.timer = this.timerService.createCalendarTimer(expression);
	}

	@Timeout
	public void notificationEscalationListeners() {
		this.browsers.forEach(bw -> {
			final String channel = bw.getChannel();

			if(channel != null && !channel.isEmpty()) {
				final Snapshot snapshot = this.escalations.get(channel);
				try {

					if(snapshot != null) {
						send(bw, snapshot);
					} else {
						bw.nothingToSay();
					}

				} finally {
					this.browsers.remove(bw);
				}
			}
		});

	}

	public void onBrowerRequest(@Observes final BrowserWindow browserWindow) {
		this.browsers.add(browserWindow);
	}

	public void onNewSnapshot(
			@Observes
			@Severity(Level.HEARTBEAT)
			final Snapshot snapshot) {

		this.browsers.forEach(browserWindow -> {

			if (browserWindow.getChannel() == null) {
				try {
					send(browserWindow, snapshot);
				} finally {
					this.browsers.remove(browserWindow);
				}
			}

		});
	}

	public void onNewEscalation(@Observes @Severity(Level.ESCALATION) final Snapshot escalation) {
		this.escalations.put(escalation.getEscalationChannel(), escalation);
	}

	private void send(final BrowserWindow browserWindow, final Snapshot snapshot) {
		final Writer writer = browserWindow.getWriter();
		this.serializer.serialize(snapshot, writer);
		browserWindow.send();
	}

}
