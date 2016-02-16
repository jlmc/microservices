package org.xine.business.reports.control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.xine.business.reports.entity.Report;

@Singleton
@Startup
public class ResportGenerator {

	@Inject
	Logger logger;

	@Inject
	ReportRepository reportRepository;


	@Schedule(hour = "*", minute = "*/1")
	public void generate() {

		final Report report = Report.Builder.init()
				.name("abc - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
				.build();
		this.logger.info(String.format("--- generating report %s", report));
		this.reportRepository.add(report);
	}


}
