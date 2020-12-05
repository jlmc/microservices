package org.xine.navigator.business.reports.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.xine.navigator.business.reports.entity.Report;

@Stateless
@Path("report")
@Produces(MediaType.TEXT_PLAIN)
public class ReportMngrResource {

    @GET
    public Response reports() {
        final List<Report> reports = new ArrayList<Report>(2);
        reports.add(new Report(1L, "A"));
        reports.add(new Report(2L, "B"));

        return Response.ok(reports).build();
    }

}
