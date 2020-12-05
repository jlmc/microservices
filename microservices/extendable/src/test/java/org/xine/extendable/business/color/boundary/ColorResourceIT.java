package org.xine.extendable.business.color.boundary;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import javax.enterprise.inject.spi.Extension;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xine.extendable.business.JAXRSConfiguration;
import org.xine.extendable.business.microscopes.method.MethodScopedExtension;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@RunWith(Arquillian.class)
public class ColorResourceIT {

    @Deployment
    public static WebArchive createDeployment() {
        // ShrinkWrap.create(WebArchive.class, "ROOT.war")

        return ShrinkWrap.create(WebArchive.class, "extendable.war").
                addPackages(true, JAXRSConfiguration.class.getPackage()).
                addAsWebInfResource(
                        new StringAsset(MethodScopedExtension.class.getName()),
                            "classes/META-INF/services/" + Extension.class.getName()).
                as(ExplodedImporter.class).importDirectory(new File("src/main/webapp")).
                as(WebArchive.class);
    }

    @ArquillianResource
    private URL webappUrl;

    @Test
    @RunAsClient
    public void test() throws Exception {

        assertRequest("color/red", "red, 1 invocations");
        assertRequest("color/red", "red, 2 invocations");
        assertRequest("color/red", "red, 3 invocations");
        assertRequest("color/green", "green, 1 invocations");
        assertRequest("color/green", "green, 2 invocations");
        assertRequest("color/green", "green, 3 invocations");
        assertRequest("color/red", "red, 4 invocations");
        assertRequest("color/blue", "blue, 1 invocations");
        assertRequest("color/blue", "blue, 2 invocations");
    }

    private void assertRequest(String path, String expectedResult) {
        final Client client = ClientBuilder.newBuilder().register(JacksonJsonProvider.class).build();
        final String result = client.target(webappUrl + "resources/" + path).request(MediaType.APPLICATION_JSON)
                .header("Accept-Language", "pt").get(String.class);
        // post(Entity.entity(invalidBook, MediaType.APPLICATION_JSON));
        System.out.println(result);

        assertEquals(expectedResult, result);
    }



}
