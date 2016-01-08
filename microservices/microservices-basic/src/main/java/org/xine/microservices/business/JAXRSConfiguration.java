package org.xine.microservices.business;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author Joao Costa
 */
@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {
}
