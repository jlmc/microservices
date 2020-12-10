package io.github.jlmc.service;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 */
@OpenAPIDefinition(info = @Info(title = "Scaling JAX-RS", description = "Scaling JAX-RS Applications", version = "1.0"))


@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {

}
