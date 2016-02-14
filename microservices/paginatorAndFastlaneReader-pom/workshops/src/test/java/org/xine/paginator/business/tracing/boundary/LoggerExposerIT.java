package org.xine.paginator.business.tracing.boundary;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class LoggerExposerIT {

    @Inject
    LoggerInjectionSupport cut;

    @Deployment
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class).
                addClasses(LoggerExposer.class, LoggerInjectionSupport.class).
                addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void loggerProperlyConfigured() {
        final Logger logger = this.cut.getLogger();
        final String expected = this.cut.getClass().getName();
        final String actual = logger.getName();
        assertThat(actual, is(expected));
    }

}
