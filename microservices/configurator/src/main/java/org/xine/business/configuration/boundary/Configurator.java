package org.xine.business.configuration.boundary;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import org.xine.business.configuration.boundary.ProviderType.Type;
import org.xine.business.configuration.control.MapProvider;

public class Configurator {

    @Inject
    @ProviderType(Type.LOCAL)
    Instance<MapProvider> provider;

    @Produces
    public String obtainConfigurableName(final InjectionPoint ip) {
        final AnnotatedField field = (AnnotatedField) ip.getAnnotated();

        final Configurable configurable = field.getAnnotation(Configurable.class);
        if (configurable != null) {
            final String key = configurable.value();
            return this.provider.get().getConfiguration().get(key);
        } else {
            return "";
        }
    }

    // @Produces
    public String getString(final InjectionPoint ip) {
        final String clazzName = ip.getMember().getDeclaringClass().getName();
        final String memberName = ip.getMember().getName();
        final String fqn = String.format("%s.%s", clazzName, memberName);


        String value = this.provider.get().getConfiguration().get(fqn);
        if (value == null) {
            value = "";
        }
        return value;
    }

}
