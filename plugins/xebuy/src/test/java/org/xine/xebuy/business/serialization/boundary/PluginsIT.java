package org.xine.xebuy.business.serialization.boundary;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xine.basicserialization.business.boundary.JavaSerializer;
import org.xine.hessian.business.boundary.HessianSerializer;
import org.xine.xebuy.business.plugin.serializer.Serialization;
import org.xine.xebuy.business.plugin.serializer.Serialization.PlanType;
import org.xine.xebuy.business.plugin.serializer.SerializationType;
import org.xine.xebuy.business.plugin.serializer.Serializer;

import com.sun.tools.ws.wscompile.Plugin;

/**
 *
 * @author Adam Bien, blog.adam-bien.com
 */
@RunWith(Arquillian.class)
public class PluginsIT {


    @Inject
    @Any
    Instance<Serializer> plugins;

    @Inject
    @Serialization(plantype = PlanType.OPTIMIZED)
    Serializer optimized;

    @Inject
    @Serialization(plantype = PlanType.DEFAULT)
    Serializer standard;

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "plugins.jar")
                .addClasses(Plugin.class, JavaSerializer.class, HessianSerializer.class, NullSerializer.class)
                .addAsManifestResource(new ByteArrayAsset("<beans/>".getBytes()), ArchivePaths.create("beans.xml"));
    }

    @Test
    public void discoverPlugins() {
        assertTrue(plugins.isAmbiguous());
        assertFalse(plugins.isUnsatisfied());
        final List<Class> serializers = new ArrayList<>();
        for (final Serializer serializer : plugins) {
            serializers.add(serializer.getClass());
        }
        assertTrue(serializers.contains(JavaSerializer.class));
        assertTrue(serializers.contains(HessianSerializer.class));
        assertTrue(serializers.contains(org.xine.xebuy.business.serialization.boundary.NullSerializer.class));
        assertThat(serializers.size(), is(3));
    }

    @Test
    public void defaultInjection() {
        assertNotNull(standard);
    }

    @Test
    public void optimizedInjection() {
        assertNotNull(optimized);
    }

    @Test
    public void dynamicSelection() {
        final Serializer actual = plugins.select(new SerializationType(Serialization.PlanType.DEFAULT)).get();
        assertThat(actual, instanceOf(standard.getClass()));
    }

}

//
// @RunWith(Arquillian.class)
// public class PluginsIT {
//
// @Deployment
// public static JavaArchive createTestArchive() {
// return ShrinkWrap.create(JavaArchive.class, "plugin.jar").
// addClasses(
// //addClasses(Plugin.class,JavaSerializer.class,HessianSerializer.class,NullSerializer.class).
// //com.sun.tools.ws.wscompile.Plugin.class,
// JavaSerializer.class,
// HessianSerializer.class).
// addAsManifestResource(
// new ByteArrayAsset("beans.xml".getBytes()),
// ArchivePaths.create(""));
// }
//
// @Inject
// @Any
// Instance<Serializer> plugins;
//
// @Inject
// @Serialization(plantype = PlanType.OPTIMIZED)
// Serializer optimized;
//
// @Inject
// @Serialization(plantype = PlanType.DEFAULT)
// Serializer standard;
//
// @Test
// public void defaultInjection() {
// assertNotNull(standard);
// }
//
// @Test
// public void optimizedInjection() {
// assertNotNull(optimized);
// }

    /*
     * @Deployment public static JavaArchive createDeployment() { return
     * ShrinkWrap.create(JavaArchive.class) .addClass(Funcionario.class)
     * .addClass(CalculadoraSalarios.class)
     * .addClass(CalculadoraSalariosDezPorcento.class) .addAsManifestResource(
     * EmptyAsset.INSTANCE, "beans.xml"); }
     */
// }
