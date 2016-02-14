package org.xine.cdidemos.business.salaries.bundary;

import java.time.Year;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.xine.cdidemos.business.salaries.control.SalariesPlanCalculator;
import org.xine.cdidemos.business.salaries.control.TaxCal;
import org.xine.cdidemos.business.salaries.entity.Employeer;
import org.xine.cdidemos.business.salaries.entity.Instruction;



@Path("tax")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Stateless
public class TaxesCalculator {
	
	@Inject
	@TaxCal
	SalariesPlanCalculator salariesPlanCalculator;
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@PathParam("id") final Integer id, @Context final UriInfo uriInfo) {

		try {
			
			final Employeer employeer = Employeer.Builder.init().withBaseSalary(1000)
									.withYearOfAdmission(Year.now())
									.withInstructionEmploee(Instruction.MASTER)
									.withInstructionDuty(Instruction.GRADUATION)
									.build();

			final double earningsEstimates = this.salariesPlanCalculator.earningsEstimates(employeer);

			return Response.ok(earningsEstimates).build();

		} catch (final Exception e) {
			return Response.serverError().build();
		}

	}

}
