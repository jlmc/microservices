package org.xine.business.staticscheduler.control;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xine.business.staticscheduler.control.StaticScheduler;


@RunWith(Arquillian.class)
public class StaticSchedulerIT {

    @Inject
    StaticScheduler scheduler;

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "scheduler.jar")
                .addClasses(StaticScheduler.class)
                .addAsManifestResource(
                        new ByteArrayAsset("<beans/>".getBytes()),
                        ArchivePaths.create("beans.xml"));
    }

    @Test
    public void testExecute() {
        Assert.assertNotNull(this.scheduler);
    }

}
