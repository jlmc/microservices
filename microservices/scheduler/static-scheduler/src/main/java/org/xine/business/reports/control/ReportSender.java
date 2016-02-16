package org.xine.business.reports.control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.xine.business.reports.entity.Report;

@Stateless
public class ReportSender {

	@Inject
	Logger logger;

	@Inject
	ReportRepository repository;

	@Schedule(hour = "*", minute = "*/3")
	public void dump() {
		this.logger.info(String.format("dunping %s",
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));

		final Collection<Report> reports = this.repository.loadReports();
		reports.forEach(System.out::println);

	}

}
